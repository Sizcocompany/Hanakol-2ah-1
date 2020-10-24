package com.example.hanakol_2ah.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.authentication.FacebookAuthenticationClass;
import com.example.hanakol_2ah.authentication.FirebaseAuthenticationClass;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends HomeBaseActivity {

    // Islam
    public static final int GOOGLE_SIGN_IN_CODE = 1;
    private static final String FACEBOOK_TAG = "FacebookAuthentication";
    SignInButton googleSignInBtn;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    GoogleApiClient googleApiClient;

    //    Facebook Ziad
    private ImageView profile_picture;
    private LoginButton button_facebook_login;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private String TAG = "FacebookAuthentication";
    private AccessTokenTracker accessTokenTracker;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    public FirebaseUser firebaseUser;


    //    sba3ba3
    private Button logIn, signUp;
    private EditText firebaseEmail, firebasePass;
    private TextView forgetPass;

    ImageView closeIcon;
    private FacebookAuthenticationClass facebookAuthenticationClass;
    //    private GoogleAuthenticationClass googleAuthenticationClass;
    private FirebaseAuthenticationClass firebaseAuthenticationClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        closeIcon = findViewById(R.id.close_btn_login);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeBaseActivity.class);
                startActivity(intent);
            }
        });

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

//   Creat object for googleAuthenticationClass

//        googleAuthenticationClass = new GoogleAuthenticationClass(googleSignInBtn, googleSignInOptions
//                , googleSignInClient, googleApiClient, LoginActivity.this, firebaseAuth);
//  ------------------------------------------------------------------------------------------------

//   Creat object for firebaseAuthenticationClass

        firebaseAuthenticationClass = new FirebaseAuthenticationClass(logIn, signUp, firebaseEmail
                , firebasePass, forgetPass, firebaseAuth, LoginActivity.this);
//  ------------------------------------------------------------------------------------------------


//   FacebookRegisterationCallback
        facebookAuthenticationClass.facebookRegisterationCallback(LoginActivity.this, onGetOwnerName(firebaseAuth, googleApiClient));

//   GetAuthStateListener
        firebaseAuthStateListener = facebookAuthenticationClass.GetAuthStateListener();

//   GetAccessTokenTracker
        accessTokenTracker = facebookAuthenticationClass.GetAccessTokenTracker();
//  ------------------------------------------------------------------------------------------------
//   End of facebook methods


        // start Gmail auth . islam


        googleSignInBtn = findViewById(R.id.button_google_sign_in);

//        googleSignInOptions = googleAuthenticationClass.getGoogleSignInOptions(getString(R.string.default_web_client_id));
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

//        googleSignInClient = googleAuthenticationClass.getGoogleSignInClient(googleSignInOptions);
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

//        GoogleSignInAccount signInGoogleAccount = googleAuthenticationClass.getGoogleSignInAccount();


        googleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = googleSignInClient.getSignInIntent();

                startActivityForResult(signin, GOOGLE_SIGN_IN_CODE);
            }
        });

//        sba3ba3


        logIn = findViewById(R.id.button_firebase_log_in);
        signUp = findViewById(R.id.button_firebase_sign_up);
        firebaseEmail = findViewById(R.id.editTextTextPersonName);
        firebasePass = findViewById(R.id.editTextTextPassword);
        forgetPass = findViewById(R.id.forget);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = firebaseEmail.getText().toString().trim();
                String password = firebasePass.getText().toString().trim();

                firebaseAuthenticationClass.checkEmailAndPassword(email, password);

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        firebaseAuthenticationClass.setLogIn(task);
                    }
                });


            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                firebaseAuthenticationClass.setSignUp();
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuthenticationClass.setForgetPass(v);
            }
        });
    }


    // ziad
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // islam

//        if (requestCode == GOOGLE_SIGN_IN_CODE) {
//
//            Task<GoogleSignInAccount> signInTask = googleAuthenticationClass.googleSignInAccountTask(data);
//
//            googleAuthenticationClass.signIn(signInTask);
//        }


        if (requestCode == GOOGLE_SIGN_IN_CODE) {

            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount signInacc = signInTask.getResult(ApiException.class);

                AuthCredential authCredential = GoogleAuthProvider.getCredential(signInacc.getIdToken(), null);

                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Toast.makeText(getApplicationContext(), "Signed In successfully ", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
//                        updateUI(user);
                        startActivity(new Intent(getApplicationContext(), HomeBaseActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error happened please try again alter ", Toast.LENGTH_SHORT).show();
            }
        }


    }


    //ziad
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        updateUI(firebaseAuth.getCurrentUser());
        facebookAuthenticationClass.updateUI(currentUser);
//        googleAuthenticationClass.updateUI(currentUser);
        firebaseAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    //ziad
    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthStateListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthStateListener);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, HomeBaseActivity.class);
        startActivity(intent);
    }
}
