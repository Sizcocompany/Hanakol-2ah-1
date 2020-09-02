package com.example.hanakol_2ah.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hanakol_2ah.authentication.FacebookAuthenticationClass;
import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.authentication.FirebaseAuthenticationClass;
import com.example.hanakol_2ah.authentication.GoogleAuthenticationClass;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // firebase
    ImageView logo;
    EditText user, password;
    Button signIn;
    private static final String FIREBASE_TAG = "Login";
    String email = "14525", pass = "112";


    //    Facebook
    private TextView info;
    private ImageView profile;
    private LoginButton login;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private AccessTokenTracker accessTokenTracker;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final String FACEBOOK_TAG = "FacebookAuthentication";
    private FacebookAuthenticationClass facebookAuthenticationClass;
    private FirebaseAuthenticationClass firebaseAuthenticationClass;



//  google
    private int RC_SIGN_IN =1 ;
    private SignInButton signInButton ;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "MainActivity";
    private Button btnSignOut;
    GoogleAuthenticationClass googleAuthenticationClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        user = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        signIn = findViewById(R.id.button_sign_in);
        final TextView signUp = findViewById(R.id.textView_sign_up);
        info = findViewById(R.id.info_tv);
        profile = findViewById(R.id.profile);
        login = findViewById(R.id.facebook_login_button);
        login.setReadPermissions("email", "public_profile");
        callbackManager = CallbackManager.Factory.create();

        signInButton = findViewById( R.id.sign_in_google );
//        btnSignOut = findViewById( R.id.signout_btn );
        googleAuthenticationClass = new GoogleAuthenticationClass(RC_SIGN_IN , signInButton , mGoogleSignInClient , firebaseAuth ,getString( R.string.default_web_client_id ), btnSignOut, LoginActivity.this );





//   Creat object for FacebookAuthenticationClass
       facebookAuthenticationClass = new FacebookAuthenticationClass(FACEBOOK_TAG, firebaseAuth,
               LoginActivity.this, login, info, profile, callbackManager);
//   creat object for FirebaseAuthenticationClass
       firebaseAuthenticationClass = new FirebaseAuthenticationClass(FIREBASE_TAG , firebaseAuth ,
               LoginActivity.this , signIn ,info , profile , callbackManager , pass , email );

//   start facebook methods
//  ------------------------------------------------------------------------------------------------
//   FacebookRegisterationCallback
        facebookAuthenticationClass.FacebookRegisterationCallback();

//   GetAuthStateListener
        authStateListener = facebookAuthenticationClass.GetAuthStateListener();

//   GetAccessTokenTracker
        accessTokenTracker = facebookAuthenticationClass.GetAccessTokenTracker();
//  ------------------------------------------------------------------------------------------------
//   End of facebook methods




//   start firebase methods
//  ------------------------------------------------------------------------------------------------
//   CreateUserWithEmailAndPassword
        firebaseAuthenticationClass.CreateUserWithEmailAndPassword();

//   SignInWithEmailAndPassword
        firebaseAuthenticationClass.SignInWithEmailAndPassword();
//  ------------------------------------------------------------------------------------------------
//        end firebase methods



//   start google methods
//  ------------------------------------------------------------------------------------------------


//   getmGoogleSignInClient
//  ------------------------------------------------------------------------------------------------
        GoogleSignInOptions googleSignInOptions = googleAuthenticationClass.getmGoogleSignInClient();

        mGoogleSignInClient = GoogleSignIn.getClient( this , googleSignInOptions );


        signInButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        } );

//        btnSignOut.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mGoogleSignInClient.signOut();
//                Toast.makeText( LoginActivity.this , "You are logged out ", Toast.LENGTH_SHORT ).show();
////                btnSignOut.setVisibility( View.INVISIBLE );
//            }
//        } );




    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){

            Task<GoogleSignInAccount> googleTask = GoogleSignIn.getSignedInAccountFromIntent( data );
            googleAuthenticationClass.handleSingleResult (googleTask);
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        facebookAuthenticationClass.updateUI(currentUser);
        firebaseAuthenticationClass.updateUI(currentUser);

        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    private void googleSignIn() {
        Intent signIngoogleIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(  signIngoogleIntent , RC_SIGN_IN );
    }


}
















