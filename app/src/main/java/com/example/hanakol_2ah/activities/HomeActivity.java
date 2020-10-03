package com.example.hanakol_2ah.activities;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.fragments.ListMealsFragmentContainer;
import com.example.hanakol_2ah.fragments.SearchFragment;
import com.example.hanakol_2ah.user_interface.FragmentSideMenu;
import com.example.hanakol_2ah.user_interface.ToolBarActivity;
import com.facebook.AccessToken;


public class HomeActivity extends ToolBarActivity implements TextView.OnEditorActionListener {
    ListMealsFragmentContainer fragment;
    public EditText search_txt;
    public static TextView login_txt_btn;
    private Boolean Visablilety;
    private Toolbar toolbar;
    //toolbar
    private ImageView ic_menu ;
    private SearchView open_search_fragment_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);


        final CardView breackfastCardView = findViewById(R.id.breackfastLayoutClick);
        final CardView launchCardView = findViewById(R.id.lunchLayoutClick);
        final CardView dinnerCardView = findViewById(R.id.dinnerLayoutClick);
        final CardView dessertsCardView = findViewById(R.id.dessertsLayoutClick);
        final CardView juicesCardView = findViewById(R.id.juicesLayoutClick);
        final CardView favoritesCardView = findViewById(R.id.favoritesLayoutClick);
        final View relativeLayout = findViewById(R.id.fragment_home_layout);
//        search_txt = findViewById(R.id.search_text);
        login_txt_btn = findViewById(R.id.login_txt_btn);
        toolbar.setVisibility(View.VISIBLE);

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
        if (Visablilety == true) {
            login_txt_btn.setVisibility(View.INVISIBLE);
        }

        onClickCardviews(breackfastCardView, "breakfast");
        onClickCardviews(launchCardView, "lunch");
        onClickCardviews(dinnerCardView, "dinner");
        onClickCardviews(favoritesCardView, "favorites");
        onClickCardviews(juicesCardView, "juices");
        onClickCardviews(dessertsCardView, "desserts");


        login_txt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

//        search_txt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                boolean handled = false;
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    handled = true;
//                }
//                return handled;
//            }
//        });

        TV_add_new_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        ic_menu = findViewById(R.id.ic_menu);
        ic_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentSideMenu fragmentSideMenu = new FragmentSideMenu();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                transaction.replace(R.id.activity_home_container, fragmentSideMenu);
                transaction.commit();

            }
        });
        open_search_fragment_txt = findViewById(R.id.search_view);


        open_search_fragment_txt.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchFragment searchFragment = new SearchFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_up);
                transaction.replace(R.id.activity_home_container, searchFragment);
                transaction.commit();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchFragment searchFragment = new SearchFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_out_up, R.anim.slide_out_up);
                transaction.replace(R.id.search_fragment_container, searchFragment);
                transaction.commit();
                return false;
            }
        });


    }

    private void handleSearchText() {


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


    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}









