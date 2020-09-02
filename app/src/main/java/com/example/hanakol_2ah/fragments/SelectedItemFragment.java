package com.example.hanakol_2ah.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hanakol_2ah.R;

public class SelectedItemFragment extends Fragment {

//    i changed the name of that fragment from single view fragment into selected item fragment to be more detailed;

    private ImageView selected_item_photo;
    private TextView selected_item_name, selected_item_steps, selected_item_ingredients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_selected_item, container, false);

        selected_item_photo = view.findViewById(R.id.selected_item_image_iv);
        selected_item_name = view.findViewById(R.id.selected_item_name_tv);
        selected_item_ingredients = view.findViewById(R.id.selected_item_ingreadents_tv);
        selected_item_steps = view.findViewById(R.id.selected_items_steps_tv);







        return view;
    }
}