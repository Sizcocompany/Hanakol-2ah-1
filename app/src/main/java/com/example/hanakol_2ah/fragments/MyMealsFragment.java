package com.example.hanakol_2ah.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import static com.example.hanakol_2ah.fragments.SelectedItemFragment.meal_edit_text_view;

public class MyMealsFragment extends Fragment {
    private String child = "breakfast";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef;
    private MealAdapter adapter;
    private View view;
    private LinearLayout child_linear;
    private SelectedItemFragment selectedItemFragment;
    private String userName;

    //----------------------------------------------------------------------------------------------
    private TextView breackfast_search_Click, lunch_search_Click, dinner_search_Click, juice_search_Click, desserts_search_Click;
    //----------------------------------------------------------------------------------------------

    public MyMealsFragment( String userName) {

        this.userName = userName;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_meals_list_container, container, false);
        ImageView back_image_button = view.findViewById(R.id.back_click_image);
        this.view = view;


        child_linear = view.findViewById(R.id.child_linear);
        child_linear.setVisibility(View.VISIBLE);
        selectedItemFragment = new SelectedItemFragment();
//        meal_edit_text_view = view.findViewById(R.id.meal_edit_text_view);
//        meal_edit_text_view.setVisibility(View.VISIBLE);
//        onGetbreakfastList(view , "Breakfast");


        setUpRecyclerView(view, child, userName);

//        meal_edit_text_view.setVisibility( View.VISIBLE );
        back_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(MyMealsFragment.this).commitAllowingStateLoss();
            }
        });




        //        --------------------------------meals_search_Click----------------------------------------
        breackfast_search_Click = view.findViewById(R.id.breackfast_child_Click);
        lunch_search_Click = view.findViewById(R.id.lunch_child_Click);
        dinner_search_Click = view.findViewById(R.id.dinner_child_Click);
        juice_search_Click = view.findViewById(R.id.juice_child_Click);
        desserts_search_Click = view.findViewById(R.id.desserts_child_Click);
//        ------------------------------------------------------------------------------------------

        breackfast_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryLight));

        breackfast_search_Click.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                child = "breakfast";
                breackfast_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryLight));
                lunch_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                dinner_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                juice_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                desserts_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                setUpRecyclerView(view, child, userName);

            }
        });

        lunch_search_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child = "lunch";
                breackfast_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                lunch_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryLight));
                dinner_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                juice_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                desserts_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                setUpRecyclerView(view, child, userName);

            }
        });
        dinner_search_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child = "dinner";
                breackfast_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                lunch_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                dinner_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryLight));
                juice_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                desserts_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                setUpRecyclerView(view, child, userName);

            }
        });
        juice_search_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child = "juices";
                breackfast_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                lunch_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                dinner_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                juice_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryLight));
                desserts_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                setUpRecyclerView(view, child, userName);

            }
        });
        desserts_search_Click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child = "desserts";
                breackfast_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                lunch_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                dinner_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                juice_search_Click.setBackground(getResources().getDrawable(R.color.endblue));
                desserts_search_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryLight));
                setUpRecyclerView(view, child, userName);

            }
        });

        return view;
    }

    private void setUpRecyclerView(View v, final String child, String userName) {

        notebookRef = db.collection(child);
//        notebookRef = db.collection("meals-database").document(child).collection("data");
        Query query = notebookRef.whereEqualTo("mealOwner", userName);
        FirestoreRecyclerOptions<Meals> options = new FirestoreRecyclerOptions.Builder<Meals>()
                .setQuery(query, Meals.class)
                .build();
        adapter = new MealAdapter(getActivity().getApplicationContext(), options);
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
                bundle.putString("MEAL_CREATED_DATE", meal.getMealCreationDate());
                bundle.putInt("VISIBILTY" , 1);
                bundle.putString("CHILD", child);

                FragmentTransaction(selectedItemFragment, bundle);

            }
        });
    }




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
