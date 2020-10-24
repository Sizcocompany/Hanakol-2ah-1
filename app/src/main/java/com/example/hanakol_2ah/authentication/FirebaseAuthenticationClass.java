package com.example.hanakol_2ah.authentication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hanakol_2ah.activities.HomeBaseActivity;
import com.example.hanakol_2ah.activities.RegisterActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthenticationClass {

    private Button logIn;
    private Button signUp;
    private EditText firebaseEmail;
    private EditText firebasePass;
    private TextView forgetPass;
    private FirebaseAuth firebaseAuth;
    private Context context;

    public FirebaseAuthenticationClass(Button logIn, Button signUp, EditText firebaseEmail
            , EditText firebasePass, TextView forgetPass, FirebaseAuth firebaseAuth
            , Context context) {
        this.logIn = logIn;
        this.signUp = signUp;
        this.firebaseEmail = firebaseEmail;
        this.firebasePass = firebasePass;
        this.forgetPass = forgetPass;
        this.firebaseAuth = firebaseAuth;
        this.context = context;
    }

    public void checkEmailAndPassword(String email , String password) {


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

    }

    public void setLogIn(Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(context, "Successful LogIn", Toast.LENGTH_LONG).show();
//                            login_txt_btn.setVisibility(View.INVISIBLE);
                    context.startActivity(new Intent(context, HomeBaseActivity.class));


                } else {
                    Toast.makeText(context, "ERROR" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
            }



    public void setSignUp() {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    public void setForgetPass(View view){
        final EditText resetMail = new EditText(view.getContext());
        AlertDialog.Builder passReset = new AlertDialog.Builder(view.getContext());
        passReset.setTitle("Reset Password !!");
        passReset.setMessage("Enter your Email to Recieve the Reset Link");
        passReset.setView(resetMail);

        passReset.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String mail = resetMail.getText().toString();
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Reset Email was Sent", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "ERROR !!" + e.getMessage(), Toast.LENGTH_LONG).show();
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
}
