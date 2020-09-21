package com.example.hanakol_2ah.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.adapters.MyAdapter;
import com.example.hanakol_2ah.models.Meals;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListMealsFragmentContainer extends Fragment {
    private String child;

    public ListMealsFragmentContainer(String child) {
        this.child = child;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_meals_list_container, container, false);
        ImageView back_image_button = view.findViewById(R.id.back_click_image);


//        onGetbreakfastList(view , "Breakfast");
        onGetMealsList(view, child);

        back_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(ListMealsFragmentContainer.this).commitAllowingStateLoss();
            }
        });


        return view;
    }

    private void onGetMealsList(View view, String string) {
        final RecyclerView rv = view.findViewById(R.id.container_recyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        final List<Meals> listData = new ArrayList<>();


        FirebaseDatabase.getInstance().getReference().child("Meal").child(string).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        Meals l = npsnapshot.getValue(Meals.class);
                        listData.add(l);
                    }
                    final MyAdapter adapter = new MyAdapter(getActivity().getApplicationContext(),listData);
                    rv.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}

