package com.example.aymen.androidchat;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.aymen.androidchat.MainActivity.flag;

public class ChatBoxActivity extends AppCompatActivity {

    String TAG = "Chat";

    String messageamr = "";

    public RecyclerView myRecylerView ;
    public List<Message> MessageList ;
    public ChatBoxAdapter chatBoxAdapter;
    public  EditText messagetxt ;
    public  Button send ;
    //declare socket object
    public static Socket socket;

    private Button chatButton,friendButton;
    private ListView messageListView;
    private MessageAdapter messageAdapter;
    private EditText textField;
    private ImageButton sendButton;
    private ImageButton sendFile;

    private String ipaddress;

    ProgressBar progressBar;

    // rest api
    private static final int FILE_SELECT_CODE = 0;

    private static final String URL = "https://192.168.43.246:3000/messageUser";

//    private static final String URL = "https://bijoy-weather-application.herokuapp.com/weather?address=boston";



    public static String Nickname ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Remove the notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_chat_box);







        messagetxt = (EditText) findViewById(R.id.message) ;
        // get the nickame of the user
        Nickname= (String)getIntent().getExtras().getString(MainActivity.NICKNAME);
        ipaddress=   getIntent().getStringExtra("ipaddress");

        System.out.println(ipaddress);

        //connect you socket client to the server
        try {
            socket = IO.socket("http://"+ipaddress+":3000");
//            socket = IO.socket("http://192.168.43.246:3000");

            socket.connect();
            socket.emit("previousMessage", Nickname);
            socket.emit("join", Nickname);

        } catch (URISyntaxException e) {
            e.printStackTrace();

        }
       //setting up recyler
//        MessageList = new ArrayList<>();
//        myRecylerView = (RecyclerView) findViewById(R.id.messagelist);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        myRecylerView.setLayoutManager(mLayoutManager);
//        myRecylerView.setItemAnimator(new DefaultItemAnimator());


        // friend list
        chatButton = (Button) findViewById(R.id.chatid);
        friendButton = (Button) findViewById(R.id.friendid);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatBoxActivity.this,UserLogin.class);
                startActivity(intent);
                finish();
            }
        });

        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatBoxActivity.this,FriendActivity.class);
                startActivity(intent);
//                finish();
            }
        });



        sendFile = findViewById(R.id.sendFile);

        textField = findViewById(R.id.textField);
        sendButton = findViewById(R.id.sendButton);
        messageListView = findViewById(R.id.messageListView);

        List<MessageFormat> messageFormatList = new ArrayList<>();

        messageAdapter = new MessageAdapter(this, R.layout.item_message, messageFormatList);
        messageListView.setAdapter(messageAdapter);

