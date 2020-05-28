package com.example.medico.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medico.R;
import com.example.medico.model.Hospital;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RumahSakitLainnyaAdapter extends RecyclerView.Adapter<RumahSakitLainnyaAdapter.HospitalViewHolder> {
    private ArrayList<Hospital> dataList;
    private Context context;

    public RumahSakitLainnyaAdapter(ArrayList<Hospital> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_rs_lainnya,viewGroup,false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int i) {
        holder.CVRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context, VerifikasiPasien.class);
                //context.startActivity(intent);
            }
        });
        Picasso.with(context).load(dataList.get(i).getUrl_profil()).into(holder.IVPhoto_profile_RS);
        holder.TVNamaRS_RecyclerView.setText(dataList.get(i).getNama());
        holder.TVJenisRS.setText(dataList.get(i).getHospitalType());
        holder.TVAddress.setText(dataList.get(i).getShortAddress());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class HospitalViewHolder extends RecyclerView.ViewHolder {
        CardView CVRecyclerView;
        ImageView IVPhoto_profile_RS;
        TextView TVNamaRS_RecyclerView, TVJenisRS, TVAddress ;

        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            CVRecyclerView = itemView.findViewById(R.id.CVRecyclerView);
            IVPhoto_profile_RS = itemView.findViewById(R.id.IVPhoto_profile_RS);
            TVNamaRS_RecyclerView = itemView.findViewById(R.id.TVNamaRS_RecyclerView);
            TVJenisRS = itemView.findViewById(R.id.TVJenisRS);
            TVAddress = itemView.findViewById(R.id.TVAddress);
        }
    }
}
