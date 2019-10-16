package com.example.mychat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddUserActivity extends AppCompatActivity {


    private EditText mTextUsername;
    private EditText mTextPassword;
    private Button mButtonLogin;
    private TextView mTextViewRegister;

    AlertDialog.Builder alertDialogBuilder;

    public static boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Remove the notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_user);


        // Code start here
        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mTextViewRegister = (TextView)findViewById(R.id.textview_register);


        mTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mButtonLogin.setEnabled(true);
                } else {
                    mButtonLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( TextUtils.isEmpty(mTextUsername.getText())){
                    /**
                     *   You can Toast a message here that the Username is Empty
                     **/

                    mTextUsername.setError( "Username is required!" );
                }
                else if( TextUtils.isEmpty(mTextPassword.getText())){
                    /**
                     *   You can Toast a message here that the Username is Empty
                     **/

                    mTextPassword.setError( "Password is required!" );
                }
                else{
                    Intent intent = new Intent(AddUserActivity.this,MainActivity.class);
                    intent.putExtra("username", mTextUsername.getText().toString());
                    startActivity(intent);
                    //finish();
                }

            }
        });


        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddUserActivity.this,RegisterActivity.class);
                startActivity(intent);
                //finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        alertDialogBuilder = new AlertDialog.Builder(AddUserActivity.this);

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
