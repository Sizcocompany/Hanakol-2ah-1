//package com.example.hanakol_2ah.authentication;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.example.hanakol_2ah.R;
//import com.example.hanakol_2ah.activities.HomeActivity;
//import com.example.hanakol_2ah.activities.LoginActivity;
//import com.facebook.AccessToken;
//import com.facebook.AccessTokenTracker;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FacebookAuthProvider;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.squareup.picasso.Picasso;
//
//public class FacebookAuthenticationClass {
//
//    final private String TAG;
//    final private FirebaseAuth firebaseAuth;
//    final private Context context;
//    final private LoginButton login;
//    final private ImageView profile;
//    final CallbackManager callbackManager;
//
//
//    public FacebookAuthenticationClass( String TAG, FirebaseAuth firebaseAuth,
//                                       Context context, LoginButton login,  ImageView profile, CallbackManager callbackManager ) {
//        this.TAG = TAG;
//        this.firebaseAuth = firebaseAuth;
//        this.context = context;
//        this.login = login;
//        this.profile = profile;
//        this.callbackManager = callbackManager;
//
//    }
//
//    public void FacebookRegisterationCallback() {
//
//        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "onSuccess" + loginResult);
//                handleFacebookToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "onCancel");
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG, "onError");
//
//            }
//        });
//
//    }
//
//    private void handleFacebookToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookToken" + token);
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Log.d(TAG, "sign in with credential:successful");
//                    FirebaseUser user = firebaseAuth.getCurrentUser();
//                    updateUI(user);
//                } else {
//                    Log.d(TAG, "sign in with credential:failure");
//                    Toast.makeText(context, "Authentication failure", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//    }
//
//
//    public FirebaseAuth.AuthStateListener GetAuthStateListener(){
//
//        return new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    updateUI(user);
//                } else {
//                    updateUI(null);
//                }
//            }
//        };
//    }
//
//    public   AccessTokenTracker GetAccessTokenTracker(){
//
//        return new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                if (currentAccessToken == null) {
//                    firebaseAuth.signOut();
//                }
//            }
//        };
//    }
//
//
//
//
//
//    public  void updateUI(FirebaseUser user) {
//        if (user != null) {
////            info.setText(user.getDisplayName());
//            if (user.getPhotoUrl() != null) {
//                String photoUrl = user.getPhotoUrl().toString();
//                photoUrl = photoUrl + "?type=large";
//                Picasso.get().load(photoUrl).into(profile);
////
//
//
//            }
//        } else {
////            info.setText(" ");
//            profile.setImageResource(R.drawable.com_facebook_auth_dialog_background);
//        }
//    }
//
//
//
//}
