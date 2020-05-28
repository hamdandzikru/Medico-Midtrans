package com.example.medico.activity;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medico.R;
import com.example.medico.model.Bacaan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ReadArticle extends AppCompatActivity {
    Bacaan bacaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_article);

        final TextView TVJudulBacaan = findViewById(R.id.TVJudulBacaan);
        final TextView TVPublisher = findViewById(R.id.TVPublisher);
        final TextView TVPublishTime = findViewById(R.id.TVPublishTime);

        final ImageView IVBacaan = findViewById(R.id.IVBacaan);

        Intent i = getIntent();
        final String title = i.getStringExtra("judul");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Bacaan").child("semua").child(title);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bacaan = dataSnapshot.getValue(Bacaan.class);
                if (dataSnapshot.exists()){
                    Picasso.with(getApplicationContext()).load(bacaan.getUrl_profil()).fit().into(IVBacaan);
                    TVJudulBacaan.setText(bacaan.getJudul());
                    TVPublisher.setText(bacaan.getPublisher());
                    TVPublishTime.setText(bacaan.getPublish_time());
                }
                else{
                    finish();
                    Toast.makeText(getApplicationContext(),"Bacaan Telah dihapus dari Database",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarReadArticle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }
}
