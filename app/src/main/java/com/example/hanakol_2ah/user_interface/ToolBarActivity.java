package com.example.hanakol_2ah.user_interface;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hanakol_2ah.R;


public class ToolBarActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView ic_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        ic_menu = findViewById(R.id.ic_menu);
        toolbar = findViewById(R.id.toolBar);

        ic_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setSupportActionBar(toolbar);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate( R.menu.menu, menu );
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.profile) {
//            Toast.makeText( this, "profile Selected ", Toast.LENGTH_SHORT ).show();
//        } else if (id == R.id.help) {
//            Toast.makeText( this, "help Selected ", Toast.LENGTH_SHORT ).show();
//        } else if (id == R.id.log_out) {
//            Toast.makeText( this, "logout Selected ", Toast.LENGTH_SHORT ).show();
//
//        } else if (id == R.id.setting) {
//            Toast.makeText( this, "setting Selected ", Toast.LENGTH_SHORT ).show();
//        } else if (id == R.id.search) {
//            Toast.makeText( this, "search Selected ", Toast.LENGTH_SHORT ).show();
//
//        }
//
//        return true;
//    }
}