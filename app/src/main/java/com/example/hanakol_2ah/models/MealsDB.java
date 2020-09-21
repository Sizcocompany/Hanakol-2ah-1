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

    public void getMealsData(final ImageView imageView, final TextView names , final TextView ingredients , final TextView steps , String child ) {
        final ArrayList<String> namesArrayList = new ArrayList<>();
        final ArrayList<String> ingreadentsArrayList = new ArrayList<>();
        final ArrayList<String> stepsArrayList = new ArrayList<>();
        final ArrayList<String> imageArrayList = new ArrayList<>();


        FirebaseDatabase.getInstance().getReference().child("Meal").child(child).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Meals meals = snapshot.getValue(Meals.class);
                    namesArrayList.add(meals.getMealName());
                    ingreadentsArrayList.add(meals.getDescription());
                    stepsArrayList.add(meals.getSteps());
                    imageArrayList.add(meals.getImageURL());
                }
                Random random = new Random();
                int Rand = random.nextInt(namesArrayList.size());
                names.setText(namesArrayList.get(Rand));
                ingredients.setText(ingreadentsArrayList.get(Rand));
                steps.setText(stepsArrayList.get(Rand));
                Picasso.get().load(imageArrayList.get(Rand)).into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//    public void getLunchData( final ImageView imageView , final TextView names , final TextView ingredients , final TextView steps ) {
//        final ArrayList<String> namesArrayList = new ArrayList<>();
//        final ArrayList<String> ingreadentsArrayList = new ArrayList<>();
//        final ArrayList<String> stepsArrayList = new ArrayList<>();
//        final ArrayList<String> imageArrayList = new ArrayList<>();
//
//
//        FirebaseDatabase.getInstance().getReference().child("hanakol-aeh").child("Meal").child("lunch").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Meals meals = snapshot.getValue(Meals.class);
//                    imageArrayList.add(meals.getImage());
//                    namesArrayList.add(meals.getName());
//                    ingreadentsArrayList.add(meals.getIngredients());
//                    stepsArrayList.add(meals.getSteps());
//
//                }
//                Random random = new Random();
//                int Rand = random.nextInt(namesArrayList.size());
//                names.setText(namesArrayList.get(Rand));
//                ingredients.setText(ingreadentsArrayList.get(Rand));
//                steps.setText(stepsArrayList.get(Rand));
//                Picasso.get().load(imageArrayList.get(Rand)).into(imageView);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    public void getDinnerData(final TextView names , final TextView ingredients , final TextView steps) {
//        final ArrayList<String> namesArrayList = new ArrayList<>();
//        final ArrayList<String> ingreadentsArrayList = new ArrayList<>();
//        final ArrayList<String> stepsArrayList = new ArrayList<>();
//
//        FirebaseDatabase.getInstance().getReference().child("hanakol-aeh").child("Meal").child("dinner").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Meals meals = snapshot.getValue(Meals.class);
//                    namesArrayList.add(meals.getName());
//                    ingreadentsArrayList.add(meals.getIngredients());
//                    stepsArrayList.add(meals.getSteps());
//
//                }
//                Random random = new Random();
//                int Rand = random.nextInt(namesArrayList.size());
//                names.setText(namesArrayList.get(Rand));
//                ingredients.setText(ingreadentsArrayList.get(Rand));
//                steps.setText(stepsArrayList.get(Rand));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }






}
