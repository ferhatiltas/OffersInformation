package com.ferhatiltas.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.*;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class SplashAnimaton extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_animaton);
    }

    @Override
    protected void onResume() {

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
                Intent intent=new Intent(getApplicationContext(),IntroActivity.class);
                startActivity(intent);
            }
        }, 1000);
        super.onResume();
    }
}


