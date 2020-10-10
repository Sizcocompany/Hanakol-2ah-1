package com.example.hanakol_2ah.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hanakol_2ah.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

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
    private String TAG = "FacebookAuthentication";
    private AccessTokenTracker accessTokenTracker;
    private FirebaseAuth.AuthStateListener authStateListener;
    public FirebaseUser firebaseUser;
    GoogleApiClient googleApiClient;
    private String TAG3 = "LoginActivity";
    private static final String FACEBOOK_TAG = "FacebookAuthentication";


    //    sba3ba3
    private Button logIn, signUp;
    private EditText firebaseEmail, firebasePass;
    private TextView forgetPass;


//    private FacebookAuthenticationClass facebookAuthenticationClass;


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

//        facebookAuthenticationClass = new FacebookAuthenticationClass(FACEBOOK_TAG, firebaseAuth,
//                LoginActivity.this, button_facebook_login, profile_picture, callbackManager);
////  ------------------------------------------------------------------------------------------------
//
//
////   FacebookRegisterationCallback
//        facebookAuthenticationClass.FacebookRegisterationCallback();
//
////   GetAuthStateListener
//        authStateListener = facebookAuthenticationClass.GetAuthStateListener();
//
////   GetAccessTokenTracker
//        accessTokenTracker = facebookAuthenticationClass.GetAccessTokenTracker();
////  ------------------------------------------------------------------------------------------------
////   End of facebook methods


        button_facebook_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess" + loginResult);
                handleFacebookToken(loginResult.getAccessToken());
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                Toast.makeText(LoginActivity.this, "Welcome chief " + onGetOwnerName(firebaseAuth, googleApiClient), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError");

            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    firebaseUser = user;
                    updateUI(user);
                } else {
                    updateUI(null);
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    firebaseAuth.signOut();
                }
            }
        };


        // start Gmail auth . islam


        googleSignInbtn = findViewById(R.id.button_google_sign_in);
//        firebaseAuth = FirebaseAuth.getInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        signInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount signInGoogleAccount = GoogleSignIn.getLastSignedInAccount(this);

        if (signInGoogleAccount != null || firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, HomeActivity.class));
        }

        googleSignInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = signInClient.getSignInIntent();

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

                if (TextUtils.isEmpty(email)) {
                    firebaseEmail.setError("Email Field is Empty");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    firebasePass.setError("Password Field is Empty");
                    return;
                }
                if (password.length() < 8) {
                    firebaseEmail.setError("Please enter 8 Characters or more");
                    return;
                }


                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Successful LogIn", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "ERROR" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passReset = new AlertDialog.Builder(v.getContext());
                passReset.setTitle("Reset Password !!");
                passReset.setMessage("Enter your Email to Recieve the Reset Link");
                passReset.setView(resetMail);

                passReset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetMail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this, "Reset Email was Sent", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "ERROR !!" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });
                passReset.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                passReset.create().show();

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
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                Log.d(TAG, e.toString());
            }
        }
    }

    //ziad
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(firebaseAuth.getCurrentUser());
//        facebookAuthenticationClass.updateUI(currentUser);
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


    private void updateUI(FirebaseUser user) {


        if (user != null) {
//            info.setText(user.getDisplayName());
            if (user.getPhotoUrl() != null) {
                String photoUrl = user.getPhotoUrl().toString();
                photoUrl = photoUrl + "?type=large";
                Picasso.get().load(photoUrl).into(profile_picture);
//                login_txt_btn.setText("Hi! "+user.getDisplayName());
//                login_txt_btn.setClickable(false);


            }
        } else {
//            info.setText(" ");
            profile_picture.setImageResource(R.drawable.com_facebook_auth_dialog_background);
        }


        // logout.setVisibility( View.VISIBLE );
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {

            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
//            login_txt_btn.setText("Hi! "+personName);
//            login_txt_btn.setClickable(false);
            Toast.makeText(LoginActivity.this, personName + personEmail, Toast.LENGTH_SHORT).show();

        }
    }


