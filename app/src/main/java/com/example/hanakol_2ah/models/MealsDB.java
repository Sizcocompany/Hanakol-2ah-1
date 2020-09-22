package com.example.hanakol_2ah.models;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class MealsDB {

    public void getMealsData(final ImageView ImageURL, final TextView MealName, final TextView Description, final TextView Steps, final RatingBar MealRate, String child) {
        final ArrayList<Meals> mealsArrayList = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Meal").child(child);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Meals meals = snapshot.getValue(Meals.class);
                    mealsArrayList.add(meals);
                }
                Random random = new Random();
                int Rand = random.nextInt(mealsArrayList.size());
                MealName.setText(mealsArrayList.get(Rand).getMealName());
                Description.setText(mealsArrayList.get(Rand).getDescription());
                Steps.setText(mealsArrayList.get(Rand).getSteps());
                Picasso.get().load(mealsArrayList.get(Rand).getImageURL()).into(ImageURL);
                MealRate.setRating(mealsArrayList.get(Rand).getMealRate());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
