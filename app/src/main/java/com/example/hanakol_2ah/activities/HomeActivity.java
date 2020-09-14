package com.example.hanakol_2ah.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanakol_2ah.Login;
import com.example.hanakol_2ah.fragments.ListMealsFragmentContainer;
import com.example.hanakol_2ah.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity {
    ListMealsFragmentContainer fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        final CardView breackfastCardView = findViewById(R.id.breackfastLayoutClick);
        final CardView launchCardView = findViewById(R.id.lunchLayoutClick);
        final CardView dinnerCardView = findViewById(R.id.dinnerLayoutClick);
        final CardView dessertsCardView = findViewById(R.id.dessertsLayoutClick);
        final CardView juicesCardView = findViewById(R.id.juicesLayoutClick);
        final CardView snackesCardView = findViewById(R.id.snackesLayoutClick);
        final View relativeLayout = findViewById(R.id.fragment_home_layout);
        TextView hanakoleh = findViewById(R.id.hanakolehTextView);
        hanakoleh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RandomMealsActivity.class);
                startActivity(intent);
            }

            public void logOut(View view){
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });



        onClickCardviews(breackfastCardView, "Breakfast");
        onClickCardviews(launchCardView, "Launch");
        onClickCardviews(dinnerCardView, "Dinner");

        TextView login_txt_btn = findViewById(R.id.login_txt_btn);
        login_txt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



    }


    public void onClickCardviews(CardView cardView, final String string) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ListMealsFragmentContainer(string);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_home_container, fragment);
                transaction.commit();


            }
        });


    }

    public void invisibleRelativeLayout() {
        View relativeLayout = findViewById(R.id.fragment_home_layout);
        relativeLayout.setVisibility(View.INVISIBLE);
    }
}