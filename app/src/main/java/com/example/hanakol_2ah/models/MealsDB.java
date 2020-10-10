package com.example.hanakol_2ah.models;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class MealsDB {


    private ListenerRegistration noteListener;


    public void getMealsData(final ImageView ImageURL, final TextView MealName, final TextView Description, final TextView Steps, final RatingBar MealRate, String child) {
        final ArrayList<Meals> mealsArrayList = new ArrayList<>();


        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//        CollectionReference studentsCollectionReference = rootRef.collection(child);
        CollectionReference studentsCollectionReference = rootRef.collection("meals-database").document(child).collection("data");
        studentsCollectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Meals meals = document.toObject(Meals.class);
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
            }


        });
    }


}
