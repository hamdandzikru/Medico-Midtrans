package com.example.medico.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medico.R;
import com.example.medico.model.DiagnosisRS;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ItemDiagnosisAdapter extends RecyclerView.Adapter<ItemDiagnosisAdapter.ViewHolder> {
    private int selectedPos = RecyclerView.NO_POSITION;
    private ArrayList<DiagnosisRS> dataList;
    private Context context;

    String email_key = "";
    String email_key_new = "";

    public ItemDiagnosisAdapter(ArrayList<DiagnosisRS> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_diagnosis,viewGroup,false);
        return new ItemDiagnosisAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        holder.itemView.setSelected(selectedPos == i);
        holder.TVNama.setText(dataList.get(i).getNama());
        holder.TVDesc.setText(dataList.get(i).getDesc());
        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selectedPos);
                selectedPos = i;
                notifyItemChanged(selectedPos);
                getUsernameLocal();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tampungan").child("users").child(email_key_new).child("PilihanDiagnosis");
                reference.setValue(dataList.get(i).getNama());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TVNama,TVDesc;
        ConstraintLayout cl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVNama = itemView.findViewById(R.id.TVNama);
            TVDesc = itemView.findViewById(R.id.TVDesc);
            cl = itemView.findViewById(R.id.cl);
        }
    }

    private void getUsernameLocal(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("id", MODE_PRIVATE);
        email_key_new = sharedPreferences.getString(email_key,"");
    }
}
