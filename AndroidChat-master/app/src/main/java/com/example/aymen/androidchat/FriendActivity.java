package com.example.aymen.androidchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;


import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.aymen.androidchat.ChatBoxActivity.Nickname;
import static com.example.aymen.androidchat.ChatBoxActivity.socket;

public class FriendActivity extends AppCompatActivity {

    private Button chatButton,friendButton;
    ListView listFriendView;
    ArrayList<String> friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Remove the notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_friend);


//        Start Coding
        friendList = new ArrayList<>();

//        friendList.add("BIJOY");
//        friendList.add("Biplob");
//        friendList.add("Arup");
//        friendList.add("Sowmen");
//        friendList.add("Sajjad");
//        friendList.add("Sat");
//        friendList.add("Faiyaz");
//        friendList.add("Archi");
//        friendList.add("Mou");
//        friendList.add("Saba");
//        friendList.add("Shihab");
//        friendList.add("Sharmin");
//        friendList.add("Mobin");
//        friendList.add("Rifa");
//        friendList.add("Mishkat");



        socket.emit("activeList", Nickname);






        //implementing socket listeners
        socket.on("listofActive", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        String data = (String) args[0];
//
//                        Toast.makeText(ChatBoxActivity.this,data,Toast.LENGTH_SHORT).show();

                        JSONObject data = (JSONObject) args[0];
                        try {
                            //extract data from fired event

                            String nickname = data.getString("senderNickname");
//                            String message = data.getString("message");

                            // make instance of message

//                            Message m = new Message(nickname,"Connected");
                            System.out.println(nickname);
                            friendList.add(nickname);
                            listFriendView = (ListView)findViewById(R.id.friendListView);
                            CustomAdapter adapter = new CustomAdapter(FriendActivity.this,friendList);
                            listFriendView.setAdapter(adapter);
                            //add the message to the messageList
/*
                            MessageList.add(m);

                            // add the new updated list to the dapter
                            chatBoxAdapter = new ChatBoxAdapter(MessageList);

                            // notify the adapter to update the recycler view

                            chatBoxAdapter.notifyDataSetChanged();

                            //set the adapter for the recycler view

                            myRecylerView.setAdapter(chatBoxAdapter);*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });



        friendButton = (Button) findViewById(R.id.friendid);
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(FriendActivity.this,FriendActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        chatButton = (Button) findViewById(R.id.chatid);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
