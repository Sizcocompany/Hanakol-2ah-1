package com.example.hanakol_2ah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.models.Meals;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Meals> MealsList;
    Context context;

    public MyAdapter(Context context,List<Meals> mealsList) {

        this.MealsList = mealsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_pager_header_shimmer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meals meals = MealsList.get(position);
        holder.name_item_recycler.setText(meals.getMealName());
        holder.ingredients_item_recycler.setText(meals.getDescription());
        holder.steps_item_recycler.setText(meals.getSteps());
        holder.meal_rating_Bar.setRating(meals.getMealRate());
        Picasso.get().load(meals.getImageURL()).into(holder.mealImageView);
    }

    @Override
    public int getItemCount() {
        return MealsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name_item_recycler, ingredients_item_recycler, steps_item_recycler;
        private ImageView mealImageView;
        private RatingBar meal_rating_Bar;

        public ViewHolder(View itemView) {
            super(itemView);
            mealImageView = itemView.findViewById(R.id.mealImageView);
            name_item_recycler = itemView.findViewById(R.id.meal_name_item_recycler);
            ingredients_item_recycler = itemView.findViewById(R.id.meal_ingredients_item_recycler);
            steps_item_recycler = itemView.findViewById(R.id.meal_steps_item_recycler);
            meal_rating_Bar = itemView.findViewById(R.id.meal_rating_Bar);

        }
    }
}

