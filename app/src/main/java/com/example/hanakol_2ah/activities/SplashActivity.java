package com.example.hanakol_2ah.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hanakol_2ah.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;

import static com.example.hanakol_2ah.activities.HomeActivity.login_txt_btn;

//public class SplashActivity extends AppCompatActivity {
//
//    private ConstraintLayout splashView;
//    private ProgressBar loadingProgressBar;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//
//        splashView = findViewById(R.id.splash_parent_view);
//        loadingProgressBar = findViewById(R.id.progressBar);
//
//        loadingProgressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
//
//        splashView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//
//        schedualWating();
//    }
//
//
//    public void schedualWating(){
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                startActivity(intent);
//            }
//        }, 2000);
//    }
//
//
//
//
//
//}


public class SplashActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView welcome_tv;
    private static int splashTimeOut=1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo=findViewById(R.id.logo_Image);
        welcome_tv =findViewById(R.id.welcome_tv);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
        },splashTimeOut);

        Animation logo_animate = AnimationUtils.loadAnimation(this,R.anim.my_splash_logo_animation);
        logo.startAnimation(logo_animate);
        Animation text_animate = AnimationUtils.loadAnimation(this,R.anim.my_splash_text_animation);
        welcome_tv.startAnimation(text_animate);
    }





}