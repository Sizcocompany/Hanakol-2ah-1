package com.example.hanakol_2ah.activities;

import android.app.DownloadManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.adapters.FavoritesAdapter;
import com.example.hanakol_2ah.models.Favorites;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritesActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference favoritesRef = db.collection("Favorites");
    private RecyclerView favoritesRecyclerview;
    private FavoritesAdapter favoritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_recycelr);

        setUPRecyclerview();







        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                favoritesAdapter.remove(viewHolder.getAdapterPosition());
                favoritesAdapter.notifyDataSetChanged();
                Toast.makeText(FavoritesActivity.this,"Meal Deleted",Toast.LENGTH_LONG).show();

            }
        }).attachToRecyclerView(favoritesRecyclerview);
    }

    private void setUPRecyclerview() {
        Query query = favoritesRef.orderBy("Rating", Query.Direction.DESCENDING);



        favoritesRecyclerview = findViewById(R.id.favoritesRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        favoritesRecyclerview.setLayoutManager(linearLayoutManager);

        ArrayList<Favorites> favoritesList = new ArrayList<>();

        favoritesAdapter = new FavoritesAdapter(this,favoritesList);
        favoritesRecyclerview.setAdapter(favoritesAdapter);
        favoritesAdapter.notifyDataSetChanged();
    }
}
