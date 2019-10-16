const express = require('express'),
http = require('http'),
app = express(),
server = http.createServer(app),
io = require('socket.io').listen(server);

//DB
const mongoose = require('mongoose')
const validator = require('validator')
mongoose.connect('mongodb://127.0.0.1:27017/chat-application', {
    useNewUrlParser: true,
    useCreateIndex: true
});

const ActiveFriend = mongoose.model('ActiveFriend', {
    uniqueId: {
        type: String,
        trim: true
    },
    userName: {
        type: String,
        trim: true
    }
});


const MessageUser = mongoose.model('MessageUser', {
    uniqueId: {
        type: String,
        trim: true
    },
    userName: {
        type: String,
        trim: true
    },
    message: {
        type: String,
        trim: true
    }

});


app.use(express.json())
app.get('/messageUser', (req, res) => {
    MessageUser.find({}).then((messages) => {
        res.send(messages);
        
    }).catch((e) => {
        console.log(e);
    });
});


app.get('/', (req, res) => {





res.send('Chat Server is running on port 3000')
});


const users = {}
io.on('connection', (socket) => {

console.log('user connected')

    socket.on('activeList', function (userNickname) {
        
        console.log(userNickname);
        // users[socket.id] = userNickname
        ActiveFriend.find({}).then((messages) => {
            // res.send(messages);

            for (var i = 0; i < messages.length; i++) {
                var obj = (messages[i]);
                var senderNickname = obj.userName;
                // var messageContent = obj.message;
                
                console.log(senderNickname + " ase ");

                let message = {  "senderNickname": senderNickname }
                // send the message to the client side 
                io.to(`${socket.id}`).emit('listofActive', message);
                // io.emit('message', message);
            }

        }).catch((e) => {
            console.log(e);
        });
    });


    socket.on('join', function(userNickname) {

        users[socket.id] = userNickname

        const active_list = new ActiveFriend({
            uniqueId: socket.id,
            userName: userNickname
        });
        active_list.save().then(() => {
            console.log(active_list)
        }).catch((error) => {
            console.log('Error!', error)
        });

        const messageName = new MessageUser({
            uniqueId: userNickname,
            userName: userNickname,
            message: "*$*"
        });
        messageName.save().then(() => {
            console.log(messageName)
        }).catch((error) => {
            console.log('Error!', error)
        }); 
        console.log(userNickname +" : has joined the chat "  );
        let message = { "senderNickname": userNickname }
        // socket.broadcast.emit('userjoinedthechat',message);
        socket.broadcast.emit('userjoinedthechat',message);
    });
    socket.on('previousMessage', function (userNickname) {

        MessageUser.find({}).then((messages) => {
            // res.send(messages);
            
            for(var i=0;i<messages.length;i++){
                var obj = (messages[i]);
                var senderNickname = obj.userName;
                var messageContent = obj.message;

                let message = { "message": messageContent, "senderNickname": senderNickname }
                // send the message to the client side 
                io.to(`${socket.id}`).emit('message',message);
                // io.emit('message', message);
            }

        }).catch((e) => {
            console.log(e);
        });

    });

    socket.on('messagedetection', (senderNickname,messageContent) => {
       

        const messageName = new MessageUser({
            uniqueId: senderNickname,
            userName: senderNickname,
            message: messageContent
        });
        messageName.save().then(() => {
            console.log(messageName)
        }).catch((error) => {
            console.log('Error!', error)
        }); 


        //log the message in console 

        console.log(senderNickname+" :" +messageContent)
        //create a message object 
        let  message = {"message":messageContent, "senderNickname":senderNickname}
            // send the message to the client side  
        io.emit('message', message );
    
    });
      
  

    socket.on('disconnect', function () {

        // socket.broadcast.emit('userdisconnect', users[socket.id])
        var userNickname = users[socket.id]

        ActiveFriend.deleteOne({
            uniqueId : socket.id
        }).then ( (result) => {
            console.log(result)
        }).catch((e)=>{
            console.log(e)
        });

        const messageName = new MessageUser({
            uniqueId: userNickname,
            userName: userNickname,
            message: "$*$"
        });
        messageName.save().then(() => {
            console.log(messageName)
        }).catch((error) => {
            console.log('Error!', error)
        }); 

        delete users[socket.id]
        console.log(userNickname+ '  disconnected ')
        let message = { "senderNickname": userNickname }
        // socket.broadcast.emit('userjoinedthechat',message);
        socket.broadcast.emit('userdisconnect', message);

        // socket.broadcast.emit("userdisconnect"," user has left ") 

    });
});





server.listen(3000,()=>{

    console.log('Node app is running on port 3000');

});