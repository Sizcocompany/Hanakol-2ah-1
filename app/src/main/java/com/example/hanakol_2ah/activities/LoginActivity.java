package com.example.hanakol_2ah.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.hanakol_2ah.authentication.FacebookAuthenticationClass;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // firebase Sabry
    ImageView logo;
    EditText user, password;
    Button button_firebase_sign_up;
    Button button_firebase_log_in;
    private static final String FIREBASE_TAG = "Login";
    String email = "14525", pass = "112";


    //    Facebook Ziad
    private TextView info;
    private ImageView profile;
    private LoginButton button_facebook_login;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private AccessTokenTracker accessTokenTracker;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final String FACEBOOK_TAG = "FacebookAuthentication";
    private FacebookAuthenticationClass facebookAuthenticationClass;
    private FirebaseAuthenticationClass firebaseAuthenticationClass;


    //  google Islam
    private int RC_SIGN_IN = 1;
    private SignInButton button_google_sign_in;
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

        profile = findViewById(R.id.email_profile);
        user = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        button_google_sign_in = findViewById(R.id.button_google_sign_in);
        button_firebase_sign_up = findViewById(R.id.button_firebase_sign_up);
        button_firebase_log_in = findViewById(R.id.button_firebase_log_in);
        button_facebook_login = findViewById(R.id.button_facebook_login);




        button_facebook_login.setReadPermissions("email", "public_profile");
        callbackManager = CallbackManager.Factory.create();



//
//        button_firebase_log_in.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String email = user.getText().toString();
//                String pass = password.getText().toString();
//
//                //if the field is empty
//                if (TextUtils.isEmpty(email)) {
//                    user.setError("Enter the Email");
//                    return;
//                }
//                if (TextUtils.isEmpty(pass)) {
//                    password.setError("Enter the Password");
//                    return;
//                }
//
//                //if the lenght is shorter than 8 characters
//                if (pass.length() < 8) {
//                    password.setError("The Password must be Longer than 8 Characters");
//                    return;
//                }
//
//                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//
//                        } else {
//                            Toast.makeText(LoginActivity.this, "ERROR" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//            }
//        });


//   Creat object for FacebookAuthenticationClass
        facebookAuthenticationClass = new FacebookAuthenticationClass(FACEBOOK_TAG, firebaseAuth,
                LoginActivity.this, button_facebook_login, profile, callbackManager);
//   creat object for FirebaseAuthenticationClass
//        firebaseAuthenticationClass = new FirebaseAuthenticationClass(FIREBASE_TAG, firebaseAuth,
//                LoginActivity.this, button_firebase_log_in, profile, callbackManager, pass, email);

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


////   start firebase methods
////  ------------------------------------------------------------------------------------------------
////   CreateUserWithEmailAndPassword
//        firebaseAuthenticationClass.CreateUserWithEmailAndPassword();
//
////   SignInWithEmailAndPassword
//        firebaseAuthenticationClass.SignInWithEmailAndPassword();
////  ------------------------------------------------------------------------------------------------
////        end firebase methods


//   start google methods
//  ------------------------------------------------------------------------------------------------
        googleAuthenticationClass = new GoogleAuthenticationClass(RC_SIGN_IN, button_google_sign_in, mGoogleSignInClient, firebaseAuth, getString(R.string.default_web_client_id), btnSignOut, LoginActivity.this);

//   getmGoogleSignInClient
//  ------------------------------------------------------------------------------------------------
        final GoogleSignInOptions googleSignInOptions = googleAuthenticationClass.getmGoogleSignInClient();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);


        button_google_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        });


//        button_firebase_sign_up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });


        btnSignOut.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                Toast.makeText( LoginActivity.this , "You are logged out ", Toast.LENGTH_SHORT ).show();
               btnSignOut.setVisibility( View.INVISIBLE );
            }
        } );

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> googleTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            googleAuthenticationClass.handleSingleResult(googleTask);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        facebookAuthenticationClass.updateUI(currentUser);
//        firebaseAuthenticationClass.updateUI(currentUser);

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
        startActivityForResult(signIngoogleIntent, RC_SIGN_IN);
    }


}










