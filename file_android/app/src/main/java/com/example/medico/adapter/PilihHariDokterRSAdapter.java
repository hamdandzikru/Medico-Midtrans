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

import static android.content.Context.MODE_PRIVATE;

public class PilihHariDokterRSAdapter extends RecyclerView.Adapter<PilihHariDokterRSAdapter.ViewHolder> {
    private int selectedPos = RecyclerView.NO_POSITION;
    private String NamaRS;
    private Context context;

    String email_key = "";
    String email_key_new = "";

    public PilihHariDokterRSAdapter(String namaRS, Context context) {
        NamaRS = namaRS;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_pilih_hari,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        getUsernameLocal();
        holder.itemView.setSelected(selectedPos == i);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tampungan").child("users").child(email_key_new).child("TelahMemilihHariUntukDokterRSFragment");
        if (i == 0){
            holder.TVHari.setText("Senin");
        }
        else if (i == 1){
            holder.TVHari.setText("Selasa");
        }
        else if (i == 2){
            holder.TVHari.setText("Rabu");
        }
        else if (i == 3){
            holder.TVHari.setText("Kamis");
        }
        else if (i == 4){
            holder.TVHari.setText("Jum'at");
        }
        else if (i == 5){
            holder.TVHari.setText("Sabtu");
        }

        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selectedPos);
                selectedPos = holder.getLayoutPosition();
                notifyItemChanged(selectedPos);
                if (i == 0){
                    reference.setValue("Senin");
                }
                else if (i == 1){
                    reference.setValue("Selasa");
                }
                else if (i == 2){
                    reference.setValue("Rabu");
                }
                else if (i == 3){
                    reference.setValue("Kamis");
                }
                else if (i == 4){
                    reference.setValue("Jumat");
                }
                else if (i == 5){
                    reference.setValue("Sabtu");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TVHari;
        ConstraintLayout cl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVHari = itemView.findViewById(R.id.TVHari);
            cl = itemView.findViewById(R.id.cl);
        }
    }

    private void getUsernameLocal(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("id", MODE_PRIVATE);
        email_key_new = sharedPreferences.getString(email_key,"");
    }
}
