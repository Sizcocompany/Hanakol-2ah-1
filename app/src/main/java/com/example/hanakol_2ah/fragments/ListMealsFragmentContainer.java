package com.example.hanakol_2ah.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

public class ListMealsFragmentContainer extends Fragment {
    private String child;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef;
    private MealAdapter adapter;
    private View view;
    private SelectedItemFragment selectedItemFragment;

    public ListMealsFragmentContainer(String child) {
        this.child = child;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_meals_list_container, container, false);
        ImageView back_image_button = view.findViewById(R.id.back_click_image);
        this.view = view;


        selectedItemFragment = new SelectedItemFragment();
//        onGetbreakfastList(view , "Breakfast");
        setUpRecyclerView(view, child);




        back_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(ListMealsFragmentContainer.this).commitAllowingStateLoss();
            }
        });


        return view;
    }


    private void setUpRecyclerView(View v, String child) {

        notebookRef = db.collection(child);
        Query query = notebookRef.orderBy("mealName", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Meals> options = new FirestoreRecyclerOptions.Builder<Meals>()
                .setQuery(query, Meals.class)
                .build();
        adapter = new MealAdapter(options);
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

                FragmentTransaction(selectedItemFragment, bundle);

            }
        });
    }

//    private void RecyclerViewsearch(View v,String child,String meal) {
//
//        final RecyclerView rv = v.findViewById(R.id.container_recyclerview);
//        rv.setHasFixedSize(true);
//        rv.setLayoutManager(new LinearLayoutManager(getContext()));
//        final List<Meals> listData = new ArrayList<>();
//
//        notebookRef = db.collection(child);
//        Query query = notebookRef.whereEqualTo(meal, Query.Direction.ASCENDING);
//        FirestoreRecyclerOptions<Meals> options = new FirestoreRecyclerOptions.Builder<Meals>()
//                .setQuery(query, Meals.class)
//                .build();
//        adapter = new MealAdapter(options);
//        RecyclerView recyclerView = v.findViewById(R.id.container_recyclerview);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);
//
//        adapter.setOnItemClickListener(new MealAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//
//                Meals meal = documentSnapshot.toObject(Meals.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("MEAL_NAME", meal.getMealName());
//                bundle.putString("MEAL_DESCRIPTION", meal.getDescription());
//                bundle.putString("MEAL_STEP", meal.getSteps());
//                bundle.putString("MEAL_IMAGE_URI", meal.getImageURL());
//                bundle.putString("MEAL_RATE", meal.getMealRate().toString());
//
//                FragmentTransaction(selectedItemFragment, bundle);
//
//
//            }
//        });
//    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    private void FragmentTransaction(Fragment fragment, Bundle bundle) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragment.setArguments(bundle);
    }
}
