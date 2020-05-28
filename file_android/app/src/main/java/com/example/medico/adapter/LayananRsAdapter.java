package com.example.medico.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.medico.R;
import com.example.medico.activity.VerifikasiPasienFromLayananFragment;
import com.example.medico.model.LayananRS;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class LayananRsAdapter extends RecyclerView.Adapter<LayananRsAdapter.ViewHolder> {
    private ArrayList<LayananRS> dataList;
    private Context context;

    public LayananRsAdapter(ArrayList<LayananRS> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_layanan_rs_rv, viewGroup, false);
        return new LayananRsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        holder.TVLayanan.setText(dataList.get(i).getNama());
        holder.TVHarga.setText(dataList.get(i).getHarga());
        holder.TVDesc.setText(dataList.get(i).getDesc());
        holder.BtnBuatAntrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VerifikasiPasienFromLayananFragment.class);
                intent.putExtra(context.getString(R.string.JenisLayanan),context.getString(R.string.RumahSakit));
                intent.putExtra(context.getString(R.string.PenyediaLayanan),getSharedPreferenceLocal("NamaRS"));
                intent.putExtra(context.getString(R.string.Layanan),holder.TVLayanan.getText().toString());
                intent.putExtra(context.getString(R.string.Kelompok),dataList.get(i).getKelompok());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TVLayanan, TVHarga, TVDesc;
        Button BtnBuatAntrian;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVLayanan = itemView.findViewById(R.id.TVLayanan);
            TVHarga = itemView.findViewById(R.id.TVHarga);
            TVDesc = itemView.findViewById(R.id.TVDesc);
            BtnBuatAntrian = itemView.findViewById(R.id.BtnBuatAntrian);
        }
    }

    private void setSharedPreferenceLocal(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.Tampungan), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    private String getSharedPreferenceLocal(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.Tampungan),MODE_PRIVATE);
        return sharedPreferences.getString(key,"Belum terisi");
    }
}
