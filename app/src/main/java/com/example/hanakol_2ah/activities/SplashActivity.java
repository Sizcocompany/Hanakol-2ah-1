package com.example.hanakol_2ah.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hanakol_2ah.R;


public class SplashActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView welcome_tv;
    private static int splashTimeOut = 10000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.logo_Image);
        welcome_tv = findViewById(R.id.welcome_tv);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, HomeBaseActivity.class);
                startActivity(i);
                finish();
            }
        }, splashTimeOut);

        Animation logo_animate = AnimationUtils.loadAnimation(this, R.anim.my_splash_logo_animation);
        logo.startAnimation(logo_animate);
        Animation text_animate = AnimationUtils.loadAnimation(this, R.anim.my_splash_text_animation);
        welcome_tv.startAnimation(text_animate);
    }


}