//
//public class LoginActivity extends AppCompatActivity {
//
//    // firebase
//    ImageView logo;
//    EditText user, password;
//    Button signIn;
//    private static final String TAG1 = "Login";
//    String email="14525", pass="112";
//    private FirebaseAuth mAuth;
//
//
//    //    Facebook
//    private TextView info;
//    private ImageView profile;
//    private LoginButton login;
//    private CallbackManager callbackManager;
//    private FirebaseAuth firebaseAuth;
//    private AccessTokenTracker accessTokenTracker;
//    private FirebaseAuth.AuthStateListener authStateListener;
//    private static final String TAG2 = "FacebookAuthentication";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        mAuth = FirebaseAuth.getInstance();
//
//
//        logo = findViewById(R.id.logo_imageView);
//        user = findViewById(R.id.editTextTextPersonName);
//        password = findViewById(R.id.editTextTextPassword);
//        signIn = findViewById(R.id.button_sign_in);
//        final TextView signUp = findViewById(R.id.textView_sign_up);
//
//
//
//
//
//        info = findViewById(R.id.info_tv);
//        profile = findViewById(R.id.profile);
//        login = findViewById(R.id.facebook_login_button);
//        login.setReadPermissions("email", "public_profile");
//
//
//
////      start facebook methods
//        callbackManager = CallbackManager.Factory.create();
//        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG2, "onSuccess" + loginResult);
//                handleFacebookToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG2, "onCancel");
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG2, "onError");
//
//            }
//        });
//        authStateListener = new FirebaseAuth.AuthStateListener() {
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
//        accessTokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                if (currentAccessToken == null) {
//                    firebaseAuth.signOut();
//                }
//            }
//        };
//
////start firebase methods
//        mAuth.createUserWithEmailAndPassword(email, pass)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG1, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG1, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//
//        mAuth.signInWithEmailAndPassword(email, pass)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG1, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG1, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
////        end firebase methods
//
//    }
//
//    private void handleFacebookToken(AccessToken token) {
//        Log.d(TAG2, "handleFacebookToken" + token);
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Log.d(TAG2, "sign in with credential:successful");
//                    FirebaseUser user = firebaseAuth.getCurrentUser();
//                    updateUI(user);
//                } else {
//                    Log.d(TAG2, "sign in with credential:failure");
//                    Toast.makeText(LoginActivity.this, "Authentication failure", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void updateUI(FirebaseUser user) {
//        if (user != null) {
//            info.setText(user.getDisplayName());
//            if (user.getPhotoUrl() != null) {
//                String photoUrl = user.getPhotoUrl().toString();
//                photoUrl = photoUrl + "?type=large";
//                Picasso.get().load(photoUrl).into(profile);
//            }
//        } else {
//            info.setText(" ");
//            profile.setImageResource(R.drawable.com_facebook_auth_dialog_background);
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//        firebaseAuth.addAuthStateListener(authStateListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (authStateListener != null) {
//            firebaseAuth.removeAuthStateListener(authStateListener);
//        }
//    }
//}


//    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("hanakol-aeh").child("Meals");
//
//    Meals meals=new Meals();
//        meals.setName("كشري");
//                meals.setIngredients("1/2كارز\n"+
//                "1/2كشعيرية\n"+
//                "1/4حمصالشام\n"+
//                "1/4عدسبجبه\n"+
//                "1كمعكرونهصغيرة،سباغيتي\n"+
//                "عصيرطماطم\n"+
//                "ثوم\n"+
//                "بصل\n"+
//                "خل\n"+
//                "ملحوفلفلاسودحسبالرغبة\n"+
//                "كمون\n"+
//                "كزبرهناشفه");
//                meals.setSteps("1-ننقعالحمصقبلهابيوموبعديننسلقهونصفيهمنالماءونضععليهفصينثوممفرومينوملحوكمونونحتفظبماءالسلقلعملالدقه\n"+
//                "2-نسلقالعدسونصفيهمنالماءونضععليهملح،كمون،ثوممفروم\n"+
//                "3-نقطعالبصلجوانحونقلىفيزيتساخنيحمرنصفيهمنالزيتونطلعهعلىمناشفونضععليهرشةملح،كمون\n"+
//                "4-نسلقالمعكرونهفيماءبيغلىفيهملحوزيتولماتستوياصفيهامنالماءواشوحهافيبعضمنزيتالبصلواضععليهافلفلاسود\n"+
//                "5-لعملالارز.بعضمنزيتالبصلنشوحالشعيريةولماتحمرانزلبالارزالمغسولوازودماءوملحولمايستوياضععليهكميهمنالعدسواقلبهممعا\n"+
//                "6-نجهزصلصةالكشريشويهمنزيتالبصلنشوحالثومالمفرومومعلقهكبيرهكزبرهناشفهوعصيرالطماطمتغلىنضيفالملحوالكمونوالفلفلالاسودونتركهاحتىتتسبكوتتقل\n");
//
//
//                reference.push().setValue(meals);