//        send file with boss

        sendFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Select file"), 1);
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//                try {
//                    startActivityForResult(
//                            Intent.createChooser(intent, "Select a File to Upload"),
//                            FILE_SELECT_CODE);
//                } catch (android.content.ActivityNotFoundException ex) {
//                    // Potentially direct the user to the Market with a Dialog
//                    Toast.makeText(ChatBoxActivity.this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        // send file with boss



        //rest api
        if(flag){


            //Creating a retrofit object
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                    .build();

            //creating the api interface
            Api api = retrofit.create(Api.class);

            //now making the call object
            //Here we are using the api method that we created inside the api interface
            Call<List<MessageNet>> call = api.getHeroes();

            //then finallly we are making the call using enqueue()
            //it takes callback interface as an argument
            //and callback is having two methods onRespnose() and onFailure
            //if the request is successfull we will get the correct response and onResponse will be executed
            //if there is some error we will get inside the onFailure() method
            call.enqueue(new Callback<List<MessageNet>>() {


                @Override
                public void onResponse(Call<List<MessageNet>> call, retrofit2.Response<List<MessageNet>> response) {
                    List<MessageNet> heroList = response.body();
                    System.out.println("print hoitese kina");
                    for(MessageNet h:heroList){
                        Log.d("Username : ",h.getUserName());
                        String name = h.getUserName();
                        System.out.println(name);
                    }
                }

                @Override
                public void onFailure(Call<List<MessageNet>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


/*            StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("CODE",response);
                    System.out.println("Hello World");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);*/

            flag = false;
        }



        // message send action
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //retrieve the nickname and the message content and fire the event messagedetection

                messageamr = textField.getText().toString();
                messageamr.trim();
                Log.d(TAG, "onClick: "+ messageamr.length()+ " "+messageamr);
                Boolean containsSpace = true;
                int i=0;
                int length = messageamr.length();
                Log.d(TAG, "onClick: value of i: "+i+"value of length: "+length);
                while (containsSpace && i<length){
                    Log.d(TAG, "onClick: Entered in While Loop");
                    if(messageamr.charAt(i)!=' ')
                        containsSpace= false;
                    i++;
                }
                Log.d(TAG, "onClick: Constains Space"+containsSpace);
                if(!containsSpace)
                {
                    Log.d(TAG, "onClick: Entered Here");
                    socket.emit("messagedetection",Nickname,textField.getText().toString());
                    textField.setText(" ");
                }
            }
        });

        //implementing socket listeners
        socket.on("userjoinedthechat", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        String data = (String) args[0];
//
//                        Toast.makeText(ChatBoxActivity.this,data,Toast.LENGTH_SHORT).show();
                        String username;
                        String fullmessage;
                        String id;
                        JSONObject data = (JSONObject) args[0];
                        try {
                            //extract data from fired event

                            username = data.getString("senderNickname") + " Connected";
//                            String message = data.getString("message");

                            id = "";
                            fullmessage = "";

                            MessageFormat format = new MessageFormat(null, username, null);
                            messageAdapter.add(format);
                            // make instance of message
                            messageListView.smoothScrollToPosition(messageAdapter.getCount()-1);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        socket.on("userdisconnect", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        String data = (String) args[0];

//                        Toast.makeText(ChatBoxActivity.this,data,Toast.LENGTH_SHORT).show();
                        String username;
                        String fullmessage;
                        String id;



                        JSONObject data = (JSONObject) args[0];
                        try {
                            //extract data from fired event
                            username = data.getString("senderNickname") + " Disconnected";
//                            String message = data.getString("message");

                            id = "";
                            fullmessage = "";

                            MessageFormat format = new MessageFormat(null, username, null);
                            messageAdapter.add(format);
                            messageListView.smoothScrollToPosition(messageAdapter.getCount()-1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //



                    }
                });
            }
        });
        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String username;
                        String fullmessage;
                        String id;

                        JSONObject data = (JSONObject) args[0];
                        try {
                            //extract data from fired event
                             username = data.getString("senderNickname");
                            fullmessage = data.getString("message");
                             id="";
                             // make instance of message
                             MessageFormat format = new MessageFormat(null, username, fullmessage);
                             messageAdapter.add(format);
                             messageListView.smoothScrollToPosition(messageAdapter.getCount()-1);
//                             messageListView.scrollTo(0, messageAdapter.getCount()-1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
    }

    // boss
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path;
        System.out.println("Dhukse activity te");
        if (requestCode == 1) {
            try {
                Uri txtUri = data.getData();
                path = txtUri.getPath();
                System.out.println("file er path ta pathaitese");
//                System.out.println(path);
                Log.d(TAG, "onActivityResult: " + path);
                String[] arrOfStr = path.split(":");
                if (arrOfStr.length > 1) {
                    Log.d(TAG, "onActivityResult: Textual " + path);
                    new fileTransfer(arrOfStr[1]).execute();
                } else {
                    Log.d(TAG, "onActivityResult: Image " + path);
                    new fileTransfer(arrOfStr[0]).execute();
                }
            } catch (NullPointerException e) {
                Log.d(TAG, "onActivityResult: No File Selected");
            }
        }


    }


    class fileTransfer extends AsyncTask<Void, Integer, String> {
        String path;

        fileTransfer(String path) {
            this.path = path;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String filenameX = "";
            try {
                if (path.charAt(0) != '/') {
                    path = "/storage/emulated/0/" + path;
                }
                File file = new File(path);
                if (path.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Path is empty", Toast.LENGTH_SHORT);
                    toast.show();
                }

                FileInputStream fileInputStream = new FileInputStream(file);

                long fileSize = file.length();
                byte[] byteArray = new byte[(int) fileSize];
                filenameX = file.getName();

                Log.d(TAG, "doInBackground: " + filenameX);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return filenameX;
        }


    }

    // boss

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        socket.emit("disconnectCheck", Nickname);

        socket.disconnect();
  }
}
