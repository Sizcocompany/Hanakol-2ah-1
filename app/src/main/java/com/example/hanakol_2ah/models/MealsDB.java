package com.example.hanakol_2ah.models;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class MealsDB {

    public void getBreakfastData(final ImageView imageView , final TextView names , final TextView ingredients , final TextView steps  ) {
        final ArrayList<String> namesArrayList = new ArrayList<>();
        final ArrayList<String> ingreadentsArrayList = new ArrayList<>();
        final ArrayList<String> stepsArrayList = new ArrayList<>();
        final ArrayList<String> imageArrayList = new ArrayList<>();
        final ArrayList<String> categoryArrayList = new ArrayList<>();
        final ArrayList<String> ratingArrayList = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("hanakol-aeh").child("Breakfast").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ModelMeals meals = snapshot.getValue(ModelMeals.class);
                    namesArrayList.add(meals.getMealName());
                    ingreadentsArrayList.add(meals.getMealIngredients());
                    stepsArrayList.add(meals.getMealSteps());
                    imageArrayList.add(meals.getImageUrl());
                    categoryArrayList.add(meals.getMeatCategory());
                    ratingArrayList.add(meals.getRatingBar());
                }
                Random random = new Random();
                int Rand = random.nextInt(namesArrayList.size());
                names.setText(namesArrayList.get(Rand));
                ingredients.setText(ingreadentsArrayList.get(Rand));
                steps.setText(stepsArrayList.get(Rand));
                String photoUrl = imageArrayList.get(Rand).toString();
                photoUrl = photoUrl + "?type=large";
                Picasso.get().load(photoUrl).into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getLunchData(final TextView names , final TextView ingredients , final TextView steps) {
        final ArrayList<String> namesArrayList = new ArrayList<>();
        final ArrayList<String> ingreadentsArrayList = new ArrayList<>();
        final ArrayList<String> stepsArrayList = new ArrayList<>();


        FirebaseDatabase.getInstance().getReference().child("hanakol-aeh").child("Launch").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ModelMeals meals = snapshot.getValue(ModelMeals.class);
                    namesArrayList.add(meals.getMealName());
                    ingreadentsArrayList.add(meals.getMealIngredients());
                    stepsArrayList.add(meals.getMealSteps());
                }
                Random random = new Random();
                int Rand = random.nextInt(namesArrayList.size());
                names.setText(namesArrayList.get(Rand));
                ingredients.setText(ingreadentsArrayList.get(Rand));
                steps.setText(stepsArrayList.get(Rand));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDinnerData(final TextView names , final TextView ingredients , final TextView steps) {
        final ArrayList<String> namesArrayList = new ArrayList<>();
        final ArrayList<String> ingreadentsArrayList = new ArrayList<>();
        final ArrayList<String> stepsArrayList = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("hanakol-aeh").child("Dinner").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ModelMeals meals = snapshot.getValue(ModelMeals.class);
                    namesArrayList.add(meals.getMealName());
                    ingreadentsArrayList.add(meals.getMealIngredients());
                    stepsArrayList.add(meals.getMealSteps());
                }
                Random random = new Random();
                int Rand = random.nextInt(namesArrayList.size());
                names.setText(namesArrayList.get(Rand));
                ingredients.setText(ingreadentsArrayList.get(Rand));
                steps.setText(stepsArrayList.get(Rand));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
