package com.example.hanakol_2ah;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MealsDB {

    public void getBreakfastData(final TextView names , final TextView ingredients , final TextView steps) {
        final ArrayList<String> namesArrayList = new ArrayList<>();
        final ArrayList<String> ingreadentsArrayList = new ArrayList<>();
        final ArrayList<String> stepsArrayList = new ArrayList<>();


        FirebaseDatabase.getInstance().getReference().child("hanakol-aeh").child("Breakfast").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Meals meals = snapshot.getValue(Meals.class);
                    namesArrayList.add(meals.getName());
                    ingreadentsArrayList.add(meals.getIngredients());
                    stepsArrayList.add(meals.getSteps());
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
    public void getLunchData(final TextView names , final TextView ingredients , final TextView steps) {
        final ArrayList<String> namesArrayList = new ArrayList<>();
        final ArrayList<String> ingreadentsArrayList = new ArrayList<>();
        final ArrayList<String> stepsArrayList = new ArrayList<>();


        FirebaseDatabase.getInstance().getReference().child("hanakol-aeh").child("Launch").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Meals meals = snapshot.getValue(Meals.class);
                    namesArrayList.add(meals.getName());
                    ingreadentsArrayList.add(meals.getIngredients());
                    stepsArrayList.add(meals.getSteps());
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
                    Meals meals = snapshot.getValue(Meals.class);
                    namesArrayList.add(meals.getName());
                    ingreadentsArrayList.add(meals.getIngredients());
                    stepsArrayList.add(meals.getSteps());

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
