package com.example.medico.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medico.FungsiFungsi.StringFunction;
import com.example.medico.R;
import com.example.medico.activity.VerifikasiPasienLama;
import com.example.medico.model.NomorKK;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PasienLamaAdapter extends RecyclerView.Adapter<PasienLamaAdapter.PasienLamaViewHolder> {
    Context context;
    private String nama_layanan;
    private String jns_layanan;

    public PasienLamaAdapter(Context context) {
        this.context = context;
    }

    public void setNama_layanan(String nama_layanan) {
        this.nama_layanan = nama_layanan;
    }

    public void setJns_layanan(String jns_layanan) {
        this.jns_layanan = jns_layanan;
    }

    @NonNull
    @Override
    public PasienLamaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.verifikasi_pasien_lama_item, parent, false);
        return new PasienLamaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PasienLamaViewHolder holder, int i) {
        holder.TVPeringatan.setVisibility(View.GONE);
        holder.BtnGoToVerifikasiPasienLama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.BtnGoToVerifikasiPasienLama.setText("Mencari...");
                holder.BtnGoToVerifikasiPasienLama.setEnabled(false);
                final Intent intent = new Intent(context, VerifikasiPasienLama.class);
                final DatabaseReference reference;
                if (jns_layanan.equals("RumahSakit")){
                    intent.putExtra(context.getString(R.string.NamaRS),nama_layanan);
                    reference = FirebaseDatabase.getInstance().getReference().child(context.getString(R.string.nomorKK)).child(context.getString(R.string.RumahSakit)).child(StringFunction.DeleteDotFromString(nama_layanan));
                }
                else{
                    intent.putExtra(context.getString(R.string.NamaDokter),nama_layanan);
                    reference = FirebaseDatabase.getInstance().getReference().child(context.getString(R.string.nomorKK)).child(context.getString(R.string.Dokter)).child(StringFunction.DeleteDotFromString(nama_layanan));
                }
                final ArrayList<NomorKK> dataList = new ArrayList<>();

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            NomorKK nomorKK = dataSnapshot1.getValue(NomorKK.class);
                            dataList.add(nomorKK);
                        }
                        boolean ketemu = false;
                        String nama = "", ttl = "", alamat = "", nik = "";
                        int i = 0;
                        for (NomorKK nomorKK : dataList){
                            if (nomorKK.getNik().equals(holder.ETNoRekamMedis.getText().toString())){
                                ketemu = true;
                                nama = dataList.get(i).getNama();
                                ttl = dataList.get(i).getTempat_lahir() + dataList.get(i).getTanggal_lahir() + dataList.get(i).getBulan_lahir() + dataList.get(i).getTahun_lahir();
                                alamat = dataList.get(i).getAlamat();
                                nik = dataList.get(i).getNik();
                                break;
                            }
                            i++;
                        }
                        if (ketemu) {
                            intent.putExtra(context.getString(R.string.nomorKK),holder.ETNoRekamMedis.getText().toString());
                            intent.putExtra("NamaPasien",nama);
                            intent.putExtra("TTLPasien",ttl);
                            intent.putExtra("AlamatPasien",alamat);
                            intent.putExtra("NIKPasien",nik);
                            context.startActivity(intent);
                        }
                        else{
                            Toast.makeText(context,"Nomor NIK tidak ditemukan",Toast.LENGTH_LONG).show();
                            holder.BtnGoToVerifikasiPasienLama.setEnabled(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class PasienLamaViewHolder extends RecyclerView.ViewHolder {
        TextView TVNoRekamMedis, TVPeringatan;
        EditText ETNoRekamMedis;
        Button BtnGoToVerifikasiPasienLama;
        public PasienLamaViewHolder(@NonNull View itemView) {
            super(itemView);
            TVNoRekamMedis = itemView.findViewById(R.id.TVNomorRekamMedis);
            TVPeringatan = itemView.findViewById(R.id.TVPeringatan);
            ETNoRekamMedis = itemView.findViewById(R.id.ETNama);
            BtnGoToVerifikasiPasienLama = itemView.findViewById(R.id.BtnGoToVerifikasiPasienLama);
        }
    }
}
