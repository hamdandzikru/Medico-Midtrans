package com.example.medico.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medico.FungsiFungsi.StringFunction;
import com.example.medico.R;
import com.example.medico.model.LayananRS;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FragmentDokterRSAdapter extends RecyclerView.Adapter<FragmentDokterRSAdapter.ViewHolder> {
    private String NamaRS;
    private Context context;
    private android.os.Handler h = new Handler();
    Runnable runnable;

    String email_key = "";
    String email_key_new = "";

    public FragmentDokterRSAdapter( Context context1) {
        context = context1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_fragment_dokter_rs,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.recyclerViewKelompok.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.recyclerViewHari.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.recyclerViewJadwal.setLayoutManager(new LinearLayoutManager(context));

        //mencari nama rs
        getUsernameLocal();
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Tampungan").child("users").child(email_key_new).child("NamaRS");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("TAG","Nama RS di database :" + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("LayananRS").child("RS Advent Bandung");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<LayananRS> dataList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    LayananRS layananRS = dataSnapshot1.getValue(LayananRS.class);
                    dataList.add(layananRS);
                }
                ArrayList<String> dataKelompok = new ArrayList<>();
                for (LayananRS layananRS : dataList){
                    if (dataKelompok.size() == 0){
                        dataKelompok.add(layananRS.getKelompok());
                    }
                    else{
                        boolean termasuk = false;
                        for (String string : dataKelompok){
                            if (!termasuk &&string.equals(layananRS.getKelompok())){
                                termasuk = true;
                            }
                        }
                        if (!termasuk){
                            dataKelompok.add(layananRS.getKelompok());
                        }
                    }
                }

                KelompokLayananUntukFragmentDokterAdapter kelompokLayananUntukFragmentDokterAdapter = new KelompokLayananUntukFragmentDokterAdapter(dataKelompok,context);
                holder.recyclerViewKelompok.setAdapter(kelompokLayananUntukFragmentDokterAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }); //akhir reference mencari layanan

        PilihHariDokterRSAdapter pilihHariDokterRSAdapter = new PilihHariDokterRSAdapter(NamaRS,context);
        holder.recyclerViewHari.setAdapter(pilihHariDokterRSAdapter);

        int j = 0;
        final String KelompokTemp = "";
        final String HariTemp = "";
        final boolean lanjut = true;

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Tampungan").child("users").child(email_key_new).child("Telah Memilih Kelompok Layananfor dokter fragment");
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.getValue().toString().equals("null")){
                            final String kelompok = dataSnapshot.getValue().toString();
                            DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference().child("Tampungan").child("users").child(email_key_new).child("TelahMemilihHariUntukDokterRSFragment");
                            reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.getValue().toString().equals("null")){
                                        final String hari = dataSnapshot.getValue().toString();
                                        DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference().child("JadwalLayananRS").child("RS Advent Bandung").child(kelompok).child(hari);
                                        reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String dataMentahHari = dataSnapshot.getValue().toString();
                                                ArrayList<String> dataJam = StringFunction.ParseOtherHospital(dataMentahHari);
                                                CardJadwalDokterAdapter cardJadwalDokterAdapter = new CardJadwalDokterAdapter(dataJam,context,kelompok,hari);
                                                holder.recyclerViewJadwal.setAdapter(cardJadwalDokterAdapter);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                runnable=this;
                h.postDelayed(runnable, 1);
            }
        },1);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerViewKelompok,recyclerViewHari,recyclerViewJadwal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewKelompok = itemView.findViewById(R.id.recyclerViewKelompok);
            recyclerViewHari = itemView.findViewById(R.id.recyclerViewHari);
            recyclerViewJadwal = itemView.findViewById(R.id.recyclerViewJadwal);
        }
    }

    private String getSharedPreferenceLocal(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Tampungan",context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"Belum terisi");
    }

    private void getUsernameLocal(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("id", Context.MODE_PRIVATE);
        email_key_new = sharedPreferences.getString(email_key,"");
    }

    private void setSharedPreferenceLocal(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.Tampungan),MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
}
