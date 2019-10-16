/*
    This activity take user information form the user and save it to the database
*/



package com.example.mychat;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextEmail;
    Button mButtonRegister;
    TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Remove the notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);


        // Code start here
        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mTextEmail = (EditText)findViewById(R.id.edittext_email);
        mButtonRegister = (Button)findViewById(R.id.button_register);
        mTextViewLogin = (TextView)findViewById(R.id.textview_Login);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(! TextUtils.isEmpty(mTextUsername.getText())){
                    /**
                     *   You can Toast a message here that the Username is Empty
                     **/
                    if(! TextUtils.isEmpty(mTextEmail.getText())){
                        /**
                         *   You can Toast a message here that the Username is Empty
                         **/
                        if(! TextUtils.isEmpty(mTextPassword.getText())){
                            /**
                             *   You can Toast a message here that the Username is Empty
                             **/
                            Intent intent = new Intent(RegisterActivity.this,AddUserActivity.class);
                            startActivity(intent);
                            //Toast.makeText(getApplicationContext(), "Check Your Email To Activate Your Account...!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            mTextPassword.setError( "Password is required!" );

                        }
                    }
                    else{
                        mTextEmail.setError( "Email is required!" );

                    }
                }
                else{
                    mTextUsername.setError( "Username is required!" );

                }

            }
        });

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,AddUserActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
