package com.example.hanakol_2ah.user_interface;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.activities.LoginActivity;
import com.example.hanakol_2ah.fragments.ListMealsFragmentContainer;
import com.example.hanakol_2ah.fragments.SelectedItemFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentSideMenu extends Fragment {
    private ImageView ic_menu_close;
    private TextView username, language, info , logout;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.side_menu_layout, container, false);

        ic_menu_close = view.findViewById(R.id.ic_menu_close);
        toolbar = view.findViewById(R.id.toolBar);

        logout = view.findViewById(R.id.log_out_side_menu_linear_layout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutCustomer(view);
            }
        });

        ic_menu_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentSideMenu fragmentSideMenu = new FragmentSideMenu()
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fragment_slide_in, R.anim.fragment_slide_in);
                transaction.hide(FragmentSideMenu.this);
                transaction.commit();
                FragmentSideMenu.this.onStop();


            }
        });


        return view;
    }


    public void logoutCustomer(final View view) {
        GoogleSignInAccount signInGoogleAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
        FirebaseAuth.AuthStateListener authStateListener;
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();;
        FirebaseUser user = firebaseAuth.getCurrentUser();;

        if (signInGoogleAccount != null) {
            FirebaseAuth.getInstance().signOut();
            GoogleSignIn.getClient(getActivity(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()).signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    startActivity(new Intent(view.getContext(), LoginActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Sign out Failed ", Toast.LENGTH_SHORT).show();
                }
            });

        }else if (user != null){
            firebaseAuth.signOut();

        }

    }
}