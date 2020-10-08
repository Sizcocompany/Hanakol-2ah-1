package com.example.hanakol_2ah.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.adapters.MealAdapter;
import com.example.hanakol_2ah.fragments.ListMealsFragmentContainer;
import com.example.hanakol_2ah.fragments.SearchFragment;
import com.example.hanakol_2ah.fragments.SelectedItemFragment;
import com.example.hanakol_2ah.user_interface.FragmentSideMenu;
import com.example.hanakol_2ah.user_interface.ToolBarActivity;
import com.facebook.AccessToken;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class HomeActivity extends ToolBarActivity implements TextView.OnEditorActionListener {
    ListMealsFragmentContainer fragment;
    public static TextView login_txt_btn;
    private Boolean Visablilety;
    private Toolbar toolbar;
    //toolbar
    private ImageView ic_menu;
    SearchFragment searchFragment;

    private CardView open_search_fragment_card_view;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query query;
    private MealAdapter adapter;
    private Fragment selectedItemFragment;


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
        login_txt_btn = findViewById(R.id.login_txt_btn);
        toolbar.setVisibility(View.VISIBLE);
        open_search_fragment_card_view = findViewById(R.id.open_search_fragment);
        searchFragment = new SearchFragment();
        selectedItemFragment = new SelectedItemFragment();


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

        onClickCardviews(breackfastCardView, "breakfast", 0);
        onClickCardviews(launchCardView, "lunch", 0);
        onClickCardviews(dinnerCardView, "dinner", 0);
        onClickCardviews(favoritesCardView, "favorites", 1);
        onClickCardviews(juicesCardView, "juices", 0);
        onClickCardviews(dessertsCardView, "desserts", 0);


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


        open_search_fragment_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment searchFragment = new SearchFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_up);
                transaction.replace(R.id.activity_home_container, searchFragment);
                transaction.commit();
            }
        });

    }


    public void onClickCardviews(CardView cardView, final String string, final int MEAL_FAVORITES_CONDITION) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ListMealsFragmentContainer(string);
                Bundle bundle = new Bundle();
                bundle.putInt("MEAL_FAVORITES_CONDITION", MEAL_FAVORITES_CONDITION);
                FragmentTransaction(fragment, bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_home_container, fragment);
                transaction.commit();


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void FragmentTransaction(Fragment fragment, Bundle bundle) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragment.setArguments(bundle);
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









