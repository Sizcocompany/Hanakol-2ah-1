package com.example.hanakol_2ah.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.models.Meals;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ImageView mealImage;
    private EditText name, description, steps;
    private TextView tvProgress;
    private Button uploadbtn;
    private ProgressBar progressBar;
    private Spinner spinner;
    private String child;
    private String URI;
    private int REQUEST_CODE_IMAGE = 101;


    DatabaseReference databaseRef;
    StorageReference storageRef;
    private CollectionReference notebookRef;


    Uri imageurl;
    Boolean isImageAdded = false;


    private ListenerRegistration noteListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mealImage = findViewById(R.id.logo_Image);
        name = findViewById(R.id.et_eatname);
        description = findViewById(R.id.et_description);
        steps = findViewById(R.id.et_steps);
        tvProgress = findViewById(R.id.textview_progress);
        progressBar = findViewById(R.id.progress_bar);
        uploadbtn = findViewById(R.id.upload_butn);
        spinner = (Spinner) findViewById(R.id.spinner);
        tvProgress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        storageRef = FirebaseStorage.getInstance().getReference().child("MealImages");

        mealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String meatName = name.getText().toString();
                final String meatDescription = description.getText().toString();
                final String meatSteps = steps.getText().toString();
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
//                final CollectionReference notebookRef = db.collection("Meal");

                if (isImageAdded != false && meatName != null && meatDescription != null && meatSteps != null) {


                    uploadImage(db, meatName, meatDescription, meatSteps, Float.parseFloat("0.00"), child);
                }

            }
        });


        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Breakfast");
        categories.add("Lunch");
        categories.add("Dinner");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }


    private void uploadImage(final FirebaseFirestore db, final String mealName, final String mealDescription, final String mealSteps, final Float mealRate, final String child) {


        tvProgress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        databaseRef = FirebaseDatabase.getInstance().getReference().child("Meal").child(child);
        final String key = databaseRef.push().getKey();
        storageRef.child(key + "jpg").putFile(imageurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageRef.child(key + "jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("MealName", mealName);
                        hashMap.put("Description", mealDescription);
                        hashMap.put("Steps", mealSteps);
                        hashMap.put("ImageURL", uri.toString());
                        hashMap.put("MealRate", mealRate);

                        Meals meals = new Meals(mealDescription, uri.toString(), mealName, mealRate, mealSteps , onGetOwnerName());
                        CollectionReference notebookRef = db.collection(child);

                        notebookRef.document(mealName).set(meals).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                Toast.makeText(AddActivity.this, "Data successfully upload", Toast.LENGTH_LONG).show();
                            }
                        });
//
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (taskSnapshot.getBytesTransferred() * 100) / taskSnapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);
                tvProgress.setText(progress + " %");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && data != null) {
            {
                imageurl = data.getData();
                isImageAdded = true;
                mealImage.setImageURI(imageurl);


            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString().toLowerCase();
        this.child = item;
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private String onGetOwnerName() {
        FirebaseAuth mFirebaseAuth;
        FirebaseUser mFirebaseUser;
        GoogleApiClient mGoogleApiClient;
        String mUsername = "UserName";
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API)
                    .build();

            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser == null) {
                // Not signed in, launch the Sign In activity
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
            } else {
                mUsername = mFirebaseUser.getEmail();

            }
        } catch (Exception e) {
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser != null) {
                mUsername = mFirebaseUser.getEmail();

            }
        }

        return mUsername;
    }

}
