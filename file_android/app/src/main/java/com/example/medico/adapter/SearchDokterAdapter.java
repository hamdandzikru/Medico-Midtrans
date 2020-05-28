package com.example.medico.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medico.R;
import com.example.medico.activity.VerifikasiPasien;
import com.example.medico.model.Doctor;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchDokterAdapter extends RecyclerView.Adapter<SearchDokterAdapter.DokterViewHolDer> {
    private ArrayList<Doctor> dataList;
    private Context context;

    public SearchDokterAdapter(ArrayList<Doctor> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public DokterViewHolDer onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_search_dokter, parent, false);
        return new DokterViewHolDer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DokterViewHolDer holder, final int i) {
        Picasso.with(context).load(dataList.get(i).getImg_url()).placeholder(R.drawable.dokter_placeholder).fit().into(holder.IVProfilDoctor);
        holder.TVNamaDokter.setText(dataList.get(i).getName());
        if (dataList.get(i).getSpecialist().equals("Umum")){
            holder.TVSpesialis.setText("Dokter Umum");
        }
        else{
            holder.TVSpesialis.setText("Spesialis " + dataList.get(i).getSpecialist());
        }
        /*
        float rating = dataList.get(i).getRating();
        String UrlGreyStar = "https://firebasestorage.googleapis.com/v0/b/medico.appspot.com/o/Gambar%20Star%20Rating%2Fgrey_star.png?alt=media&token=ae553573-c3e4-448b-9d91-278184ffa127";
        String UrlYellowStar = "https://firebasestorage.googleapis.com/v0/b/medico.appspot.com/o/Gambar%20Star%20Rating%2Fyellow_star.png?alt=media&token=d4af0c59-9ab4-4bb8-a1cf-e647c54433bb";

        if (rating == 5.0){
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVFirstStar);
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVSecondStar);
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVThirdStar);
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVFourthStar);
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVFifthStar);
        }
        else if (rating >= 4){
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVFirstStar);
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVSecondStar);
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVThirdStar);
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVFourthStar);
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVFifthStar);
        }
        else if (rating >= 3){
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVFirstStar);
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVSecondStar);
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVThirdStar);
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVFourthStar);
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVFifthStar);
        }
        else if (rating >=2){
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVFirstStar);
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVSecondStar);
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVThirdStar);
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVFourthStar);
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVFifthStar);
        }
        else if (rating >= 1){
            Picasso.with(context).load(UrlYellowStar).fit().into(holder.IVFirstStar);
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVSecondStar);
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVThirdStar);
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVFourthStar);
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVFifthStar);
        }
        else{
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVFirstStar);
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVSecondStar);
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVThirdStar);
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVFourthStar);
            Picasso.with(context).load(UrlGreyStar).fit().into(holder.IVFifthStar);
        }
        */
        holder.BtnBuatAntrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VerifikasiPasien.class);
                intent.putExtra(context.getString(R.string.JenisLayanan),context.getString(R.string.Dokter));
                intent.putExtra(context.getString(R.string.NamaDokter),dataList.get(i).getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class DokterViewHolDer extends RecyclerView.ViewHolder {
        ImageView IVProfilDoctor,IVFirstStar,IVSecondStar,IVThirdStar,IVFourthStar,IVFifthStar;
        TextView TVNamaDokter,TVSpesialis;
        Button BtnBuatAntrian;
        public DokterViewHolDer(@NonNull View itemView) {
            super(itemView);
            IVProfilDoctor = itemView.findViewById(R.id.IVProfilDoctor);
            IVFirstStar = itemView.findViewById(R.id.IVFirstStar);
            IVSecondStar = itemView.findViewById(R.id.IVSecondStar);
            IVThirdStar = itemView.findViewById(R.id.IVThirdStar);
            IVFourthStar = itemView.findViewById(R.id.IVFourthStar);
            IVFifthStar = itemView.findViewById(R.id.IVFifthStar);
            TVNamaDokter = itemView.findViewById(R.id.TVNamaDokter);
            TVSpesialis = itemView.findViewById(R.id.TVSpesialis);
            BtnBuatAntrian = itemView.findViewById(R.id.BtnBuatAntrian);
        }
    }
}
