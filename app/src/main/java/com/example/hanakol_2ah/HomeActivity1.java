package com.example.hanakol_2ah;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity1 extends AppCompatActivity {

    private EditText inputSearch;
    RecyclerView recyclerView ;
    FloatingActionButton floatin_btn ;

    FirebaseRecyclerOptions<ModelMeals> options;
    FirebaseRecyclerAdapter<ModelMeals , MyViewHolder> adapter;
    DatabaseReference databaseRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );

        databaseRef = FirebaseDatabase.getInstance().getReference().child( "Meal" ).child( "breakfast" );
        inputSearch = findViewById( R.id.input_serch );
        recyclerView = findViewById( R.id.recycler_view );
        recyclerView.setLayoutManager( new LinearLayoutManager( getApplicationContext() ) );
        recyclerView.setHasFixedSize (true);
        floatin_btn= findViewById( R.id.floating_adding_btn );

        floatin_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext() , MainActivity.class ) );
            }
        } );

        LoadData();
    }

    private void LoadData() {
        options = new FirebaseRecyclerOptions.Builder<ModelMeals>().setQuery( databaseRef , ModelMeals.class ).build();
        adapter = new FirebaseRecyclerAdapter<ModelMeals, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull ModelMeals model) {

                holder.textView.setText( model.getMealName() );
                Picasso.get().load( model.getImageUrl() ).into( holder.imageView );

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View  view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.single_view , parent , false );
                return new MyViewHolder( view );
            }
        };

        adapter.startListening();
        recyclerView.setAdapter( adapter );
    }
}