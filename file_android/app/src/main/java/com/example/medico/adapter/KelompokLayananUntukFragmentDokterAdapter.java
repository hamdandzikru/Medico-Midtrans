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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class KelompokLayananUntukFragmentDokterAdapter extends RecyclerView.Adapter<KelompokLayananUntukFragmentDokterAdapter.ViewHolder> {
    private int selectedPos = RecyclerView.NO_POSITION;
    private ArrayList<String> dataList;
    private Context context;
    String email_key_new = "";


    public KelompokLayananUntukFragmentDokterAdapter(ArrayList<String> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_kelompok,viewGroup,false);
        return new KelompokLayananUntukFragmentDokterAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.TVKelompok.setText(dataList.get(i));
        holder.itemView.setSelected(selectedPos == i);
        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selectedPos);
                selectedPos = holder.getLayoutPosition();
                notifyItemChanged(selectedPos);
                getUsernameLocal();
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child(context.getString(R.string.Tampungan)).child("users").child(email_key_new).child(context.getString(R.string.TelahMemilihKelompokLayanan) + "for dokter fragment");
                reference1.setValue(holder.TVKelompok.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TVKelompok;
        ConstraintLayout cl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVKelompok = itemView.findViewById(R.id.TVKelompok);
            cl = itemView.findViewById(R.id.cl);
        }
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("id", MODE_PRIVATE);
        String email_key = "";
        email_key_new = sharedPreferences.getString(email_key,"");
    }
}
