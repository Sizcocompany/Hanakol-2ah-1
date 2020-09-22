package com.example.hanakol_2ah.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.authentication.FacebookAuthenticationClass;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    // Islam
    public static final int GOOGLE_SIGN_IN_CODE = 1;
    SignInButton googleSignInbtn;
    GoogleSignInOptions gso;
    GoogleSignInClient signInClient;

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

        // start Gmail auth . islam



            googleSignInbtn = findViewById(R.id.button_google_sign_in);
//        firebaseAuth = FirebaseAuth.getInstance();

            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail().build();

            signInClient = GoogleSignIn.getClient(this, gso);

            GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);

            if (signInAccount != null || firebaseAuth.getCurrentUser() != null) {
                startActivity(new Intent(this, HomeActivity.class));
            }

            googleSignInbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signin = signInClient.getSignInIntent();

                    startActivityForResult(signin, GOOGLE_SIGN_IN_CODE);
                }
            });

    }

    // ziad
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // islam

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
                            updateUI(user);
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));

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


    private void updateUI (FirebaseUser fUser){
       // logout.setVisibility( View.VISIBLE );
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount( getApplicationContext() );
        if (account != null){

            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            Toast.makeText( LoginActivity.this , personName + personEmail  , Toast.LENGTH_SHORT).show();

        }
    }

}
