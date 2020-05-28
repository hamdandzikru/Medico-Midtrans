package com.example.medico.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medico.FungsiFungsi.StringFunction;
import com.example.medico.R;
import com.example.medico.model.LayananRS;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FragmentLayananAdapter extends RecyclerView.Adapter<FragmentLayananAdapter.ItemViewHolder> {
    private Context context;
    private String NamaRSGlobal;
    private android.os.Handler h = new Handler();
    Runnable runnable;

    String email_key = "";
    String email_key_new = "";

    public FragmentLayananAdapter(Context context1, String NamaRSGlobal) {
        context = context1;
        NamaRSGlobal = getSharedPreferenceLocal(context1.getString(R.string.NamaRS));
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_layanan_rs,viewGroup,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int i) {
        //String NamaRS = getSharedPreferenceLocal(context.getString(R.string.NamaRS));
        String NamaRS = "RS Advent Bandung";
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("LayananRS").child(StringFunction.DeleteDotFromString(NamaRS));
        final String final_nama_rs = "RS Advent Bandung";
        Log.d("TAGTEMP","Nama RS: "+final_nama_rs);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<LayananRS> dataLayanan = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    LayananRS layananRS = dataSnapshot1.getValue(LayananRS.class);
                    dataLayanan.add(layananRS);
                }
                final ArrayList<String> dataKelompok = new ArrayList<>();
                for (LayananRS layananRS : dataLayanan){
                    String kelompok = layananRS.getKelompok();
                    if (dataLayanan.size() == 0){
                        dataKelompok.add(kelompok);
                    }
                    else{
                        int i = 0;
                        boolean lanjut = true;
                        while (lanjut && i <= dataKelompok.size()-1){
                            if (kelompok.equals(dataKelompok.get(i))){
                                lanjut = false;
                            }
                            i++;
                        }
                        if (lanjut) {
                            dataKelompok.add(kelompok);
                        }
                    }
                    KelompokLayananRSAdapter adapterKelompok = new KelompokLayananRSAdapter(dataKelompok,context);
                    holder.recyclerViewKelompok.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
                    holder.recyclerViewKelompok.setAdapter(adapterKelompok);

                    getUsernameLocal();
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child(context.getString(R.string.LayananRS)).child(final_nama_rs);
                    reference1.keepSynced(true);
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<LayananRS> dataList = new ArrayList<>();
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                LayananRS layananRS1 = dataSnapshot1.getValue(LayananRS.class);
                                dataList.add(layananRS1);
                            }
                            LayananRsAdapter layananRsAdapter = new LayananRsAdapter(dataList,context);
                            holder.recyclerViewLayanan.setLayoutManager(new LinearLayoutManager(context));
                            holder.recyclerViewLayanan.setAdapter(layananRsAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //
                        }
                    });

                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getUsernameLocal();
                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child(context.getString(R.string.Tampungan)).child("users").child(email_key_new).child(context.getString(R.string.TelahMemilihKelompokLayanan));
                            reference1.keepSynced(true);
                            reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.getValue().toString().equals("null")){
                                        String kelompokLocal = dataSnapshot.getValue().toString();
                                        setSharedPreferenceLocal("KelompokTemp",kelompokLocal);
                                        ArrayList<LayananRS> dataLayananLokal = new ArrayList<>();
                                        for (LayananRS layananRS1 : dataLayanan){
                                            if (layananRS1.getKelompok().equals(kelompokLocal)){
                                                dataLayananLokal.add(layananRS1);
                                            }
                                        }
                                        LayananRsAdapter layananRsAdapter = new LayananRsAdapter(dataLayananLokal,context);
                                        holder.recyclerViewLayanan.setLayoutManager(new LinearLayoutManager(context));
                                        holder.recyclerViewLayanan.setAdapter(layananRsAdapter);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    //
                                }
                            });

                            runnable=this;
                            h.postDelayed(runnable, 1);
                        }
                    },1);

                }
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

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerViewKelompok,recyclerViewLayanan;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewKelompok = itemView.findViewById(R.id.recyclerViewKelompok);
            recyclerViewLayanan = itemView.findViewById(R.id.recyclerViewLayanan);
        }
    }

    private void getUsernameLocal(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("id", MODE_PRIVATE);
        email_key_new = sharedPreferences.getString(email_key,"");
    }

    private String getSharedPreferenceLocal(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.Tampungan),MODE_PRIVATE);
        return sharedPreferences.getString(key,"Belum terisi");
    }

    private void setSharedPreferenceLocal(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.Tampungan),MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
}
