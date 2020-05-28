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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class WaktuBerobatAdapter extends RecyclerView.Adapter<WaktuBerobatAdapter.ViewHolder> {
    private int selectedPos = RecyclerView.NO_POSITION;
    private Context context;
    private ArrayList<String> dataList;
    String email_key = "";
    String email_key_new = "";

    public WaktuBerobatAdapter(Context context, ArrayList<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_waktu_berobat,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        holder.text.setText(dataList.get(i));
        final String string = String.valueOf(i);
        getUsernameLocal();
        final String username = email_key_new;
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        holder.itemView.setSelected(selectedPos == i);
        holder.CL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selectedPos);
                selectedPos = holder.getLayoutPosition();
                notifyItemChanged(selectedPos);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference.getRef().child("Tampungan").child("users").child(email_key_new).child("IndeksWaktuBerobat").setValue(string);
                        reference.getRef().child("Tampungan").child("users").child(email_key_new).child(context.getString(R.string.WaktuBerobat)).setValue(dataList.get(i));
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
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout CL;
        TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CL = itemView.findViewById(R.id.CL);
            text = itemView.findViewById(R.id.text);
        }
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("id", MODE_PRIVATE);
        email_key_new = sharedPreferences.getString(email_key,"");
    }
}
