package com.example.medico.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;

import com.example.medico.R;
import com.example.medico.adapter.SearchRSActAdapter;
import com.example.medico.model.Hospital;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchRS extends AppCompatActivity {
    private ArrayList<Hospital> ArrayListHospital;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rs);

        recyclerView = findViewById(R.id.RecyclerViewSearchRS);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Rumah Sakit");
        databaseReference.keepSynced(true);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayListHospital = new ArrayList<Hospital>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Hospital p = dataSnapshot1.getValue(Hospital.class);
                    ArrayListHospital.add(p);
                }
                SearchRSActAdapter searchRSActAdapter = new SearchRSActAdapter(ArrayListHospital, getApplicationContext());
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                recyclerView.setAdapter(searchRSActAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toolbar ToolbarNavigation = (Toolbar) findViewById(R.id.ToolbarSearchRSNavigation);

        setSupportActionBar(ToolbarNavigation);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
