package com.example.hanakol_2ah.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hanakol_2ah.R;
import com.squareup.picasso.Picasso;

public class SelectedItemFragment extends Fragment {

//    i changed the name of that fragment from single view fragment into selected item fragment to be more detailed;

    private ImageView selected_item_photo;
    private TextView selected_item_name, selected_item_steps, selected_item_ingredients;
    private RatingBar meal_rating_Bar;
    public static final String EXTRA_NAME = "com.examples.hanakol-2ah.EXTRA_NAME";
    public static final String EXTRA_RATE = "com.examples.hanakol-2ah.EXTRA_RATE";
    public static final String EXTRA_STEPS = "com.examples.hanakol-2ah.EXTRA_STEPS";
    public static final String EXTRA_DESCRIPTION = "com.examples.hanakol-2ah.EXTRA_DESCRIPTION";
    public static final String EXTRA_IMAGE_URL = "com.examples.hanakol-2ah.EXTRA_IMAGE_URL";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_selected_item, container, false);

        selected_item_photo = view.findViewById(R.id.selected_item_image_iv);
        selected_item_name = view.findViewById(R.id.selected_item_name_tv);
        selected_item_ingredients = view.findViewById(R.id.selected_item_ingreadents_tv);
        selected_item_steps = view.findViewById(R.id.selected_items_steps_tv);
        meal_rating_Bar = view.findViewById(R.id.meal_rating_Bar);



        Bundle bundle = this.getArguments();
        if(getArguments()!=null) {
            Picasso.get().load(bundle.getString("MEAL_IMAGE_URI")).into(selected_item_photo);
            selected_item_ingredients.setText(bundle.getString("MEAL_DESCRIPTION"));
            selected_item_name.setText(bundle.getString("MEAL_NAME"));
            selected_item_steps.setText(bundle.getString("MEAL_STEP"));
            meal_rating_Bar.setRating(Float.parseFloat(bundle.getString("MEAL_RATE")));

        }


//        Intent intent = getActivity().getIntent();
//        Picasso.get().load(intent.getStringExtra(EXTRA_IMAGE_URL)).into(selected_item_photo);
//        selected_item_ingredients.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
//        selected_item_name.setText(intent.getStringExtra(EXTRA_NAME));
//        selected_item_steps.setText(intent.getStringExtra(EXTRA_STEPS));
//        meal_rating_Bar.setRating(Float.parseFloat(intent.getStringExtra(EXTRA_RATE)));


        return view;
    }
}