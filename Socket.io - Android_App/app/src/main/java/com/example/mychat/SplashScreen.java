/*
    This is a flash Screen for this app
*/


package com.example.mychat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Remove the notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        //Code start here
        Animation myanimation = AnimationUtils.loadAnimation(this,R.anim.mytransition);

        findViewById(R.id.logosust).startAnimation(myanimation);
        findViewById(R.id.logoblockchain).startAnimation(myanimation);
        findViewById(R.id.chat1).startAnimation(myanimation);


        Thread mythread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(4000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(SplashScreen.this,AddUserActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        mythread.start();
    }
}
