package com.example.hanakol_2ah.user_interface;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
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
import com.example.hanakol_2ah.activities.HomeActivity;
import com.example.hanakol_2ah.fragments.LocalHelperLanguage;
import com.example.hanakol_2ah.fragments.MyMealsFragment;
import com.facebook.login.LoginManager;
import com.firebase.ui.auth.data.model.Resource;
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
    private TextView languageDialog;
    private TextView info;
    private View logout;
    private Toolbar toolbar;
    private  View myPosts ;
    boolean lang_slected = true ;
    Resources resources ;
    Context context ;

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

        languageDialog = view.findViewById( R.id.language_Linear );

        languageDialog.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] language  = {"English" , "غريس"};

                int checkItem ;

                if (lang_slected){
                    checkItem = 0;
                }else {

                    checkItem = 1;
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );

                builder.setTitle( "Select language" );
                builder.setSingleChoiceItems( language, checkItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        languageDialog.setText( language[which] );
                        if (language[which].equals( "English" )) {

                            context = LocalHelperLanguage.setLocale( getActivity(), "en" );
                            resources = context.getResources();

                        }
                        if (language[which].equals( "غريس" )) {

                            context = LocalHelperLanguage.setLocale( getActivity(), "egy" );
                            resources = context.getResources();

                        }

                    }
                } ).setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                } );

                builder.create().show();
            }
        } );

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
                user_profile_name.setText("User Name");
                user_profile_pic.setImageResource(R.drawable.ic_user_pic);
                logout.setVisibility(View.INVISIBLE);
                myPosts.setVisibility(View.INVISIBLE);
            } else {
                logout.setVisibility(View.VISIBLE);
                myPosts.setVisibility(View.VISIBLE);
                login_txt_btn.setVisibility(View.INVISIBLE);
//                mUsername = mFirebaseUser.getDisplayName();
//                if(mUsername ==null){
                    mUsername = mFirebaseUser.getEmail();
//                }
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
        mEmail = mFirebaseUser.getEmail();
        myPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                MyMealsFragment myMealsFragment = new MyMealsFragment("breakfast" , mEmail );
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//                transaction.replace(R.id.activity_home_container, myMealsFragment);
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