//    facebook

    private void handleFacebookToken(AccessToken token) {
        Log.d(TAG, "handleFacebookToken" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "sign in with credential:successful");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    Log.d(TAG, "sign in with credential:failure");
                    Toast.makeText(LoginActivity.this, "Authentication failure", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private String onGetOwnerName(FirebaseAuth firebaseAuth, GoogleApiClient googleApiClient) {
        FirebaseAuth mFirebaseAuth2 = firebaseAuth;
        FirebaseUser mFirebaseUser;
        GoogleApiClient mGoogleApiClient = googleApiClient;
        String mUsername = "UserName";
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API)
                    .build();

            mFirebaseAuth2 = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth2.getCurrentUser();
            if (mFirebaseUser == null) {
                mUsername = "Dear chief";
                // Not signed in, launch the Sign In activity
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
            } else {
                mUsername = mFirebaseUser.getDisplayName();

            }
        } catch (Exception e) {
            mFirebaseAuth2 = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth2.getCurrentUser();
            if (mFirebaseUser != null) {
                mUsername = mFirebaseUser.getDisplayName();

            }
        }

        return mUsername;
    }


}


//package com.example.hanakol_2ah.activities;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.hanakol_2ah.R;
//import com.facebook.AccessToken;
//import com.facebook.AccessTokenTracker;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FacebookAuthProvider;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.GoogleAuthProvider;
//import com.squareup.picasso.Picasso;
//
//import static com.example.hanakol_2ah.activities.HomeActivity.login_txt_btn;
//
//public class LoginActivity extends AppCompatActivity {
//
//    // Islam
//    public static final int GOOGLE_SIGN_IN_CODE = 1;
//    SignInButton googleSignInbtn;
//    GoogleSignInOptions gso;
//    GoogleSignInClient signInClient;
//
//    //    Facebook Ziad
//    private TextView info;
//    private ImageView profile_picture;
//    private LoginButton button_facebook_login;
//    private CallbackManager callbackManager;
//    private FirebaseAuth firebaseAuth;
//    private String TAG = "FacebookAuthentication";
//    private AccessTokenTracker accessTokenTracker;
//    private FirebaseAuth.AuthStateListener authStateListener;
//    public FirebaseUser firebaseUser;
//    GoogleApiClient googleApiClient;
//    private static final String FACEBOOK_TAG = "FacebookAuthentication";
////    private FacebookAuthenticationClass facebookAuthenticationClass;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        profile_picture = findViewById(R.id.profile_picture);
//
//
////   start facebook methods
////  ------------------------------------------------------------------------------------------------
////        ziad
////  ------------------------------------------------------------------------------------------------
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        button_facebook_login = findViewById(R.id.button_facebook_login);
//        button_facebook_login.setReadPermissions("email", "public_profile");
//        callbackManager = CallbackManager.Factory.create();
//
////   Creat object for FacebookAuthenticationClass
//
////        facebookAuthenticationClass = new FacebookAuthenticationClass(FACEBOOK_TAG, firebaseAuth,
////                LoginActivity.this, button_facebook_login, profile_picture, callbackManager);
//////  ------------------------------------------------------------------------------------------------
////
////
//////   FacebookRegisterationCallback
////        facebookAuthenticationClass.FacebookRegisterationCallback();
////
//////   GetAuthStateListener
////        authStateListener = facebookAuthenticationClass.GetAuthStateListener();
////
//////   GetAccessTokenTracker
////        accessTokenTracker = facebookAuthenticationClass.GetAccessTokenTracker();
//////  ------------------------------------------------------------------------------------------------
//////   End of facebook methods
//
//
//
//        button_facebook_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "onSuccess" + loginResult);
//                handleFacebookToken(loginResult.getAccessToken());
//                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                Toast.makeText(LoginActivity.this, "Welcome chief "+onGetOwnerName(firebaseAuth , googleApiClient), Toast.LENGTH_SHORT).show();
//                startActivity(intent);
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
//     authStateListener =new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    firebaseUser = user;
//                    updateUI(user);
//                } else {
//                    updateUI(null);
//                }
//            }
//        };
//
//     accessTokenTracker = new AccessTokenTracker() {
//         @Override
//         protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//             if (currentAccessToken == null) {
//                 firebaseAuth.signOut();
//             }
//         }
//     };
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        // start Gmail auth . islam
//
//
//
//        googleSignInbtn = findViewById(R.id.button_google_sign_in);
////        firebaseAuth = FirebaseAuth.getInstance();
//
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail().build();
//
//        signInClient = GoogleSignIn.getClient(this, gso);
//
//        GoogleSignInAccount signInGoogleAccount = GoogleSignIn.getLastSignedInAccount(this);
//
//        if (signInGoogleAccount != null || firebaseAuth.getCurrentUser() != null) {
//            startActivity(new Intent(this, HomeActivity.class));
//        }
//
//        googleSignInbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent signin = signInClient.getSignInIntent();
//
//                startActivityForResult(signin, GOOGLE_SIGN_IN_CODE);
//            }
//        });
//
//    }
//
//
//    // ziad
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // islam
//
//        if (requestCode == GOOGLE_SIGN_IN_CODE) {
//
//            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(data);
//
//            try {
//                GoogleSignInAccount signInacc = signInTask.getResult(ApiException.class);
//
//                AuthCredential authCredential = GoogleAuthProvider.getCredential(signInacc.getIdToken(), null);
//
//                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        Toast.makeText(getApplicationContext(), "Signed In successfully ", Toast.LENGTH_SHORT).show();
//                        FirebaseUser user = firebaseAuth.getCurrentUser();
//                        updateUI(user);
//                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
//
//            } catch (ApiException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Error happened please try again alter ", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
////ziad
//    @Override
//    protected void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        updateUI(firebaseAuth.getCurrentUser());
////        facebookAuthenticationClass.updateUI(currentUser);
//        firebaseAuth.addAuthStateListener(authStateListener);
//    }
////ziad
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (authStateListener != null) {
//            firebaseAuth.removeAuthStateListener(authStateListener);
//        }
//    }
//
//
//    private void updateUI (FirebaseUser user){
//
//
//
//        if (user != null) {
////            info.setText(user.getDisplayName());
//            if (user.getPhotoUrl() != null) {
//                String photoUrl = user.getPhotoUrl().toString();
//                photoUrl = photoUrl + "?type=large";
//                Picasso.get().load(photoUrl).into(profile_picture);
////                login_txt_btn.setText("Hi! "+user.getDisplayName());
////                login_txt_btn.setClickable(false);
//
//
//            }
//        } else {
////            info.setText(" ");
//            profile_picture.setImageResource(R.drawable.com_facebook_auth_dialog_background);
//        }
//
//
//
//
//
//        // logout.setVisibility( View.VISIBLE );
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount( getApplicationContext() );
//        if (account != null){
//
//            String personName = account.getDisplayName();
//            String personGivenName = account.getGivenName();
//            String personFamilyName = account.getFamilyName();
//            String personEmail = account.getEmail();
//            String personId = account.getId();
//            Uri personPhoto = account.getPhotoUrl();
////            login_txt_btn.setText("Hi! "+personName);
////            login_txt_btn.setClickable(false);
//            Toast.makeText( LoginActivity.this , personName + personEmail  , Toast.LENGTH_SHORT).show();
//
//        }
//    }
//
//
//
////    facebook
//
//    private void handleFacebookToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookToken" + token);
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Log.d(TAG, "sign in with credential:successful");
//                    FirebaseUser user = firebaseAuth.getCurrentUser();
//                    updateUI(user);
//                } else {
//                    Log.d(TAG, "sign in with credential:failure");
//                    Toast.makeText(LoginActivity.this, "Authentication failure", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//    }
//
//    private String onGetOwnerName(FirebaseAuth firebaseAuth , GoogleApiClient googleApiClient) {
//        FirebaseAuth mFirebaseAuth2 = firebaseAuth;
//        FirebaseUser mFirebaseUser;
//        GoogleApiClient mGoogleApiClient = googleApiClient;
//        String mUsername = "UserName";
//        try {
//            mGoogleApiClient = new GoogleApiClient.Builder(this)
//                    .enableAutoManage(this /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) this /* OnConnectionFailedListener */)
//                    .addApi(Auth.GOOGLE_SIGN_IN_API)
//                    .build();
//
//            mFirebaseAuth2 = FirebaseAuth.getInstance();
//            mFirebaseUser = mFirebaseAuth2.getCurrentUser();
//            if (mFirebaseUser == null) {
//                mUsername = "Dear chief";
//                // Not signed in, launch the Sign In activity
////            startActivity(new Intent(this, LoginActivity.class));
////            finish();
//            } else {
//                mUsername = mFirebaseUser.getDisplayName();
//
//            }
//        } catch (Exception e) {
//            mFirebaseAuth2 = FirebaseAuth.getInstance();
//            mFirebaseUser = mFirebaseAuth2.getCurrentUser();
//            if (mFirebaseUser != null) {
//                mUsername = mFirebaseUser.getDisplayName();
//
//            }
//        }
//
//        return mUsername;
//    }
//
//
//}
