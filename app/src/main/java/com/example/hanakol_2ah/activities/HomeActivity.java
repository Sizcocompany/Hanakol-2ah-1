//package com.example.hanakol_2ah.activities;
//
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.appcompat.widget.Toolbar;
//import androidx.cardview.widget.CardView;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.example.hanakol_2ah.R;
//import com.example.hanakol_2ah.adapters.MealAdapter;
//import com.example.hanakol_2ah.control.Controller;
//import com.example.hanakol_2ah.fragments.BaseFragment;
//import com.example.hanakol_2ah.fragments.ListMealsFragmentContainer;
//import com.example.hanakol_2ah.fragments.SearchFragment;
//import com.example.hanakol_2ah.fragments.SelectedItemFragment;
//import com.example.hanakol_2ah.user_interface.FragmentSideMenu;
//import com.example.hanakol_2ah.user_interface.ToolBarActivity;
//import com.facebook.AccessToken;
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.Query;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//
//public class HomeActivity extends ToolBarActivity implements TextView.OnEditorActionListener {
//    ListMealsFragmentContainer fragment;
//    public static TextView login_txt_btn;
//    private Boolean Visablilety;
//    private Toolbar toolbar;
//    //toolbar
//    private ImageView ic_menu;
//    SearchFragment searchFragment;
//    FragmentSideMenu fragmentSideMenu;
//    private List<BaseFragment> fragmentList;
//    private FrameLayout containerFrameLayout;
//    private DrawerLayout drawerLayout;
//    private final int DRAWER_CLOSE_DELAY = 300;
//
//    public static String LANGUAGE_AR = "ar";
//    public static String LANGUAGE_EN = "en";
//
//    private CardView open_search_fragment_card_view;
//
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private Query query;
//    private MealAdapter adapter;
//    private Fragment selectedItemFragment;
//    private GoogleApiClient mGoogleApiClient;
//
//    private FirebaseAuth mFirebaseAuth;
//    private FirebaseUser mFirebaseUser;
////    GoogleApiClient mGoogleApiClient;
//
//
//
//    private TextView userName;
//    private LinearLayout aboutUs;
//    private LinearLayout changeLanguage;
//    private LinearLayout logOut;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        toolbar = findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);
//
//
//        fragmentSideMenu = new FragmentSideMenu();
////        mGoogleApiClient = new GoogleApiClient.Builder(this)
////                .addConnectionCallbacks(getActivity().getApplicationContext())
////                .addOnConnectionFailedListener(this)
////                .build();
//
//
//
//        customizeSideMenu();
//
//        final CardView breackfastCardView = findViewById(R.id.breackfastLayoutClick);
//        final CardView launchCardView = findViewById(R.id.lunchLayoutClick);
//        final CardView dinnerCardView = findViewById(R.id.dinnerLayoutClick);
//        final CardView dessertsCardView = findViewById(R.id.dessertsLayoutClick);
//        final CardView juicesCardView = findViewById(R.id.juicesLayoutClick);
//        final CardView favoritesCardView = findViewById(R.id.favoritesLayoutClick);
//        login_txt_btn = findViewById(R.id.login_txt_btn);
//        toolbar.setVisibility(View.VISIBLE);
//        open_search_fragment_card_view = findViewById(R.id.open_search_fragment);
//        searchFragment = new SearchFragment();
//        selectedItemFragment = new SelectedItemFragment();
//
//        fragmentList = new ArrayList<>();  // initialization
//        drawerLayout =  findViewById(R.id.drawer_layout);
//
//        containerFrameLayout =  findViewById(R.id.activity_home_container);
//
//        final TextView TV_add_new_meal = findViewById(R.id.TV_add_new_meal);
//        TextView hanakoleh = findViewById(R.id.hanakolehTextView);
//        hanakoleh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, RandomMealsActivity.class);
//                startActivity(intent);
//            }
////
////            public void logOut(View view){
////                FirebaseAuth.getInstance().signOut();
////                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
////                finish();
////            }
//        });
//
//        Visablilety = isLoggedIn();
//        if (Visablilety == true) {
//            login_txt_btn.setVisibility(View.INVISIBLE);
//        }
//
//        onClickCardviews(breackfastCardView, "breakfast", 0);
//        onClickCardviews(launchCardView, "lunch", 0);
//        onClickCardviews(dinnerCardView, "dinner", 0);
//        onClickCardviews(favoritesCardView, "favorites", 1);
//        onClickCardviews(juicesCardView, "juices", 0);
//        onClickCardviews(dessertsCardView, "desserts", 0);
//
//
//        login_txt_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        TV_add_new_meal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, AddActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        ic_menu = findViewById(R.id.ic_menu);
//        ic_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////
//////                Bundle bundle = new Bundle();
//                FragmentSideMenu fragmentSideMenu = new FragmentSideMenu();
////////                UserProfileFragment fragmentSideMenu =new UserProfileFragment();
//////                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//////                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//////                transaction.replace(R.id.activity_home_container, fragmentSideMenu);
//////                transaction.commit();
////
//                addFragmentToView(fragmentSideMenu);
////                handleMenuAction();
//
//
//            }
//        });
//
//
//        open_search_fragment_card_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SearchFragment searchFragment = new SearchFragment();
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_up);
//                transaction.replace(R.id.activity_home_container, searchFragment);
//                transaction.commit();
//            }
//        });
//
//    }
//
//
//    public void onClickCardviews(CardView cardView, final String string, final int MEAL_FAVORITES_CONDITION) {
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragment = new ListMealsFragmentContainer(string);
//                Bundle bundle = new Bundle();
//                bundle.putInt("MEAL_FAVORITES_CONDITION", MEAL_FAVORITES_CONDITION);
//                FragmentTransaction(fragment, bundle);
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.activity_home_container, fragment);
//                transaction.commit();
//
//
//            }
//        });
//
//
//    }
//
//
//    private void FragmentTransaction(Fragment fragment, Bundle bundle) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.activity_home_container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//        fragment.setArguments(bundle);
//    }
//
//
//    public boolean isLoggedIn() {
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        return accessToken != null;
//    }
//
//
//    @Override
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        return false;
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
//
//
////    public String onGetOwnerName() {
//////        FirebaseAuth mFirebaseAuth;
//////        FirebaseUser mFirebaseUser;
//////        GoogleApiClient mGoogleApiClient;
////        String mUsername = "UserName";
////        try {
////            mGoogleApiClient = new GoogleApiClient.Builder(this)
////                    .enableAutoManage(this /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) this /* OnConnectionFailedListener */)
////                    .addApi(Auth.GOOGLE_SIGN_IN_API)
////                    .build();
////
////            mFirebaseAuth = FirebaseAuth.getInstance();
////            mFirebaseUser = mFirebaseAuth.getCurrentUser();
////            if (mFirebaseUser == null) {
////                // Not signed in, launch the Sign In activity
////            } else {
////                mUsername = mFirebaseUser.getDisplayName();
////
////            }
////        } catch (Exception e) {
////            mFirebaseAuth = FirebaseAuth.getInstance();
////            mFirebaseUser = mFirebaseAuth.getCurrentUser();
////            if (mFirebaseUser != null) {
////                mUsername = mFirebaseUser.getDisplayName();
////
////            }
////        }
////
////        return mUsername;
////    }
////    public String onGetOwnerEmail() {
//////        FirebaseAuth mFirebaseAuth;
//////        FirebaseUser mFirebaseUser;
//////        GoogleApiClient mGoogleApiClient;
////        String mUsername = "UserName";
////        try {
////            mGoogleApiClient = new GoogleApiClient.Builder(this)
////                    .enableAutoManage(this /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) this /* OnConnectionFailedListener */)
////                    .addApi(Auth.GOOGLE_SIGN_IN_API)
////                    .build();
////
////            mFirebaseAuth = FirebaseAuth.getInstance();
////            mFirebaseUser = mFirebaseAuth.getCurrentUser();
////            if (mFirebaseUser == null) {
////                // Not signed in, launch the Sign In activity
////            } else {
////                mUsername = mFirebaseUser.getEmail();
////
////            }
////        } catch (Exception e) {
////            mFirebaseAuth = FirebaseAuth.getInstance();
////            mFirebaseUser = mFirebaseAuth.getCurrentUser();
////            if (mFirebaseUser != null) {
////                mUsername = mFirebaseUser.getEmail();
////
////            }
////
////        }
////
////        return mUsername;
////    }
//
//
//    public BaseFragment getCurrentFragment() {
//        if (fragmentList.size() > 0) {
//            return fragmentList.get(fragmentList.size() - 1);
//        } else {
//            return null;
//        }
//    }
//
//    public void addFragmentToView(BaseFragment fragment) {
//        if (fragment != null) {
//            if (fragment.isFragmentAdded()) {
//                View currentView = null;
//                if (getCurrentFragment() != null) {
//                    currentView = getCurrentFragment().getView();
//                }
//                if (currentView != null) {
//                    currentView.setVisibility(View.GONE);
//                }
//                getSupportFragmentManager().beginTransaction().
//                        add(containerFrameLayout.getId(), fragment).commit();
//            } else
//                getSupportFragmentManager().beginTransaction().
//                        replace(containerFrameLayout.getId(), fragment).
//                        commitAllowingStateLoss();
//
//            fragmentList.add(fragment);
//
//
//        }
//    }
//
//
//    public void proceedWithBack() {
//        if (fragmentList.size() > 1) {
//
//            if (fragmentList.get(fragmentList.size() - 1).isFragmentAdded()) {
//                getSupportFragmentManager().beginTransaction().remove(fragmentList.get(fragmentList.size() - 1)).commit();
//                fragmentList.remove(fragmentList.size() - 1);
//                if (getCurrentFragment() != null) {
//                    if (getCurrentFragment().getView() != null) {
//                        getCurrentFragment().getView().setVisibility(View.VISIBLE);
//                    }
//                }
//            } else {
//                fragmentList.remove(fragmentList.size() - 1);
//                getSupportFragmentManager().beginTransaction().replace(R.id.activity_home_container, fragmentList.get(fragmentList.size() - 1)).commit();
//            }
//
//        } else {
//
//            if (this instanceof LoginActivity || this instanceof HomeActivity) {
//                moveTaskToBack(true);
//            } else {
//                super.onBackPressed();
//            }
//
//        }
//    }
//
//
//
//
//
//    private void handleMenuAction() {
//        toggleDrawer(false);
//    }
//
//    public void toggleDrawer(boolean isCloseDelayed) {
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//
//        if (conf.locale.getDisplayLanguage().toLowerCase().equals(new Locale(LANGUAGE_AR).getDisplayLanguage().toLowerCase())) {
//            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        drawerLayout.closeDrawer(Gravity.RIGHT);
//                    }
//                }, isCloseDelayed ? DRAWER_CLOSE_DELAY : 0);
//
//            } else {
//                drawerLayout.openDrawer(Gravity.RIGHT);
//            }
//        } else {
//            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        drawerLayout.closeDrawer(Gravity.LEFT);
//                    }
//                }, isCloseDelayed ? DRAWER_CLOSE_DELAY : 0);
//
//            } else {
//                drawerLayout.openDrawer(Gravity.LEFT);
//            }
//        }
//    }
//
//    protected void customizeSideMenu() {
//        userName = (TextView) findViewById(R.id.user_profile_name);
//        aboutUs = (LinearLayout) findViewById(R.id.my_post);
//        changeLanguage = (LinearLayout) findViewById(R.id.language_Linear);
//        logOut = (LinearLayout) findViewById(R.id.log_out_side_menu_linear_layout);
//
//
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
//        logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Controller.getInstance(HomeActivity.this).btnLogout("sasassa" , fragmentSideMenu , mGoogleApiClient);
//                Controller.getInstance(HomeActivity.this).showLogOutDialog(HomeActivity.this, true);
//            }
//        });
//    }
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() { super.onStop(); }
//
//    @Override
//    protected void onDestroy() { super.onDestroy(); }
//
//    @Override
//    protected void onPause() { super.onPause(); }
//
//    @Override
//    protected void onResume() { super.onResume(); }
//
//}









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









