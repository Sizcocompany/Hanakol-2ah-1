package com.example.hanakol_2ah.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.adapters.MealAdapter;
import com.example.hanakol_2ah.fragments.ListMealsFragmentContainer;
import com.example.hanakol_2ah.fragments.MyMealsFragment;
import com.example.hanakol_2ah.fragments.SearchFragment;
import com.example.hanakol_2ah.fragments.SelectedItemFragment;
import com.example.hanakol_2ah.user_interface.ToolBarActivity;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Locale;


public class HomeBaseActivity extends ToolBarActivity implements TextView.OnEditorActionListener, GoogleApiClient.OnConnectionFailedListener {
    ListMealsFragmentContainer fragment;
    public static TextView login_txt_btn, add_new_meal_tv_btn;
    private Boolean Visablilety;
    private Toolbar toolbar;


    //toolbar
    private ImageView ic_menu;
    SearchFragment searchFragment;
    private ImageView open_search_fragment_iv;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query query;
    private MealAdapter adapter;
    private Fragment selectedItemFragment;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private long backPressedTime;
    private boolean isMenuOpen;


//-------------------------------------------SideMenu-----------------------------------------------

    private String mUsername;
    private String mEmail;
    private String mPhotoUrl;
    private GoogleApiClient mGoogleApiClient;

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

        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        setContentView(R.layout.activity_home_base);


// initiate a video view
        VideoView simpleVideoView = (VideoView) findViewById(R.id.videoView);
        simpleVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        simpleVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.background));
        simpleVideoView.start();


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
        open_search_fragment_iv = findViewById(R.id.open_search_fragment);
        searchFragment = new SearchFragment();
        selectedItemFragment = new SelectedItemFragment();

        drawerLayout = findViewById(R.id.drawer_layout);

        add_new_meal_tv_btn = findViewById(R.id.TV_add_new_meal);
        TextView hanakoleh = findViewById(R.id.hanakolehTextView);
        hanakoleh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeBaseActivity.this, RandomMealsActivity.class);
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
                Intent intent = new Intent(HomeBaseActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        if (isLoggedIn() != true) {
            add_new_meal_tv_btn.setVisibility(View.INVISIBLE);
        }
        add_new_meal_tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeBaseActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        ic_menu = findViewById(R.id.ic_menu);
        ic_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setSideMenuData();
                handleMenuAction();

            }
        });


        open_search_fragment_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment searchFragment = new SearchFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_up);
                transaction.replace(R.id.activity_home_container, searchFragment, "fragment");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }


    public void onClickCardviews(CardView cardView, final String string, final int MEAL_FAVORITES_CONDITION) {

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!isMenuOpen) {
                fragment = new ListMealsFragmentContainer(string);
                Bundle bundle = new Bundle();
                bundle.putInt("MEAL_FAVORITES_CONDITION", MEAL_FAVORITES_CONDITION);
                FragmentTransaction(fragment, bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_home_container, fragment, "fragment");
                transaction.commit();
//                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // to set home activte updated with all actions in side menu
        setSideMenuData();
    }

    private void setSideMenuData() {
        try {

            mGoogleApiClient = new GoogleApiClient.Builder(this.getApplicationContext())
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API)
                    .build();

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
//                mUsername = mFirebaseUser.getDisplayName();
//                if(mUsername ==null){
                mUsername = firebaseUser.getEmail();
//                }
                mUsername = firebaseUser.getDisplayName();
                if (firebaseUser.getPhotoUrl() != null) {
                    mPhotoUrl = firebaseUser.getPhotoUrl().toString();
                    Picasso.get().load(mPhotoUrl).into(user_profile_pic);
                }
                user_profile_name.setText(mUsername);


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

        try {
            mEmail = firebaseUser.getEmail();
        } catch (Exception e) {
            mEmail = "userName";
        }
    }

    // to add new fragment
    private void FragmentTransaction(Fragment fragment, Bundle bundle) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home_container, fragment, "fragment");
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

                isMenuOpen = false;
            } else {
                drawerLayout.openDrawer(Gravity.RIGHT);
                isMenuOpen = true;
            }
        } else {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawerLayout.closeDrawer(Gravity.LEFT);
                    }
                }, isCloseDelayed ? DRAWER_CLOSE_DELAY : 0);

                isMenuOpen = false;
            } else {
                drawerLayout.openDrawer(Gravity.LEFT);
                isMenuOpen = true;
            }
        }
    }

    protected void customizeSideMenu() {
        user_profile_pic = findViewById(R.id.user_profile_pic);
        user_profile_name = (TextView) findViewById(R.id.user_profile_name);

        myPosts = findViewById(R.id.my_post);
        changeLanguage = (LinearLayout) findViewById(R.id.language_Linear);
        logOut = findViewById(R.id.log_out_side_menu_linear_layout);
        aboutUs = findViewById(R.id.about_Linear);


        myPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyMealsFragment myMealsFragment = new MyMealsFragment(mEmail);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_home_container, myMealsFragment, "fragment");
                transaction.commit();

                drawerLayout.closeDrawer(Gravity.LEFT);


            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int x;
            }
        });

        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int x;
            }
        });
//
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogout();
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

    }

    public void btnLogout() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseAuth.signOut();
        LoginManager.getInstance().logOut();
        login_txt_btn.setVisibility(View.VISIBLE);
        add_new_meal_tv_btn.setVisibility(View.INVISIBLE);
        user_profile_pic.setImageResource(R.drawable.ic_user_pic);
        user_profile_name.setText("User Name");
        myPosts.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "See you later chief " + mUsername + ":)", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public String onGetOwnerName(FirebaseAuth firebaseAuth, GoogleApiClient googleApiClient) {
        FirebaseAuth mFirebaseAuth2 = firebaseAuth;
        FirebaseUser mFirebaseUser;
        GoogleApiClient mGoogleApiClient = googleApiClient;
        String mUsername = "UserName";
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API)
                    .build();

            mFirebaseAuth2 = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth2.getCurrentUser();
            if (mFirebaseUser == null) {
                mUsername = "Dear chief";
                // Not signed in, launch the Sign In activity
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
            } else {
                mUsername = mFirebaseUser.getDisplayName();

            }
        } catch (Exception e) {
            mFirebaseAuth2 = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth2.getCurrentUser();
            if (mFirebaseUser != null) {
                mUsername = mFirebaseUser.getDisplayName();

            }
        }

        return mUsername;
    }

    public String onGetOwnerName() {
        FirebaseAuth mFirebaseAuth;
        FirebaseUser mFirebaseUser;
        GoogleApiClient mGoogleApiClient;
        String mUsername = "UserName";
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API)
                    .build();

            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser == null) {
                // Not signed in, launch the Sign In activity
            } else {
                mUsername = mFirebaseUser.getEmail();

            }
        } catch (Exception e) {
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser != null) {
                mUsername = mFirebaseUser.getEmail();

            }
        }

        return mUsername;
    }


    @Override
    public void onBackPressed() {


        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    isMenuOpen = false;
                }
            }, false ? DRAWER_CLOSE_DELAY : 0);

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    isMenuOpen = true;
                }
            }, false ? DRAWER_CLOSE_DELAY : 0);
        }


        if (getSupportFragmentManager().getFragments().size() > 0) {

            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1)).commit();

            showHideToolBar(true);
        } else if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();

        }
        backPressedTime = System.currentTimeMillis();

    }

    public void showHideToolBar(boolean show) {
        toolbar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}