package com.example.hanakol_2ah.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.authentication.FacebookAuthenticationClass;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    //    Facebook Ziad
    private TextView info;
    private ImageView profile_picture;
    private LoginButton button_facebook_login;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private AccessTokenTracker accessTokenTracker;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final String FACEBOOK_TAG = "FacebookAuthentication";
    private FacebookAuthenticationClass facebookAuthenticationClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        profile_picture = findViewById(R.id.profile_picture);


//   start facebook methods
//  ------------------------------------------------------------------------------------------------
//        ziad
//  ------------------------------------------------------------------------------------------------
        FacebookSdk.sdkInitialize(getApplicationContext());
        button_facebook_login = findViewById(R.id.button_facebook_login);
        button_facebook_login.setReadPermissions("email", "public_profile");
        callbackManager = CallbackManager.Factory.create();

//   Creat object for FacebookAuthenticationClass
        facebookAuthenticationClass = new FacebookAuthenticationClass(FACEBOOK_TAG, firebaseAuth,
                LoginActivity.this, button_facebook_login, profile_picture, callbackManager);
//  ------------------------------------------------------------------------------------------------

//   FacebookRegisterationCallback
        facebookAuthenticationClass.FacebookRegisterationCallback();

//   GetAuthStateListener
        authStateListener = facebookAuthenticationClass.GetAuthStateListener();

//   GetAccessTokenTracker
        accessTokenTracker = facebookAuthenticationClass.GetAccessTokenTracker();
//  ------------------------------------------------------------------------------------------------
//   End of facebook methods


    }

// ziad
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

//ziad
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        facebookAuthenticationClass.updateUI(currentUser);
        firebaseAuth.addAuthStateListener(authStateListener);
    }
//ziad
    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }


}
