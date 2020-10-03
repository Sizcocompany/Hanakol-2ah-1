package com.example.hanakol_2ah.user_interface;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.fragments.MyMeals;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import static com.example.hanakol_2ah.activities.HomeActivity.login_txt_btn;

public class FragmentSideMenu extends Fragment implements GoogleApiClient.OnConnectionFailedListener {
    private ImageView ic_menu_close;
    private ImageView user_profile_pic;
    private TextView user_profile_name;
    private TextView language;
    private TextView info;
    private View logout;
    private Toolbar toolbar;
    private  View myPosts ;

    private String mUsername;
    private String mEmail;
    private String mPhotoUrl;

    //    Authentication
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private GoogleApiClient mGoogleApiClient;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.side_menu_layout, container, false);


//    ImageViews
        ic_menu_close = view.findViewById(R.id.ic_menu_close);
        user_profile_pic = view.findViewById(R.id.user_profile_pic);
//     Toolbar
        toolbar = view.findViewById(R.id.toolBar);
//     Layout
        logout = view.findViewById(R.id.log_out_side_menu_linear_layout);
//        TextView
        user_profile_name = view.findViewById(R.id.user_profile_name);

        myPosts = view.findViewById(R.id.my_post);
        myPosts.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);
        //initialize Google Api Client
        try {


            mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                    .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API)
                    .build();

            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser == null) {
                // Not signed in, launch the Sign In activity
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
            } else {
                logout.setVisibility(View.VISIBLE);
                myPosts.setVisibility(View.VISIBLE);
                mUsername = mFirebaseUser.getDisplayName();
                if (mFirebaseUser.getPhotoUrl() != null) {
                    mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
                    Picasso.get().load(mPhotoUrl).into(user_profile_pic);
                }
                user_profile_name.setText(mUsername);


            }
        } catch (Exception e) {
            logout.setVisibility(View.VISIBLE);
            myPosts.setVisibility(View.VISIBLE);
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser != null) {
                mUsername = mFirebaseUser.getDisplayName();
                mEmail = mFirebaseUser.getEmail();
                if (mFirebaseUser.getPhotoUrl() != null) {
                    mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
                    Picasso.get().load(mPhotoUrl).into(user_profile_pic);
                }
                user_profile_name.setText(mUsername);
            } else {
                user_profile_name.setText("User Name");
                user_profile_pic.setImageResource(R.drawable.ic_user_pic);
                logout.setVisibility(View.INVISIBLE);
                myPosts.setVisibility(View.INVISIBLE);

            }
        }


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogout(mUsername);
            }
        });
        myPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                MyMeals myMeals = new MyMeals("breakfast" , mUsername );
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_home_container, myMeals);
                transaction.commit();
            }
        });

        ic_menu_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fragment_slide_in, R.anim.fragment_slide_in);
                transaction.hide(FragmentSideMenu.this);
                transaction.commit();
                FragmentSideMenu.this.onStop();


            }
        });


        return view;
    }


    public void btnLogout(String Username) {

        mFirebaseAuth.signOut();
        LoginManager.getInstance().logOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        login_txt_btn.setVisibility(View.VISIBLE);
        user_profile_pic.setImageResource(R.drawable.ic_user_pic);
        user_profile_name.setText("User Name");
        myPosts.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(), "See you later chief "+Username +":)", Toast.LENGTH_LONG).show();

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_slide_in, R.anim.fragment_slide_in);
        transaction.hide(FragmentSideMenu.this);
        transaction.commit();
        FragmentSideMenu.this.onStop();


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Toast.makeText(getActivity(), "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}