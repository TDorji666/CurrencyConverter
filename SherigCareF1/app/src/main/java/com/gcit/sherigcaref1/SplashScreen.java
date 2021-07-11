package com.gcit.sherigcaref1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        Thread thread = new Thread(){
            @Override
            public void run(){
                super.run();
                try{
                    sleep(2000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }

        };
        thread.start();
    }
}
//