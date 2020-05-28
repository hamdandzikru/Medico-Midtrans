package com.example.medico.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.medico.activity.ProfilRS;
import com.example.medico.R;
import com.example.medico.model.Hospital;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SearchRSActAdapter extends RecyclerView.Adapter<SearchRSActAdapter.HospitalViewHolder> {
    private ArrayList<Hospital> dataList;
    private LayoutInflater mInflater;
    private Context context;
    //private ItemClickListener mClickListener;

    String email_key = "";
    String email_key_new = "";

    public SearchRSActAdapter (ArrayList<Hospital> dataList, Context context ){
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.searchrs_item, parent, false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int i) {
        holder.TVNamaRS_RecyclerView.setText(dataList.get(i).getNama());
        holder.TVJenisRS.setText(dataList.get(i).getHospitalType());
        holder.TVAddress.setText(dataList.get(i).getShortAddress());
        Picasso.with(context).load(dataList.get(i).getUrl_profil()).fit().into(holder.IVPhoto_profile_RS);

        final String nama_rs = dataList.get(i).getNama();
        final String url_map = dataList.get(i).getUrl_alamat();
        holder.CVRecyclerViewSearchRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(context, ProfilRS.class);
                profil.putExtra(context.getString(R.string.NamaRS),nama_rs);
                setSharedPreferenceLocal(context.getString(R.string.NamaRS),nama_rs);
                getUsernameLocal();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tampungan").child("users").child(email_key_new).child(context.getString(R.string.NamaRS));
                reference.setValue(nama_rs);
                profil.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(profil);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class HospitalViewHolder extends RecyclerView.ViewHolder {
        TextView TVNamaRS_RecyclerView, TVJenisRS, TVAddress;
        ImageView IVPhoto_profile_RS;
        CardView CVRecyclerViewSearchRS;
        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            TVNamaRS_RecyclerView = itemView.findViewById(R.id.TVNamaRS_RecyclerView);
            TVJenisRS = itemView.findViewById(R.id.TVJenisRS);
            TVAddress = itemView.findViewById(R.id.TVAddress);
            IVPhoto_profile_RS = itemView.findViewById(R.id.IVPhoto_profile_RS);
            CVRecyclerViewSearchRS = itemView.findViewById(R.id.CVRecyclerViewSearchRS);
        }
    }

    private void setSharedPreferenceLocal(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.Tampungan),MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    private void getUsernameLocal(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("id", MODE_PRIVATE);
        email_key_new = sharedPreferences.getString(email_key,"");
    }
}
