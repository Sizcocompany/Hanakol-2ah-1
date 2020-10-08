package com.example.hanakol_2ah.control;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.user_interface.FragmentSideMenu;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.hanakol_2ah.activities.HomeActivity.login_txt_btn;

public class Controller {

//    private User loggedUser;
    private Activity currentActivity;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_OPEN_CAMERA = 124;
    public static final int MY_PERMISSIONS_REQUEST_TELEPHONE_STATE = 125;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private GoogleApiClient mGoogleApiClient;







    private static Controller instance;

    public static Controller getInstance(Context context) {
        if (instance == null) {
            instance = new Controller();
//            dbHelper = new DBHelper(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
        }
        return instance;
    }


    public static Controller getInstance() {

        return instance;
    }
    private Controller() {
    }

    public static void adjustDialogWidth(Context context, Dialog dialog) {
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int dialogWidth = (int) (screenWidth * .9f);
        dialog.getWindow().setLayout(dialogWidth, dialog.getWindow().getAttributes().height);
    }


    public void showLogOutDialog(final Activity activity, final boolean closeActivity) {

        final Dialog innerAlertDialog = new Dialog(activity);
        innerAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        innerAlertDialog.setContentView(R.layout.custom_dialog_layout);
        adjustDialogWidth(activity, innerAlertDialog);
        TextView title = (TextView) innerAlertDialog
                .findViewById(R.id.custom_dialog_title_id);
        title.setText(R.string.app_name);
        TextView messageTXT = (TextView) innerAlertDialog
                .findViewById(R.id.custom_dialog_message_id);
        messageTXT.setText("Are you sure you want to Logout?");
        innerAlertDialog.setCancelable(false);
        Button ok_btn = (Button) innerAlertDialog
                .findViewById(R.id.custom_dialog_okBtn_id);
        ok_btn.setText(R.string.OK);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (closeActivity) {
                    Toast.makeText(activity, "see you later", Toast.LENGTH_LONG).show();
                    innerAlertDialog.dismiss();
                }
            }

        });

        Button cancel_btn = (Button) innerAlertDialog
                .findViewById(R.id.custom_dialog_cancelBtn_id);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                innerAlertDialog.dismiss();


            }
        });

        ok_btn.setAllCaps(true);
        cancel_btn.setAllCaps(true);

        innerAlertDialog.show();

    }

    public void btnLogout(String Username , FragmentSideMenu fragmentSideMenu , GoogleApiClient googleApiClient) {
//
//        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
//                .enableAutoManage(getActivity() /* FragmentActivity */, fragmentSideMenu /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API)
//                .build();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();



        mFirebaseAuth.signOut();
        LoginManager.getInstance().logOut();
//        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        login_txt_btn.setVisibility(View.VISIBLE);
//        user_profile_pic.setImageResource(R.drawable.ic_user_pic);
//        user_profile_name.setText("User Name");
//        View aboutUs = (LinearLayout) findViewById(R.id.my_post);
//        myPosts.setVisibility(View.INVISIBLE);
//        Toast.makeText(fragmentSideMenu.getActivity(), "See you later chief " + Username + ":)", Toast.LENGTH_LONG).show();

        fragmentSideMenu.onStop();

    }
}
