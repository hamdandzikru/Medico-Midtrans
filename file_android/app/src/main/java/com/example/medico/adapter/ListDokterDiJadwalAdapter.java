package com.example.medico.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medico.R;

import java.util.ArrayList;

public class ListDokterDiJadwalAdapter extends RecyclerView.Adapter<ListDokterDiJadwalAdapter.ViewHolder> {
    private ArrayList<String> dataList;
    private Context context;

    public ListDokterDiJadwalAdapter(ArrayList<String> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View v = layoutInflater.inflate(R.layout.item_dokter_jadwal,viewGroup,false);
        return new ListDokterDiJadwalAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.TVNamaDokter.setText(dataList.get(i));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TVNamaDokter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVNamaDokter = itemView.findViewById(R.id.TVNamaDokter);
        }
    }
}
