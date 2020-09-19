package com.example.hanakol_2ah.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.models.ModelMeals;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<ModelMeals> MealsList;

    public MyAdapter(List<ModelMeals> mealsList) {
        this.MealsList = mealsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager_header_shimmer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelMeals meals = MealsList.get(position);
        holder.name_item_recycler.setText(meals.getMealName());
        holder.ingredients_item_recycler.setText(meals.getMealIngredients());
        holder.steps_item_recycler.setText(meals.getMealSteps());
        String photoUrl = meals.getImageUrl();
        photoUrl = photoUrl + "?type=large";
        Picasso.get().load(photoUrl).into(holder.mealImageView);
    }

    @Override
    public int getItemCount() {
        return MealsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name_item_recycler, ingredients_item_recycler, steps_item_recycler;
        private ImageView mealImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            name_item_recycler = itemView.findViewById(R.id.meal_name_item_recycler);
            ingredients_item_recycler = itemView.findViewById(R.id.meal_ingredients_item_recycler);
            steps_item_recycler = itemView.findViewById(R.id.meal_steps_item_recycler);
            mealImageView = itemView.findViewById(R.id.mealImageView);
        }
    }
}
