package com.example.hanakol_2ah;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Meals> MealsList;

    public MyAdapter(List<Meals> mealsList) {
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
        Meals meals = MealsList.get(position);
        holder.name_item_recycler.setText(meals.getName());
        holder.ingredients_item_recycler.setText(meals.getIngredients());
        holder.steps_item_recycler.setText(meals.getSteps());
    }

    @Override
    public int getItemCount() {
        return MealsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name_item_recycler, ingredients_item_recycler, steps_item_recycler;

        public ViewHolder(View itemView) {
            super(itemView);
            name_item_recycler = itemView.findViewById(R.id.name_item_recycler);
            ingredients_item_recycler = itemView.findViewById(R.id.ingredients_item_recycler);
            steps_item_recycler = itemView.findViewById(R.id.steps_item_recycler);
        }
    }
}
