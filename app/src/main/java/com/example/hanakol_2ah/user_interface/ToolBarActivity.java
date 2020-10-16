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
}