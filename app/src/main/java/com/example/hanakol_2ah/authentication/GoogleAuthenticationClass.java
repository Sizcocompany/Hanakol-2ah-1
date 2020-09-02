package com.example.hanakol_2ah.authentication;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class GoogleAuthenticationClass {

    private int RC_SIGN_IN =1 ;
    private SignInButton signInButton ;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private String R_string_default_web_client_id;
    private Button btnSignOut;
    private Context context;


    public GoogleAuthenticationClass(int RC_SIGN_IN, SignInButton signInButton, GoogleSignInClient mGoogleSignInClient, FirebaseAuth mAuth, String R_string_default_web_client_id, Button btnSignOut, Context context) {
        this.RC_SIGN_IN = RC_SIGN_IN;
        this.signInButton = signInButton;
        this.mGoogleSignInClient = mGoogleSignInClient;
        this.mAuth = mAuth;
        this.R_string_default_web_client_id = R_string_default_web_client_id;
        this.btnSignOut = btnSignOut;
        this.context = context;
    }

    public GoogleSignInOptions getmGoogleSignInClient(){

        return new GoogleSignInOptions.Builder( GoogleSignInOptions.DEFAULT_SIGN_IN )
                .requestIdToken(R_string_default_web_client_id)
                .requestEmail().build();
    }
    public void handleSingleResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount acc = completedTask.getResult( ApiException.class );

            Toast.makeText( context , "Signed in Successfully", Toast.LENGTH_SHORT ).show();
            FirebaseGoogleAuth(acc);

        } catch (ApiException e) {
            Toast.makeText( context , "Signed in Failed please try again ", Toast.LENGTH_SHORT ).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct ) {

        if (acct != null) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential( acct.getIdToken(), null );
            mAuth.signInWithCredential( authCredential ).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText( context, "Successfully", Toast.LENGTH_SHORT ).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI( user );
                    } else {

                        Toast.makeText( context, "Failed", Toast.LENGTH_SHORT ).show();
                        updateUI( null );
                    }
                }
            } );
        } else {

            Toast.makeText( context, "acc failed", Toast.LENGTH_SHORT ).show();
        }
    }

    public void updateUI(FirebaseUser fUser) {

        btnSignOut.setVisibility( View.VISIBLE );
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount( context );
        if(account != null){

            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            Toast.makeText( context , personName + personEmail , Toast.LENGTH_LONG ).show();

        }
    }

}
