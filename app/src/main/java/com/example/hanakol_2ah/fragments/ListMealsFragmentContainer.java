package com.example.hanakol_2ah.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

public class ListMealsFragmentContainer extends Fragment implements AdapterView.OnItemSelectedListener {
    private String child;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef;
    private MealAdapter adapter;
    private View view;
    private Spinner spinner;
    private SelectedItemFragment selectedItemFragment;
    private int MEAL_SORTING_CONDITION;
    private int MEAL_FAVORITES_CONDITION;

    public ListMealsFragmentContainer(String child) {
        this.child = child;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_meals_list_container, container, false);
        ImageView back_image_button = view.findViewById(R.id.back_click_image);
        this.view = view;

        spinner = view.findViewById(R.id.spinner_sorting);

        selectedItemFragment = new SelectedItemFragment();
        Bundle bundle = this.getArguments();
        if (getArguments() != null) {
            MEAL_FAVORITES_CONDITION = bundle.getInt("MEAL_FAVORITES_CONDITION");
        }
//        setUpRecyclerView(view, child);


        back_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().remove(ListMealsFragmentContainer.this).commitAllowingStateLoss();
            }
        });


        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Sort A to Z");
        categories.add("Sort Z to A");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        return view;
    }


    private void setUpRecyclerView(View v, final String child ) {

        notebookRef = db.collection(child);
//        notebookRef = db.collection("meals-database").document(child).collection("data");
        Query query;
        if (MEAL_SORTING_CONDITION == 0) {
            query = notebookRef.orderBy("mealName", Query.Direction.ASCENDING);
            FirestoreRecyclerOptions<Meals> options = new FirestoreRecyclerOptions.Builder<Meals>()
                    .setQuery(query, Meals.class)
                    .build();
            adapter = new MealAdapter(getActivity().getApplicationContext(), options);

        } else if (MEAL_SORTING_CONDITION == 1){
            query = notebookRef.orderBy("mealName", Query.Direction.DESCENDING);
            FirestoreRecyclerOptions<Meals> options = new FirestoreRecyclerOptions.Builder<Meals>()
                    .setQuery(query, Meals.class)
                    .build();
            adapter = new MealAdapter(getActivity().getApplicationContext(), options);

        }
//            adapter = new MealAdapter(getActivity().getApplicationContext(), options);
        RecyclerView recyclerView = v.findViewById(R.id.container_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


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
                bundle.putInt("MEAL_FAVORITES_CONDITION", MEAL_FAVORITES_CONDITION);
                bundle.putString("CHILD", child);

                FragmentTransaction(selectedItemFragment, bundle);

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        setUpRecyclerView(view, child);
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean isFragmentAdded() {
        return false;
    }

    public boolean hasEditableFields() {
        return false;
    }

    private void FragmentTransaction(Fragment fragment, Bundle bundle) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragment.setArguments(bundle);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString().toLowerCase();
        switch (item) {
            case "sort a to z":
                this.MEAL_SORTING_CONDITION = 0;
                break;
            case "sort z to a":
                this.MEAL_SORTING_CONDITION = 1;
                break;
            default:
                this.MEAL_SORTING_CONDITION = 0;
                break;

        }
//        this.MEAL_SORTING_CONDITION = MEAL_SORTING_CONDITION;
        // Showing selected spinner item
        onStart();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