//public class LoginActivity extends AppCompatActivity {
//
//    // firebase Sabry
//    ImageView logo;
//    EditText user, password;
//    Button signIn;
//    private static final String FIREBASE_TAG = "Login";
//    String email = "14525", pass = "112";
//
//
//    //    Facebook Ziad
//    private TextView info;
//    private ImageView profile;
//    private LoginButton button_facebook_login;
//    private CallbackManager callbackManager;
//    private FirebaseAuth firebaseAuth;
//    private AccessTokenTracker accessTokenTracker;
//    private FirebaseAuth.AuthStateListener authStateListener;
//    private static final String FACEBOOK_TAG = "FacebookAuthentication";
//    private FacebookAuthenticationClass facebookAuthenticationClass;
//    private FirebaseAuthenticationClass firebaseAuthenticationClass;
//
//
//
//    //  google Islam
//    private int RC_SIGN_IN =1 ;
//    private SignInButton button_google_sign_in;
//    private GoogleSignInClient mGoogleSignInClient;
//    private String TAG = "MainActivity";
//    private Button btnSignOut;
//    GoogleAuthenticationClass googleAuthenticationClass;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        user = findViewById(R.id.editTextTextPersonName);
//        password = findViewById(R.id.editTextTextPassword);
//        signIn = findViewById(R.id.button_sign_in);
//        final Button button_firebase_sign_up = findViewById(R.id.button_firebase_sign_up);
////        info = findViewById(R.id.info_tv);
//        profile = findViewById(R.id.email_profile);
//        button_facebook_login = findViewById(R.id.button_facebook_login);
//        button_facebook_login.setReadPermissions("email", "public_profile");
//        callbackManager = CallbackManager.Factory.create();
////
//        button_google_sign_in = findViewById( R.id.button_google_sign_in );
//////        btnSignOut = findViewById( R.id.signout_btn );
//        googleAuthenticationClass = new GoogleAuthenticationClass(RC_SIGN_IN , button_google_sign_in, mGoogleSignInClient , firebaseAuth ,getString( R.string.default_web_client_id ), btnSignOut, LoginActivity.this );
////
////
////
////
////
//////   Creat object for FacebookAuthenticationClass
//        facebookAuthenticationClass = new FacebookAuthenticationClass(FACEBOOK_TAG, firebaseAuth,
//                LoginActivity.this, button_facebook_login, info, profile, callbackManager);
//////   creat object for FirebaseAuthenticationClass
////        firebaseAuthenticationClass = new FirebaseAuthenticationClass(FIREBASE_TAG , firebaseAuth ,
////                LoginActivity.this , signIn ,info , profile , callbackManager , pass , email );
////
//////   start facebook methods
//////  ------------------------------------------------------------------------------------------------
//////   FacebookRegisterationCallback
//        facebookAuthenticationClass.FacebookRegisterationCallback();
//
////   GetAuthStateListener
//        authStateListener = facebookAuthenticationClass.GetAuthStateListener();
//
////   GetAccessTokenTracker
//        accessTokenTracker = facebookAuthenticationClass.GetAccessTokenTracker();
//////  ------------------------------------------------------------------------------------------------
//////   End of facebook methods
////
////
//////   start firebase methods
//////  ------------------------------------------------------------------------------------------------
//////   CreateUserWithEmailAndPassword
////        firebaseAuthenticationClass.CreateUserWithEmailAndPassword();
////
//////   SignInWithEmailAndPassword
////        firebaseAuthenticationClass.SignInWithEmailAndPassword();
//////  ------------------------------------------------------------------------------------------------
//////        end firebase methods
////
////
////
//////   start google methods
//////  ------------------------------------------------------------------------------------------------
////
////
//////   getmGoogleSignInClient
//////  ------------------------------------------------------------------------------------------------
//        GoogleSignInOptions googleSignInOptions = googleAuthenticationClass.getmGoogleSignInClient();
//
//        mGoogleSignInClient = GoogleSignIn.getClient( this , googleSignInOptions );
//
//
//        button_google_sign_in.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                googleSignIn();
//                startActivity( new Intent(LoginActivity.this , HomeActivity.class ) );
//            }
//        } );
//
////        btnSignOut.setOnClickListener( new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                mGoogleSignInClient.signOut();
////                Toast.makeText( LoginActivity.this , "You are logged out ", Toast.LENGTH_SHORT ).show();
////               btnSignOut.setVisibility( View.INVISIBLE );
////            }
////        } );
//
//    }
////
////
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN){
//
//            Task<GoogleSignInAccount> googleTask = GoogleSignIn.getSignedInAccountFromIntent( data );
//            googleAuthenticationClass.handleSingleResult (googleTask);
//        }
//    }
////
////
////
//    @Override
//    protected void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        facebookAuthenticationClass.updateUI(currentUser);
////        firebaseAuthenticationClass.updateUI(currentUser);
//
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
//
//    private void googleSignIn() {
//        Intent signIngoogleIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(  signIngoogleIntent , RC_SIGN_IN );
//    }
//
//
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