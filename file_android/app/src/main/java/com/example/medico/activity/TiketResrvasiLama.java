package com.example.medico.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.example.medico.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TiketResrvasiLama extends AppCompatActivity {
    Toolbar toolbar;

    String email_key = "";
    String email_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_resrvasi_lama);

        Intent before = getIntent();
        getUsernameLocal();
        toolbar = (Toolbar) findViewById(R.id.ToolbarTiketReservasi);

        TextView toolbar_text = findViewById(R.id.toolbar_text);
        final TextView TVNamaRS = findViewById(R.id.TVNamaRS);
        final TextView TVMedicalCheckUp = findViewById(R.id.TVMedicalCheckUp);
        final TextView TVHargaPelayanan = findViewById(R.id.HargaPelayanan);
        final TextView TVTanggalPelayanan = findViewById(R.id.TanggalPelayanan);
        final TextView TVWaktuPelayanan = findViewById(R.id.WaktuPelayanan);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_text.setText("Tiket Reservasi");

        final String IdTicket = before.getStringExtra(getString(R.string.IdTicket));

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tiket").child(email_key_new).child(IdTicket);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TVNamaRS.setText(dataSnapshot.child("penyedia_layanan").getValue().toString());
                TVMedicalCheckUp.setText(dataSnapshot.child("jns_layanan").getValue().toString());
                TVHargaPelayanan.setText(dataSnapshot.child("harga_layanan").getValue().toString());
                TVTanggalPelayanan.setText(dataSnapshot.child("tanggal").getValue().toString());
                TVWaktuPelayanan.setText(dataSnapshot.child("waktu").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        email_key_new = sharedPreferences.getString(email_key,"");
    }
}
