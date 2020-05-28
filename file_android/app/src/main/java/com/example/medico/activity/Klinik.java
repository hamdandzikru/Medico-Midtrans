package com.example.medico.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medico.R;
import com.squareup.picasso.Picasso;

public class Klinik extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klinik);

        Toolbar toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView TVToolbar = findViewById(R.id.TVToolbar);
        TVToolbar.setText("Klinik");

        ImageView IV = findViewById(R.id.IV);
        Picasso.with(Klinik.this).load("https://firebasestorage.googleapis.com/v0/b/medico-8c179.appspot.com/o/storage%2Fikon%2Fundraw_under_construction_46pa.png?alt=media&token=9ba179b5-b717-4aa3-8d9f-1fcec7aed597").into(IV);
    }
}
