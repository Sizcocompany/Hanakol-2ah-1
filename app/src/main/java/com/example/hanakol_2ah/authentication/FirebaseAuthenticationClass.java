package com.example.hanakol_2ah.authentication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hanakol_2ah.R;
import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class FirebaseAuthenticationClass {

    final private String TAG;
    final private FirebaseAuth firebaseAuth;
    final private Context context;
    final private Button login;
    final private ImageView profile;
    final private CallbackManager callbackManager;
    final private String PASSWORD, USERNAME;


    public FirebaseAuthenticationClass(String TAG, FirebaseAuth firebaseAuth, Context context,
                                       Button login, ImageView profile,
                                       CallbackManager callbackManager, String PASSWORD, String USERNAME) {
        this.TAG = TAG;
        this.firebaseAuth = firebaseAuth;
        this.context = context;
        this.login = login;
        this.profile = profile;
        this.callbackManager = callbackManager;
        this.PASSWORD = PASSWORD;
        this.USERNAME = USERNAME;
    }

    public void CreateUserWithEmailAndPassword(){

        firebaseAuth.createUserWithEmailAndPassword(USERNAME, PASSWORD)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText((Activity)context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }
    public void SignInWithEmailAndPassword(){
        firebaseAuth.signInWithEmailAndPassword(USERNAME, PASSWORD)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText((Activity)context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }





    public  void updateUI(FirebaseUser user) {

        if (user != null) {
//            info.setText(user.getDisplayName());
            if (user.getPhotoUrl() != null) {
                String photoUrl = user.getPhotoUrl().toString();
                photoUrl = photoUrl + "?type=large";
                Picasso.get().load(photoUrl).into(profile);
            }
        } else {
//            info.setText(" ");
            profile.setImageResource(R.drawable.com_facebook_auth_dialog_background);
        }
    }

}
