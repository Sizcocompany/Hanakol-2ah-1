package com.example.hanakol_2ah.activities;

import android.os.Bundle;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.adapters.FavoritesAdapter;
import com.example.hanakol_2ah.models.Favorites;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView favoritesRecyclerview;
    private FavoritesAdapter favoritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_recycelr);

        favoritesRecyclerview = findViewById(R.id.favoritesRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        favoritesRecyclerview.setLayoutManager(linearLayoutManager);

         ArrayList<Favorites> favoritesList = new ArrayList<>();

         favoritesAdapter = new FavoritesAdapter(this,favoritesList);
         favoritesRecyclerview.setAdapter(favoritesAdapter);
         favoritesAdapter.notifyDataSetChanged();
    }
}
