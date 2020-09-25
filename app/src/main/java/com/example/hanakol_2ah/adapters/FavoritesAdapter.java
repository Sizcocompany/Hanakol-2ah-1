package com.example.hanakol_2ah.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.models.Favorites;
import com.example.hanakol_2ah.models.Meals;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.favoritesViewHolder> {

    private ArrayList<Favorites> favoritesMealsList;
    Context context;

    public FavoritesAdapter(Context context,ArrayList<Favorites> favoritesMealsList) {

        this.favoritesMealsList = favoritesMealsList;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoritesAdapter.favoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_pager_header_shimmer, parent, false);
        return new FavoritesAdapter.favoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.favoritesViewHolder holder, int position) {
        Favorites favorites = favoritesMealsList.get(position);
        holder.favoritesName.setText(favorites.getFavoritesMealName());
        holder.favoritesIngredients.setText(favorites.getFavoritesDescription());
        holder.favoritesSteps.setText(favorites.getFavoritesSteps());
        holder.favoritesMeal_rating_Bar.setRating(favorites.getFavoritesMealRate());
        Picasso.get().load(favorites.getFavoritesImageURL()).into(holder.favoritesMealImageView);
    }

    @Override
    public int getItemCount() {
        return favoritesMealsList.size();
    }

    public class favoritesViewHolder extends RecyclerView.ViewHolder {
        private TextView favoritesName , favoritesIngredients, favoritesSteps;
        private ImageView favoritesMealImageView;
        private RatingBar favoritesMeal_rating_Bar;

        public favoritesViewHolder(View itemView) {
            super(itemView);
            favoritesMealImageView = itemView.findViewById(R.id.favorites_mealImageView);
            favoritesName = itemView.findViewById(R.id.favorites_meal_name_item_recycler);
            favoritesIngredients = itemView.findViewById(R.id.favorites_meal_ingredients_item_recycler);
            favoritesSteps = itemView.findViewById(R.id.favorites_meal_steps_item_recycler);
            favoritesMeal_rating_Bar = itemView.findViewById(R.id.favorites_meal_rating_Bar);

        }
    }
}
