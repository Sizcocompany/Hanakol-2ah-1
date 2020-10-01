package com.example.hanakol_2ah.adapters;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class MealAdapter extends FirestoreRecyclerAdapter<Meals, MealAdapter.NoteHolder> {
    private OnItemClickListener listener;

    public MealAdapter(@NonNull FirestoreRecyclerOptions<Meals> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Meals meals) {
        holder.name_item_recycler.setText(meals.getMealName());
        holder.ingredients_item_recycler.setText(meals.getDescription());
        holder.steps_item_recycler.setText(meals.getSteps());
        holder.meal_rating_Bar.setRating(meals.getMealRate());
        Picasso.get().load(meals.getImageURL()).into(holder.mealImageView);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager_header_shimmer,
                parent, false);
        return new NoteHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView name_item_recycler, ingredients_item_recycler, steps_item_recycler;
        private ImageView mealImageView;
        private RatingBar meal_rating_Bar;

        public NoteHolder(View itemView) {
            super(itemView);

            mealImageView = itemView.findViewById(R.id.mealImageView);
            name_item_recycler = itemView.findViewById(R.id.meal_name_item_recycler);
            ingredients_item_recycler = itemView.findViewById(R.id.meal_ingredients_item_recycler);
            steps_item_recycler = itemView.findViewById(R.id.meal_steps_item_recycler);
            meal_rating_Bar = itemView.findViewById(R.id.meal_rating_Bar);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}