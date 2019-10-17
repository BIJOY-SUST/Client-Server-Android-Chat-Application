package com.example.aymen.androidchat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;


public class UserLogin extends AppCompatActivity {


    private Button btn;
    private EditText nickname, ipAddress;
    public static final String NICKNAME = "usernickname";
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Remove the notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_user_login);

        //call UI component  by id
        btn = (Button) findViewById(R.id.button_login) ;
        nickname = (EditText) findViewById(R.id.edittext_username);
        ipAddress = findViewById(R.id.edittext_ip);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the nickname is not empty go to chatbox activity and add the nickname to the intent extra

                if( TextUtils.isEmpty(nickname.getText())){
                    /**
                     *   You can Toast a message here that the Username is Empty
                     **/

                    nickname.setError( "Username is required!" );
                }
                else if( TextUtils.isEmpty(ipAddress.getText())){
                    /**
                     *   You can Toast a message here that the Username is Empty
                     **/

                    ipAddress.setError( "Server IPv4 Address is required!" );
                }
                else{
                    Intent i  = new Intent(UserLogin.this,ChatBoxActivity.class);
//                    retreive nickname from textview and add it to intent extra
                    i.putExtra(NICKNAME,nickname.getText().toString());
                    i.putExtra("ipaddress",ipAddress.getText().toString());
                    System.out.println(nickname.getText().toString() + " is on the way");
                    startActivity(i);
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        alertDialogBuilder = new AlertDialog.Builder(UserLogin.this);

        alertDialogBuilder.setTitle("Confirm Exit");
        alertDialogBuilder.setMessage("Are you sure you want to exit?");
        alertDialogBuilder.setIcon(R.drawable.question3);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                finish();
                moveTaskToBack(true);
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
//         super.onBackPressed();
    }

}
