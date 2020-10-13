package com.example.hanakol_2ah.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.adapters.MealAdapter;
import com.example.hanakol_2ah.fragments.ListMealsFragmentContainer;
import com.example.hanakol_2ah.fragments.SearchFragment;
import com.example.hanakol_2ah.fragments.SelectedItemFragment;
import com.example.hanakol_2ah.user_interface.ToolBarActivity;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Locale;


public class HomeActivity extends ToolBarActivity implements TextView.OnEditorActionListener, GoogleApiClient.OnConnectionFailedListener {
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
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

//-------------------------------------------SideMenu-----------------------------------------------

    private String mUsername;
    private String mEmail;
    private String mPhotoUrl;
    private GoogleApiClient mGoogleApiClient;

//--------------------------------------------------------------------------------------------------

//--------------------------------------Drawer-Layout-----------------------------------------------

    private DrawerLayout drawerLayout;
    private final int DRAWER_CLOSE_DELAY = 300;
    public static String LANGUAGE_AR = "ar";
    public static String LANGUAGE_EN = "en";
    private TextView user_profile_name;
    private LinearLayout aboutUs;
    private LinearLayout changeLanguage;
    private LinearLayout logOut;
    private ImageView user_profile_pic;
    private View myPosts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        FacebookSdk.sdkInitialize(getApplicationContext());
        customizeSideMenu();


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

        drawerLayout = findViewById(R.id.drawer_layout);

        final TextView add_new_meal_tv_btn = findViewById(R.id.TV_add_new_meal);
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

      if(isLoggedIn() != true){
          add_new_meal_tv_btn.setVisibility(View.INVISIBLE);
      }
        add_new_meal_tv_btn.setOnClickListener(new View.OnClickListener() {
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
                handleMenuAction();

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


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            return true;
        }

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


    private void handleMenuAction() {
        toggleDrawer(false);
    }

    public void toggleDrawer(boolean isCloseDelayed) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();


        if (conf.locale
                .getDisplayLanguage()
                .toLowerCase()
                .equals(new Locale(LANGUAGE_AR)
                .getDisplayLanguage().toLowerCase())) {
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawerLayout.closeDrawer(Gravity.RIGHT);
                    }
                }, isCloseDelayed ? DRAWER_CLOSE_DELAY : 0);

            } else {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        } else {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawerLayout.closeDrawer(Gravity.LEFT);
                    }
                }, isCloseDelayed ? DRAWER_CLOSE_DELAY : 0);

            } else {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        }
    }

    protected void customizeSideMenu() {
        user_profile_pic = findViewById(R.id.user_profile_pic);
        user_profile_name = (TextView) findViewById(R.id.user_profile_name);

        myPosts = findViewById(R.id.my_post);
        changeLanguage = (LinearLayout) findViewById(R.id.language_Linear);
        logOut = findViewById(R.id.log_out_side_menu_linear_layout);


        try {


            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser == null) {
                user_profile_name.setText("User Name");
                user_profile_pic.setImageResource(R.drawable.ic_user_pic);
                logOut.setVisibility(View.INVISIBLE);
                myPosts.setVisibility(View.INVISIBLE);
            } else {
                logOut.setVisibility(View.VISIBLE);
                myPosts.setVisibility(View.VISIBLE);
                login_txt_btn.setVisibility(View.INVISIBLE);
                mUsername = firebaseUser.getDisplayName();
                if (mUsername == null) {
                    mUsername = firebaseUser.getEmail();

                    mUsername = firebaseUser.getDisplayName();
                    if (firebaseUser.getPhotoUrl() != null) {
                        mPhotoUrl = firebaseUser.getPhotoUrl().toString();
                        Picasso.get().load(mPhotoUrl).into(user_profile_pic);
                    }
                    user_profile_name.setText(mUsername);


                }
            }
        } catch (Exception e) {
            logOut.setVisibility(View.VISIBLE);
            myPosts.setVisibility(View.VISIBLE);
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                mUsername = firebaseUser.getDisplayName();
                mEmail = firebaseUser.getEmail();
                if (firebaseUser.getPhotoUrl() != null) {
                    mPhotoUrl = firebaseUser.getPhotoUrl().toString();
                    Picasso.get().load(mPhotoUrl).into(user_profile_pic);
                }
                user_profile_name.setText(mUsername);
            } else {
                user_profile_name.setText("User Name");
                user_profile_pic.setImageResource(R.drawable.ic_user_pic);
                logOut.setVisibility(View.INVISIBLE);
                myPosts.setVisibility(View.INVISIBLE);

            }
        }


//        aboutUs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                startActivity(new  In);
//            }
//        });
//
//        changeLanguage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Controller.getInstance(HomeActivity.this).btnLogout("sasassa" , fragmentSideMenu , mGoogleApiClient);
//                Controller.getInstance(HomeActivity.this).showLogOutDialog(HomeActivity.this, true);
                btnLogout();
            }
        });

    }


    public void btnLogout() {


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseAuth.signOut();
        LoginManager.getInstance().logOut();
        login_txt_btn.setVisibility(View.VISIBLE);
        user_profile_pic.setImageResource(R.drawable.ic_user_pic);
        user_profile_name.setText("User Name");
        myPosts.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "See you later chief " + mUsername + ":)", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}









