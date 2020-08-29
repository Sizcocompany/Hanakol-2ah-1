package com.example.hanakol_2ah;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ImageView mealImage;
    private EditText name , description , steps ;
    private TextView tvProgress ;
    private Button uploadbtn ;
    private ProgressBar progressBar;
    private RatingBar mRatingBar;
    private int REQUEST_CODE_IMAGE = 101;

    DatabaseReference databaseRef ;
    StorageReference storageRef ;

    Uri imageurl;
    Boolean isImageAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mealImage = findViewById( R.id.meatImage );
        name = findViewById( R.id.et_eatname );
        description = findViewById( R.id.et_description );
        steps = findViewById( R.id.et_steps );
        tvProgress = findViewById( R.id.textview_progress );
        progressBar = findViewById( R.id.progress_bar );
        uploadbtn = findViewById( R.id.upload_butn );
        mRatingBar = findViewById( R.id.rating_Bar );

        tvProgress.setVisibility( View.GONE );
        progressBar.setVisibility( View.GONE );

        databaseRef = FirebaseDatabase.getInstance().getReference().child( "Meal" ).child( "breakfast" );
        storageRef = FirebaseStorage.getInstance().getReference().child( " MealImages" );

        mealImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(  );
                intent.setType( "image/*" );
                intent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( intent , REQUEST_CODE_IMAGE );
            }
        } );

        uploadbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String meatName = name.getText().toString();
               final String meatDescription = description.getText().toString();
               final String meatSteps = steps.getText().toString();
               if(isImageAdded != false && meatName != null && meatDescription != null && meatSteps != null){

                   uploadImage (meatName , meatDescription , meatSteps);
               }

            }
        } );
    }

    private void uploadImage(final String mealName, final String meatDescription, final String mealSteps) {

        tvProgress.setVisibility( View.VISIBLE );
        progressBar.setVisibility( View.VISIBLE );

     final    String key = databaseRef.getKey();
        storageRef.child( key + "jpg" ).putFile( imageurl ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageRef.child( key +"jpg" ).getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap(  );

                        hashMap.put( "MealName" , mealName);
                        hashMap.put( "description " , meatDescription );
                        hashMap.put( "Steps" , mealSteps );
                        hashMap.put( "ImageURL" , uri.toString() );

                        databaseRef.child( key ).setValue( hashMap ).addOnSuccessListener( new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                startActivity( new Intent(  getApplicationContext() , HomeActivity1.class ) );
                           //  Toast.makeText( MainActivity.this, "Data successfully upload", Toast.LENGTH_SHORT ).show();
                            }
                        } );
                    }
                } );

            }
        } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (taskSnapshot.getBytesTransferred()*100) / taskSnapshot.getTotalByteCount();
                progressBar.setProgress( (int) progress );
                tvProgress.setText( progress +" %"  );
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode == REQUEST_CODE_IMAGE && data != null){
            {
                imageurl = data.getData();
                isImageAdded = true ;
                mealImage.setImageURI( imageurl );
                

            }
        }
    }
}