package com.example.hanakol_2ah.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanakol_2ah.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EditItemFragment extends Fragment {

    private ImageView selected_item_photo, back_icon, save_icon;
    private EditText selected_item_name, selected_item_steps, selected_item_ingredients;
    private TextView selected_item_owner_name, meal_creation_date;
    private RatingBar meal_rating_Bar;
    private List<ListMealsFragmentContainer> fragmentList;
    private String imageUri, child;
    private String IMAGE_URL, MEAL_NAME, MEAL_DESCRIPTION, MEAL_STEP, MEAL_OWNER_EMAIL, MEAL_CREATION_DATE;
    private Float MEAL_RATE_BAR;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private View view;


    public EditItemFragment(String child){
        this.child = child;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_edit_item, container, false);
        this.view = view;

        back_icon = view.findViewById(R.id.back_click_image);
        save_icon = view.findViewById(R.id.save_edite_image);
        selected_item_name = view.findViewById(R.id.selected_item_name_ET);
        selected_item_ingredients = view.findViewById(R.id.selected_item_ingreadents_ET);
        meal_rating_Bar = view.findViewById(R.id.meal_rating_Bar);
        selected_item_steps = view.findViewById(R.id.selected_items_steps_ET);
        selected_item_owner_name = view.findViewById(R.id.selected_item_owner_name_tv);
        meal_creation_date = view.findViewById(R.id.upload_date_txt);
        selected_item_photo = view.findViewById(R.id.selected_item_image_iv);

        Bundle bundle = this.getArguments();
        if (getArguments() != null) {
            Picasso.get().load(bundle.getString("MEAL_IMAGE_URI")).into(selected_item_photo);
            IMAGE_URL = bundle.getString("MEAL_IMAGE_URI");
            selected_item_ingredients.setText(bundle.getString("MEAL_DESCRIPTION"));
            MEAL_DESCRIPTION = bundle.getString("MEAL_DESCRIPTION");
            selected_item_name.setText(bundle.getString("MEAL_NAME"));
            MEAL_NAME = bundle.getString("MEAL_NAME");
            selected_item_steps.setText(bundle.getString("MEAL_STEP"));
            MEAL_STEP = bundle.getString("MEAL_STEP");
            selected_item_owner_name.setText(bundle.getString("MEAL_OWNER_EMAIL"));
            MEAL_OWNER_EMAIL = bundle.getString("MEAL_OWNER_EMAIL");
            meal_creation_date.setText(bundle.getString("MEAL_CREATION_DATE"));
            MEAL_CREATION_DATE = bundle.getString("MEAL_CREATION_DATE");
//            child = bundle.getString("CHILD");
        }

        save_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatData(child , MEAL_NAME);
            }
        });

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListMealsFragmentContainer fragment = new ListMealsFragmentContainer(child);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.activity_home_container, fragment);
                fragmentTransaction.commit();
//                proceedWithBack();

            }
        });


        return view;
    }

    private void UpdatData(String child , String mealName) {
        DocumentReference meal = db.collection(child).document(mealName);

//        meal.update("mealName", selected_item_name.getText().toString());
        meal.update("description", selected_item_ingredients.getText().toString());
        meal.update("steps", selected_item_steps.getText().toString());


    }

}

