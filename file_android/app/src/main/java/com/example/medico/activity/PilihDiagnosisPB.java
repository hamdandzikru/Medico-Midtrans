package com.example.medico.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.medico.R;
import com.example.medico.adapter.ItemDiagnosisAdapter;
import com.example.medico.model.DiagnosisRS;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PilihDiagnosisPB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_diagnosis_pb);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView TVToolbar = findViewById(R.id.TVToolbar);
        TVToolbar.setText("Daftar Diagnosis");

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(PilihDiagnosisPB.this));
        Intent before = getIntent();

        String NamaRS = "";

        if (getIntent().getExtras() != null){ //ada intent
            //NamaRS = before.getStringExtra(getString(R.string.NamaRS));
            NamaRS = "RS Advent Bandung";
            setSharedPreferenceLocal(getString(R.string.NamaRS),NamaRS);
        }
        else{
            //NamaRS = getSharedPreferenceLocal(getString(R.string.NamaRS));
            NamaRS = "RS Advent Bandung";
            Log.d("TAG","Nama RS Tanpa Intent :" + getSharedPreferenceLocal(getString(R.string.NamaRS)));
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("DiagnosisRS").child(NamaRS);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<DiagnosisRS> dataList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    DiagnosisRS diagnosisRS = dataSnapshot1.getValue(DiagnosisRS.class);
                    dataList.add(diagnosisRS);
                }
                ItemDiagnosisAdapter adapter = new ItemDiagnosisAdapter(dataList,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setSharedPreferenceLocal(String key, String value){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.Tampungan),MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    private String getSharedPreferenceLocal(String key){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.Tampungan),MODE_PRIVATE);
        return sharedPreferences.getString(key,"Belum terisi");
    }
}
