package com.example.hanakol_2ah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Test1 extends AppCompatActivity {

    FloatingActionButton  floatingActionButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_test1 );

        floatingActionButton = findViewById( R.id.floatingActionButton );

        floatingActionButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( Test1.this , MainActivity.class );
                startActivity( intent );
            }
        } );
    }
}