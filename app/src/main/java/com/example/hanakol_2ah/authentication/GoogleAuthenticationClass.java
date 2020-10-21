package com.example.hanakol_2ah.authentication;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hanakol_2ah.activities.HomeBaseActivity;
import com.example.hanakol_2ah.activities.LoginActivity;
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

public class GoogleAuthenticationClass{

    private static final int GOOGLE_SIGN_IN_CODE = 1;
    private static final String FACEBOOK_TAG = "FacebookAuthentication";
    private SignInButton googleSignInBtn;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;
    private GoogleApiClient googleApiClient;
    private Context context;
    private FirebaseAuth firebaseAuth;


    public GoogleAuthenticationClass(SignInButton googleSignInBtn, GoogleSignInOptions googleSignInOptions, GoogleSignInClient googleSignInClient, GoogleApiClient googleApiClient, Context context, FirebaseAuth firebaseAuth) {
        this.googleSignInBtn = googleSignInBtn;
        this.googleSignInOptions = googleSignInOptions;
        this.googleSignInClient = googleSignInClient;
        this.googleApiClient = googleApiClient;
        this.context = context;
        this.firebaseAuth = firebaseAuth;
    }

    public GoogleSignInOptions getGoogleSignInOptions(String default_web_client_id){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(default_web_client_id)
                .requestEmail().build();
    }
    public GoogleSignInClient getGoogleSignInClient(GoogleSignInOptions googleSignInOptions){
        return GoogleSignIn.getClient(context, googleSignInOptions);
    }
    public GoogleSignInAccount getGoogleSignInAccount(){
        return GoogleSignIn.getLastSignedInAccount(context);
    }

    public Task<GoogleSignInAccount> googleSignInAccountTask(Intent data ){
        return  GoogleSignIn.getSignedInAccountFromIntent(data);
    }
    public void signIn(Task<GoogleSignInAccount> signInTask){
        try {
            GoogleSignInAccount signInacc = signInTask.getResult(ApiException.class);

            AuthCredential authCredential = GoogleAuthProvider.getCredential(signInacc.getIdToken(), null);

            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    Toast.makeText(context.getApplicationContext(), "Signed In successfully ", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
//                    updateUI(user);
                    context.startActivity(new Intent(context.getApplicationContext(), HomeBaseActivity.class));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        } catch (ApiException e) {
        }
    }

//    public void updateUI(FirebaseUser user) {
//
//
//
//        // logout.setVisibility( View.VISIBLE );
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context.getApplicationContext());
//        if (account != null) {
//
//            String personName = account.getDisplayName();
////            String personGivenName = account.getGivenName();
////            String personFamilyName = account.getFamilyName();
//            String personEmail = account.getEmail();
////            String personId = account.getId();
////            Uri personPhoto = account.getPhotoUrl();
////            login_txt_btn.setText("Hi! "+personName);
////            login_txt_btn.setClickable(false);
//            Toast.makeText(context, personName + "\n" + personEmail, Toast.LENGTH_LONG).show();
//
//        }
//    }

}