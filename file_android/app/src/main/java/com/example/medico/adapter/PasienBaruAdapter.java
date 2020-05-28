package com.example.medico.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medico.FungsiFungsi.DateFunction;
import com.example.medico.activity.PilihJadwal;
import com.example.medico.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

public class PasienBaruAdapter extends RecyclerView.Adapter<PasienBaruAdapter.PasienBaruViewHolder> {
    private Context context;
    private String nama_layanan;
    private String jns_layanan;
    private Boolean isTypeTTL;

    private String email_key_new = "";

    public PasienBaruAdapter(Context context) {
        this.context = context;
        isTypeTTL = false;
    }

    public void setNama_layanan(String nama_layanan) {
        this.nama_layanan = nama_layanan;
    }

    public void setJns_layanan(String jns_layanan) {
        this.jns_layanan = jns_layanan;
    }

    @NonNull
    @Override
    public PasienBaruViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.verifikasi_pasien_baru_item,parent,false);
        return new PasienBaruViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PasienBaruViewHolder holder, int i) {
        holder.TVPeringatanNama.setVisibility(View.GONE);
        holder.TVPeringatanNIK.setVisibility(View.GONE);
        holder.TVPeringatanTTL.setVisibility(View.GONE);
        holder.TVPeringatanAlamat.setVisibility(View.GONE);

        holder.ETTTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        getUsernameLocal();
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        holder.tanggal.setText(dateFormatter.format(newDate.getTime()));
                        holder.hint.setVisibility(View.GONE);
                        holder.tanggal.setVisibility(View.VISIBLE);
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(context.getString(R.string.Tampungan)).child("users").child(email_key_new).child("Tanggal");
                        reference.setValue(dateFormatter.format(newDate.getTime()));
                        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child(context.getString(R.string.Tampungan)).child("users").child(email_key_new).child("TelahMemilihTangggal");
                        reference2.setValue("1");
                    }
                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        holder.BtnGoToPilihJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.ETNama.getText().toString().isEmpty()){
                    holder.TVPeringatanNama.setVisibility(View.GONE);
                }

                if (!holder.ETNIK.getText().toString().isEmpty()){
                    holder.TVPeringatanNIK.setVisibility(View.GONE);
                }

                if (!holder.ETAlamat.getText().toString().isEmpty()){
                    holder.TVPeringatanAlamat.setVisibility(View.GONE);
                }

                if (isTypeTTL){
                    holder.TVPeringatanTTL.setVisibility(View.GONE);
                }

                final Intent i = new Intent(context, PilihJadwal.class);
                i.putExtra("JenisLayanan",jns_layanan);
                if (jns_layanan.equals("RumahSakit")){
                    i.putExtra("NamaRS",nama_layanan);
                }
                else{
                    i.putExtra("NamaDokter",nama_layanan);
                }
                getUsernameLocal();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(context.getString(R.string.Tampungan)).child("users").child(email_key_new);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("TelahMemilihTangggal").getValue().toString().equals("1")){
                            isTypeTTL = true;
                            String stringTanggal = dataSnapshot.child("Tanggal").getValue().toString();
                            int tanggal = 0;
                            String bulan = "";
                            int tahun;
                            DateFunction dateFunction = new DateFunction();
                            tanggal = dateFunction.getTanggalFromDate(stringTanggal);
                            bulan = dateFunction.getBulanFromDate(stringTanggal);
                            tahun = dateFunction.getTahunFromDate(stringTanggal);
                            if (!holder.ETNama.getText().toString().isEmpty() && !holder.ETAlamat.getText().toString().isEmpty() && !holder.ETNIK.getText().toString().isEmpty()){
                                i.putExtra(context.getString(R.string.NamaPasien),holder.ETNama.getText().toString());
                                String ttl = tanggal + bulan + tahun;
                                i.putExtra(context.getString(R.string.TTLPasien),ttl);
                                i.putExtra(context.getString(R.string.AlamatPasien),holder.ETAlamat.getText().toString());
                                i.putExtra(context.getString(R.string.NIKPasien),holder.ETNIK.getText().toString());
                                context.startActivity(i);
                            }
                            else {
                                if (holder.ETNama.getText().toString().isEmpty()){
                                    holder.TVPeringatanNama.setVisibility(View.VISIBLE);
                                }

                                if (holder.ETNIK.getText().toString().isEmpty()){
                                    holder.TVPeringatanNIK.setVisibility(View.VISIBLE);
                                }

                                if (holder.TVPeringatanAlamat.getText().toString().isEmpty()){
                                    holder.TVPeringatanAlamat.setVisibility(View.VISIBLE);
                                }

                                if (holder.ETNama.getText().toString().isEmpty()){
                                    Toast.makeText(context,"Isi nama pasien",Toast.LENGTH_SHORT).show();
                                }

                                try {
                                    TimeUnit.SECONDS.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                if (holder.ETNIK.getText().toString().isEmpty()){
                                    Toast.makeText(context,"Isi NIK pasien",Toast.LENGTH_SHORT).show();
                                }

                                try {
                                    TimeUnit.SECONDS.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                if (holder.ETAlamat.getText().toString().isEmpty()){
                                    Toast.makeText(context,"Isi alamat pasien",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else{
                            if (holder.ETNama.getText().toString().isEmpty()){
                                holder.TVPeringatanNama.setVisibility(View.VISIBLE);
                            }

                            if (holder.ETNIK.getText().toString().isEmpty()){
                                holder.TVPeringatanNIK.setVisibility(View.VISIBLE);
                            }

                            if (holder.TVPeringatanAlamat.getText().toString().isEmpty()){
                                holder.TVPeringatanAlamat.setVisibility(View.VISIBLE);
                            }

                            holder.TVPeringatanTTL.setVisibility(View.VISIBLE);

                            if (holder.ETNama.getText().toString().isEmpty()){
                                Toast.makeText(context,"Isi Nama pasien",Toast.LENGTH_SHORT).show();
                            }

                            try {
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (holder.ETNIK.getText().toString().isEmpty()){
                                Toast.makeText(context,"Isi NIK pasien",Toast.LENGTH_SHORT).show();
                            }

                            try {
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(context,"Isi tanggal lahir pasien",Toast.LENGTH_SHORT).show();

                            try {
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (holder.ETAlamat.getText().toString().isEmpty()){
                                Toast.makeText(context,"Isi alamat pasien",Toast.LENGTH_SHORT).show();
                            }
                        }
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
        return 1;
    }

    public class PasienBaruViewHolder extends RecyclerView.ViewHolder {
        EditText ETNama, ETNIK, ETAlamat;
        RelativeLayout ETTTL;
        TextView TVPeringatanNama, TVPeringatanNIK, TVPeringatanTTL, TVPeringatanAlamat;
        Button BtnGoToPilihJadwal;
        TextView hint, tanggal;

        public PasienBaruViewHolder(@NonNull View itemView) {
            super(itemView);
            ETNama = itemView.findViewById(R.id.ETNama);
            ETNIK = itemView.findViewById(R.id.ETNIK);
            ETTTL = itemView.findViewById(R.id.ETTTL);

            hint = itemView.findViewById(R.id.hint);
            tanggal = itemView.findViewById(R.id.tanggal);


            ETAlamat = itemView.findViewById(R.id.ETAlamat);
            BtnGoToPilihJadwal = itemView.findViewById(R.id.BtnGoToPilihJadwal);

            TVPeringatanNama = itemView.findViewById(R.id.TVPeringatanNama);
            TVPeringatanNIK = itemView.findViewById(R.id.TVPeringatanNIK);
            TVPeringatanTTL = itemView.findViewById(R.id.TVPeringatanTTL);
            TVPeringatanAlamat = itemView.findViewById(R.id.TVPeringatanAlamat);
        }
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("id", MODE_PRIVATE);
        String email_key = "";
        email_key_new = sharedPreferences.getString(email_key,"");
    }
}
