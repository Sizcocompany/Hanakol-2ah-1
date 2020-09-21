package com.example.hanakol_2ah.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
        final TextView ingredients = findViewById(R.id.textViewاسم);
        final TextView names = findViewById(R.id.textView2خطوات);
        final TextView steps = findViewById(R.id.textViewوصف);
        final ImageView imageViewMeal = findViewById(R.id.imageViewMeal);
        TextView headerTextView = findViewById(R.id.headerTextView);
        Button Dinnerbutton = findViewById(R.id.buttonUploadDinnerData);
        Button Breakfastbutton = findViewById(R.id.buttonUploadbreakfastData);
        Button Luanchbutton = findViewById(R.id.buttonUploadLaunchData);


        Breakfastbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mealsDB.getMealsData(imageViewMeal, names, ingredients, steps, "breakfast");
            }
        });

        Luanchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealsDB.getMealsData(imageViewMeal, names, ingredients, steps, "lunch");
            }
        });

        Dinnerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealsDB.getMealsData(imageViewMeal, names, ingredients, steps, "dinner");
            }
        });


    }


}
