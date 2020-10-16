package com.example.hanakol_2ah.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hanakol_2ah.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {


    EditText rName, rEmail, rPassword, rPasswordConfirm;
    Button registerAccount;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        rName = findViewById( R.id.registerName );
        rEmail = findViewById( R.id.registerEmail );
        rPassword = findViewById( R.id.registerPassword );
        rPasswordConfirm = findViewById( R.id.registerConfirmPass );
        registerAccount = findViewById( R.id.registerButton );
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();




            if (mFirebaseUser != null) {
                startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                finish();
            }
            registerAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = rEmail.getText().toString();
                    String password = rPassword.getText().toString();

                    //if the field is empty
                    if (TextUtils.isEmpty(email)) {
                        rEmail.setError("Enter the Email");
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        rPassword.setError("Enter the Password");
                        return;
                    }

                    //if the lenght is shorter than 8 characters
                    if (password.length() < 8) {
                        rPassword.setError("The Password must be Longer than 8 Characters");
                        return;
                    }

                    //register the email in firebase for the new comer
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Email Registered Successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "ERROR" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });


                }
            });



    }

}

