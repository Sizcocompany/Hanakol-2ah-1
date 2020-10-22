package com.example.hanakol_2ah.fragments;


import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.adapters.MealAdapter;
import com.example.hanakol_2ah.models.Meals;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * Created by amra on 5/2/2018.
 */


public class BaseFragment extends Fragment {

    /**
     * @return true >> the fragment_map is added not replace  && false>> the fragment_map is Replaced
     * getSupportFragmentManager().beginTransaction().[[[[(true>add)//(false>replace)]]]](R.id.fragment_content, fragment_map, fargmentTag);
     */
    public boolean isFragmentAdded() {
        return false;
    }

    /**
     * @return true if the fragment_map has editViews , and you need to show [discard/stay] alert onBackPress.
     * otherwise return false
     */
    public boolean hasEditableFields() {
        return false;
    }

    public void setUpRecyclerView(CollectionReference mealsRef, final FirebaseFirestore db, FirestoreRecyclerOptions<Meals> options, MealAdapter adapter, final Fragment fragment, View v, final String child) {

        mealsRef = db.collection(child);
        Query query = mealsRef.orderBy("mealName", Query.Direction.ASCENDING);
        options = new FirestoreRecyclerOptions.Builder<Meals>()
                .setQuery(query, Meals.class)
                .build();
        adapter = new MealAdapter(getActivity().getApplicationContext(), options);
        RecyclerView recyclerView = v.findViewById(R.id.container_search_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        onStart();


        adapter.setOnItemClickListener(new MealAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Meals meal = documentSnapshot.toObject(Meals.class);
                Bundle bundle = new Bundle();
                bundle.putString("MEAL_NAME", meal.getMealName());
                bundle.putString("MEAL_DESCRIPTION", meal.getDescription());
                bundle.putString("MEAL_STEP", meal.getSteps());
                bundle.putString("MEAL_IMAGE_URI", meal.getImageURL());
                bundle.putString("MEAL_RATE", meal.getMealRate().toString());
                bundle.putString("MEAL_OWNER_EMAIL", meal.getMealOwner());
                bundle.putString("MEAL_CREATION_DATE", meal.getMealCreationDate());
                bundle.putString("CHILD", child);

                FragmentTransaction(fragment, bundle);

            }
        });
    }

    private void FragmentTransaction(Fragment fragment, Bundle bundle) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragment.setArguments(bundle);
    }

}