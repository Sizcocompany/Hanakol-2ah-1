package com.example.hanakol_2ah.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.models.MealsDB;


public class RandomMealsActivity extends AppCompatActivity {


    MealsDB mealsDB = new MealsDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_meals);
        final TextView Description = findViewById(R.id.textView2خطوات);
        final TextView MealName =  findViewById(R.id.textViewاسم);
        final TextView Steps = findViewById(R.id.textViewوصف);
        final ImageView ImageURL =findViewById(R.id.imageViewMeal);
        final RatingBar MealRate =findViewById(R.id.randomratingBar);
        TextView Dinnerbutton = findViewById(R.id.buttonUploadDinnerData);
        TextView Breakfastbutton = findViewById(R.id.buttonUploadbreakfastData);
        TextView Luanchbutton = findViewById(R.id.buttonUploadLaunchData);


        Breakfastbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mealsDB.getMealsData(ImageURL, MealName, Description, Steps , MealRate, "breakfast");
            }
        });

        Luanchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealsDB.getMealsData(ImageURL, MealName, Description, Steps , MealRate, "lunch");
            }
        });

        Dinnerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealsDB.getMealsData(ImageURL, MealName, Description, Steps, MealRate, "dinner");
            }
        });


    }


}
