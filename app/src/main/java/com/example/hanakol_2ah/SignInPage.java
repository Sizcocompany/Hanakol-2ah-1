package com.example.hanakol_2ah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInPage extends AppCompatActivity {

    private int RC_SIGN_IN =1 ;
    private SignInButton signInButton ;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private String TAG = "MainActivity";
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_in_page );

        signInButton = findViewById( R.id.sign_in_google );
        mAuth = FirebaseAuth.getInstance();
        btnSignOut = findViewById( R.id.signout_btn );


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder( GoogleSignInOptions.DEFAULT_SIGN_IN )
                .requestIdToken(getString( R.string.default_web_client_id ))
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient( this , gso );

        signInButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        } );

        btnSignOut.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                Toast.makeText( SignInPage.this , "You are logged out ", Toast.LENGTH_SHORT ).show();
                btnSignOut.setVisibility( View.INVISIBLE );
            }
        } );

    }

    private void googleSignIn() {
        Intent signIngoogleIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(  signIngoogleIntent , RC_SIGN_IN );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == RC_SIGN_IN){

            Task <GoogleSignInAccount> googleTask = GoogleSignIn.getSignedInAccountFromIntent( data );
            handleSingleResult (googleTask);
        }
    }

    private void handleSingleResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount acc = completedTask.getResult( ApiException.class );

            Toast.makeText( SignInPage.this , "Signed in Successfully", Toast.LENGTH_SHORT ).show();
            FirebaseGoogleAuth(acc);

        } catch (ApiException e) {
            Toast.makeText( SignInPage.this , "Signed in Failed please try again ", Toast.LENGTH_SHORT ).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct ) {

        if (acct != null) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential( acct.getIdToken(), null );
            mAuth.signInWithCredential( authCredential ).addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText( SignInPage.this, "Successfully", Toast.LENGTH_SHORT ).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI( user );
                    } else {

                        Toast.makeText( SignInPage.this, "Failed", Toast.LENGTH_SHORT ).show();
                        updateUI( null );
                    }
                }
            } );
        } else {

            Toast.makeText( SignInPage.this, "acc failed", Toast.LENGTH_SHORT ).show();
        }
    }
    private void updateUI(FirebaseUser fUser) {

        btnSignOut.setVisibility( View.VISIBLE );
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount( getApplicationContext() );
        if(account != null){

            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            Toast.makeText( SignInPage.this , personName + personEmail , Toast.LENGTH_LONG ).show();

        }
    }
}