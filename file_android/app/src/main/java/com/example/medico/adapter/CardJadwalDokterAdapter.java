package com.example.medico.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medico.FungsiFungsi.StringFunction;
import com.example.medico.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CardJadwalDokterAdapter extends RecyclerView.Adapter<CardJadwalDokterAdapter.ViewHolder> {
    private ArrayList<String> dataJam;
    private Context context;
    private String kelompok, hari;

    public CardJadwalDokterAdapter(ArrayList<String> dataJam, Context context, String kelompok, String hari) {
        this.dataJam = dataJam;
        this.context = context;
        this.kelompok = kelompok;
        this.hari = hari;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View v = layoutInflater.inflate(R.layout.item_card_jadwal_dokter,viewGroup,false);
        return new CardJadwalDokterAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.TVJam.setText(dataJam.get(i));
        String NamaRS = getSharedPreferenceLocal(context.getString(R.string.NamaRS));
        String jam = StringFunction.DeleteDotFromString(dataJam.get(i));
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("DokterLayananRS").child("RS Advent Bandung").child(kelompok).child(hari).child(jam);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> dataDokter = new ArrayList<>();
                dataDokter = StringFunction.ParseOtherHospital(dataSnapshot.getValue().toString());
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                ListDokterDiJadwalAdapter listDokterDiJadwalAdapter = new ListDokterDiJadwalAdapter(dataDokter,context);
                holder.recyclerView.setAdapter(listDokterDiJadwalAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataJam.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TVJam;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVJam = itemView.findViewById(R.id.TVJam);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }

    private String getSharedPreferenceLocal(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Tampungan",context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"Belum terisi");
    }
}
