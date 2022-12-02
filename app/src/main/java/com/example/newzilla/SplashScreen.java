package com.example.newzilla;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashScreen extends Activity  {

    static int SPLASH_TIME_OUT = 500;

    ImageView iv_Loader;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean hasLoggedIn =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        iv_Loader = findViewById(R.id.splash_screen);

        sharedPreferences = getSharedPreferences("newzilla", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn",false);


        success();

    }


    public void success() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent;

                if(hasLoggedIn){

                    intent = new Intent(getApplicationContext(), Home.class);
                }else {

                    intent = new Intent(getApplicationContext(), Login.class);
                }
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}
