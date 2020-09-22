package com.example.hanakol_2ah.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.fragments.ListMealsFragmentContainer;
import com.facebook.AccessToken;


public class HomeActivity extends AppCompatActivity {
    ListMealsFragmentContainer fragment;
    TextView login_txt_btn;
    private Boolean Visablilety ;

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
        login_txt_btn = findViewById(R.id.login_txt_btn);
        ;
        final TextView TV_add_new_meal = findViewById(R.id.TV_add_new_meal);
        TextView hanakoleh = findViewById(R.id.hanakolehTextView);
        hanakoleh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RandomMealsActivity.class);
                startActivity(intent);
            }
//
//            public void logOut(View view){
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                finish();
//            }
        });

        Visablilety = isLoggedIn();
        if (Visablilety == true){
            login_txt_btn.setVisibility(View.INVISIBLE);
        }

        onClickCardviews(breackfastCardView, "breakfast");
        onClickCardviews(launchCardView, "lunch");
        onClickCardviews(dinnerCardView, "dinner");

        login_txt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        TV_add_new_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddActivity.class);
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

//    public void invisibleRelativeLayout() {
//        View relativeLayout = findViewById(R.id.fragment_home_layout);
//        relativeLayout.setVisibility(View.INVISIBLE);
//    }
//
//    public void removeLoginText(Boolean aBoolean) {
//        if (aBoolean == false) {
//            this.login_txt_btn.setVisibility(View.INVISIBLE);
//        }

//    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

}