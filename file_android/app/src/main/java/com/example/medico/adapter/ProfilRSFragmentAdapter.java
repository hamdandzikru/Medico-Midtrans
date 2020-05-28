package com.example.medico.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medico.FungsiFungsi.StringFunction;
import com.example.medico.R;
import com.example.medico.model.Hospital;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ProfilRSFragmentAdapter extends RecyclerView.Adapter<ProfilRSFragmentAdapter.HospitalViewHolder> {
    private Context context;
    private String NamaRS;

    public ProfilRSFragmentAdapter(Context  context1) {
        context = context1;
        NamaRS = getSharedPreferenceLocal(context1.getString(R.string.NamaRS));
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_fragment_profil_rs,viewGroup,false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HospitalViewHolder holder, int i) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(context.getString(R.string.RumahSakit)).child(StringFunction.DeleteDotFromString(NamaRS));
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Hospital hospital = dataSnapshot.getValue(Hospital.class);
                holder.TVRSDesc.setText(hospital.getDeskripsi());
                holder.TVKontakIGD.setText(hospital.getIGD());
                holder.TVKontakInformasi.setText(hospital.getInformation());
                holder.TVKontakEmail.setText(hospital.getEmail());

                final ArrayList<String> dataListString = StringFunction.ParseOtherHospital(hospital.getRSLainnya());

                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child(context.getString(R.string.RumahSakit));
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Hospital> dataHospital = new ArrayList<>();
                        boolean included = false;
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            Hospital hospital1 = dataSnapshot1.getValue(Hospital.class);
                            boolean f = true;
                            for (String string : dataListString){
                                if (f && string.equals(hospital1.getNama())){
                                    included = true;
                                    f = false;
                                }
                            }
                            if (included){
                                dataHospital.add(hospital1);
                            }
                            included = false;
                        }
                        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                        RumahSakitLainnyaAdapter adapter = new RumahSakitLainnyaAdapter(dataHospital,context);
                        holder.recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class HospitalViewHolder extends RecyclerView.ViewHolder {
        TextView TVRSDesc, TVKontakIGD, TVKontakInformasi, TVKontakEmail;
        RecyclerView recyclerView;
        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            TVRSDesc = itemView.findViewById(R.id.TVRSDesc);
            TVKontakIGD = itemView.findViewById(R.id.TVKontakIGD);
            TVKontakInformasi = itemView.findViewById(R.id.TVKontakInformasi);
            TVKontakEmail = itemView.findViewById(R.id.TVKontakEmail);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }

    private String getSharedPreferenceLocal(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.Tampungan),MODE_PRIVATE);
        return sharedPreferences.getString(key,"Belum terisi");
    }
}
