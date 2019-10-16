/*
    This activity mainly receive data from the database and send the data to the Custom Adapter for displaying in a view
*/


package com.example.mychat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.example.mychat.MainActivity;
import com.example.mychat.R;

import java.util.ArrayList;

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

        friendList.add("BIJOY");
        friendList.add("Biplob");
        friendList.add("Arup");
        friendList.add("Sowmen");
        friendList.add("Sajjad");
        friendList.add("Sat");
        friendList.add("Faiyaz");
        friendList.add("Archi");
        friendList.add("Mou");
        friendList.add("Saba");
        friendList.add("Shihab");
        friendList.add("Sharmin");
        friendList.add("Mobin");
        friendList.add("Rifa");
        friendList.add("Mishkat");

        listFriendView = (ListView)findViewById(R.id.friendListView);
        CustomAdapter adapter = new CustomAdapter(FriendActivity.this,friendList);
        listFriendView.setAdapter(adapter);


        friendButton = findViewById(R.id.friendid);
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendActivity.this,FriendActivity.class);
                startActivity(intent);
                finish();
            }
        });

        chatButton = findViewById(R.id.chatid);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendActivity.this, AddUserActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
