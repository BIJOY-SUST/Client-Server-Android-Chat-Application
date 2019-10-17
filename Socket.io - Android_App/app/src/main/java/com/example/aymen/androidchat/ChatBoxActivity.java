package com.example.aymen.androidchat;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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




        textField = findViewById(R.id.textField);
        sendButton = findViewById(R.id.sendButton);
        messageListView = findViewById(R.id.messageListView);

        List<MessageFormat> messageFormatList = new ArrayList<>();

        messageAdapter = new MessageAdapter(this, R.layout.item_message, messageFormatList);
        messageListView.setAdapter(messageAdapter);


        // message send action
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //retrieve the nickname and the message content and fire the event messagedetection

                messageamr = textField.getText().toString().trim();
                String newMessage= messageamr.trim();

                Log.d(TAG, "onClick: "+ newMessage.length()+ " "+newMessage);
                Boolean containsSpace = true;
                int i=0;
                int length = newMessage.length();
                Log.d(TAG, "onClick: value of i: "+i+"value of length: "+length);
                while (containsSpace && i<length){
                    Log.d(TAG, "onClick: Entered in While Loop");
                    if(newMessage.charAt(i)!=' ')
                        containsSpace= false;
                    i++;
                }
                Log.d(TAG, "onClick: Constains Space"+containsSpace);
                if(!containsSpace)
                {
                    Log.d(TAG, "onClick: Entered Here");
                    socket.emit("messagedetection",Nickname,newMessage);
                    textField.setText(" ");
                }
                else{
//                    textField.setError( "This value should not be null!" );
                    Toast toast = Toast.makeText(getApplicationContext(), "This value should not be null!", Toast.LENGTH_SHORT);
                    toast.show();

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


    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String ans = result.get(0);
                    if(ans.length()>0)
                    {
                        Log.d(TAG, "onClick: Entered Here");
                        socket.emit("messagedetection",Nickname,ans);
                        textField.setText(" ");
                    }
                    else{
//                    textField.setError( "This value should not be null!" );
                        Toast toast = Toast.makeText(getApplicationContext(), "This value should not be null!", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        socket.emit("disconnectCheck", Nickname);

        socket.disconnect();
    }
}
