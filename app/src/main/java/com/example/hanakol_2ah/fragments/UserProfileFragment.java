//package com.example.hanakol_2ah.fragments;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.hanakol_2ah.R;
//import com.example.hanakol_2ah.adapters.MealAdapter;
//import com.example.hanakol_2ah.models.Meals;
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.Query;
//import com.squareup.picasso.Picasso;
//
//public class UserProfileFragment extends BaseFragment{
//
//        private FirebaseFirestore db = FirebaseFirestore.getInstance();
//        private CollectionReference notebookRef;
//        private CollectionReference notebookRef2;
//        DocumentReference docRef;
//        private MealAdapter adapter;
//        private String child = "breakfast";
//        private FirestoreRecyclerOptions<Meals> options;
//
//        private View view;
//        private SelectedItemFragment selectedItemFragment;
//
//        private View profilePic ;
//        private TextView username , email;
//
//
//        //----------------------------------------------------------------------------------------------
//        private TextView breackfast_prifile_click, lunch_profile_Click, dinner_profile_Click, juice_profile_Click, desserts_profile_Click;
//        //----------------------------------------------------------------------------------------------
//
//        @Nullable
//        @Override
//        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            final View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
//            this.view = view;
//            selectedItemFragment = new SelectedItemFragment();
//
//
//
//            setUpRecyclerView(view, child);
//
//            profilePic = view.findViewById(R.id.user_profile_pic_page);
//            username = view.findViewById(R.id.user_profile_name_page);
//            email = view.findViewById(R.id.user_profile_email_page);
//
//
//
////            ic_close.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    getParentFragmentManager().beginTransaction().remove(UserProfileFragment.this).commitAllowingStateLoss();
////                }
////            });
//
//
//
//
////        --------------------------------meals_search_Click----------------------------------------
//            breackfast_prifile_click = view.findViewById(R.id.breackfast_profile_search_Click);
//            lunch_profile_Click = view.findViewById(R.id.lunch_profile_search_Click);
//            dinner_profile_Click = view.findViewById(R.id.dinner_profile_search_Click);
//            juice_profile_Click = view.findViewById(R.id.juice_profle_search_Click);
//            desserts_profile_Click = view.findViewById(R.id.desserts_profile_search_Click);
////        ------------------------------------------------------------------------------------------
//
//
//            breackfast_prifile_click.setBackground(getResources().getDrawable(R.color.colorPrimary));
//
//            breackfast_prifile_click.setOnClickListener(new View.OnClickListener() {
//
//                public void onClick(View v) {
//                    child = "breakfast";
//                    breackfast_prifile_click.setBackground(getResources().getDrawable(R.color.colorPrimary));
//                    lunch_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    dinner_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    juice_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    desserts_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    setUpRecyclerView(view, child);
//
//                }
//            });
//
//            lunch_profile_Click.setOnClickListener(new View.OnClickListener() {
//                @SuppressLint("ResourceAsColor")
//                @Override
//                public void onClick(View v) {
//                    child = "lunch";
//                    breackfast_prifile_click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    lunch_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimary));
//                    dinner_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    juice_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    desserts_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    setUpRecyclerView(view, child);
//
//                }
//            });
//            dinner_profile_Click.setOnClickListener(new View.OnClickListener() {
//                @SuppressLint("ResourceAsColor")
//                @Override
//                public void onClick(View v) {
//                    child = "dinner";
//                    breackfast_prifile_click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    lunch_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    dinner_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimary));
//                    juice_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    desserts_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    setUpRecyclerView(view, child);
//
//                }
//            });
//            juice_profile_Click.setOnClickListener(new View.OnClickListener() {
//                @SuppressLint("ResourceAsColor")
//                @Override
//                public void onClick(View v) {
//                    child = "juices";
//                    breackfast_prifile_click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    lunch_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    dinner_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    juice_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimary));
//                    desserts_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    setUpRecyclerView(view, child);
//
//                }
//            });
//            desserts_profile_Click.setOnClickListener(new View.OnClickListener() {
//                @SuppressLint("ResourceAsColor")
//                @Override
//                public void onClick(View v) {
//                    child = "desserts";
//                    breackfast_prifile_click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    lunch_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    dinner_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    juice_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimaryDark));
//                    desserts_profile_Click.setBackground(getResources().getDrawable(R.color.colorPrimary));
//                    setUpRecyclerView(view, child);
//
//                }
//            });
//
//
//            return view;
//        }
//
//        @Override
//        public void onStart() {
//            super.onStart();
//            adapter.startListening();
//        }
//
//
//        private void setUpRecyclerView(View v, final String child) {
//
//        notebookRef = db.collection(child);
////            notebookRef = db.collection("meals-database").document(child).collection("data");
//            Query query = notebookRef.orderBy("mealName", Query.Direction.ASCENDING);
//            options = new FirestoreRecyclerOptions.Builder<Meals>()
//                    .setQuery(query, Meals.class)
//                    .build();
//            adapter = new MealAdapter(getActivity().getApplicationContext(), options);
//            RecyclerView recyclerView = v.findViewById(R.id.container_profile_search_recyclerview);
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//            recyclerView.setAdapter(adapter);
//            onStart();
//
//
//            adapter.setOnItemClickListener(new MealAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//
//                    Meals meal = documentSnapshot.toObject(Meals.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("MEAL_NAME", meal.getMealName());
//                    bundle.putString("MEAL_DESCRIPTION", meal.getDescription());
//                    bundle.putString("MEAL_STEP", meal.getSteps());
//                    bundle.putString("MEAL_IMAGE_URI", meal.getImageURL());
//                    bundle.putString("MEAL_RATE", meal.getMealRate().toString());
//                    bundle.putString("MEAL_OWNER_EMAIL", meal.getMealOwner());
//                    bundle.putString("MEAL_CREATION_DATE", meal.getMealCreationDate());
//                    username.setText(meal.getMealOwner());
//                    email.setText(meal.getMealOwnerEmail());
//                    bundle.putString("CHILD", child);
//
//                    FragmentTransaction(selectedItemFragment, bundle);
//
//                }
//            });
//        }
//
//        private void FragmentTransaction(Fragment fragment, Bundle bundle) {
//            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.activity_home_container, fragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//            fragment.setArguments(bundle);
//        }
//
//
//    }
//
