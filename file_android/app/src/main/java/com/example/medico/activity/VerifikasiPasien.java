package com.example.medico.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medico.FungsiFungsi.DateFunction;
import com.example.medico.FungsiFungsi.StringFunction;
import com.example.medico.R;
import com.example.medico.model.NomorRekamMedis;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerifikasiPasien extends AppCompatActivity {
    RelativeLayout RLJenisPasien;
    TextView TVJnsPasien;
    String email_key = "";
    String email_key_new = "";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_pasien);

        getUsernameLocal();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(getString(R.string.Tampungan)).child("users").child(email_key_new).child("TelahMemilihTangggal");
        reference.setValue("0");

        final Intent before = getIntent();

        //AWAL BAGIAN PASIEN LAMA
        final TextView[] TVNomorRekamMedis = {findViewById(R.id.TVNomorRekamMedis)};
        final TextView[] TVPeringatanPasienLama = {findViewById(R.id.TVPeringatanPasienLama)};

        final EditText[] ETNomorRekamMedis = {findViewById(R.id.ETNomorRekamMedis)};

        final Button BtnGoToVerifikasiPasienLama = findViewById(R.id.BtnGoToVerifikasiPasienLama);
        //AKHIR BAGIAN PASIEN LAMA

        //AWAL BAGIAN PASIEN BARU
        final TextView TVNama = findViewById(R.id.TVNama);
        final TextView TVNIK = findViewById(R.id.TVNIK);
        final TextView TVTL = findViewById(R.id.TVTL);
        final TextView TVAlamat = findViewById(R.id.TVAlamat);

        final RelativeLayout ETTTL = findViewById(R.id.ETTTL);

        final TextView hint = findViewById(R.id.hint);
        final TextView tanggal = findViewById(R.id.tanggal);


        final TextView TVPeringatanNama = findViewById(R.id.TVPeringatanNama);
        final TextView TVPeringatanNIK = findViewById(R.id.TVPeringatanNIK);
        final TextView TVPeringatanTTL = findViewById(R.id.TVPeringatanTTL);
        final TextView TVPeringatanAlamat = findViewById(R.id.TVPeringatanAlamat);

        final EditText ETNamaPasienBaru = findViewById(R.id.ETNamaPasienBaru);
        final EditText ETNIK = findViewById(R.id.ETNIK);
        final EditText ETAlamat = findViewById(R.id.ETAlamat);

        final Button BtnGoToPilihJadwal = findViewById(R.id.BtnGoToPilihJadwal);
        final View view = findViewById(R.id.view);
        //AKHIR BAGIAN PASIEN BARU

        final Calendar newCalendar = Calendar.getInstance();
        String jns_layanan = "";
        String penyedia_layanan = "";

        if (getIntent().getExtras() != null){
            jns_layanan = before.getStringExtra(getString(R.string.JenisLayanan));
            getUsernameLocal();
            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.Tampungan)).child(email_key_new).child(getString(R.string.JenisLayanan));
            reference1.setValue(before.getStringExtra(getString(R.string.JenisLayanan)));
            setSharedPreferenceLocal(getString(R.string.JenisLayanan),jns_layanan);
            Log.d("TAG","Jenis Layanan  Lokal : " + getSharedPreferenceLocal(getString(R.string.JenisLayanan)));
            if (jns_layanan.equals(getString(R.string.RumahSakit))){ // jika jenis layanannya adalah rumah sakit
                setSharedPreferenceLocal(getString(R.string.NamaRS),before.getStringExtra(getString(R.string.NamaRS)));
                penyedia_layanan = getSharedPreferenceLocal(getString(R.string.NamaRS));
                Log.d("TAG","Penyedia Layanan  Lokal : " + getSharedPreferenceLocal(getString(R.string.NamaRS)));
            }
            else{ //jika jenis layanannya adalah dokter
                setSharedPreferenceLocal(getString(R.string.NamaDokter),before.getStringExtra(getString(R.string.NamaDokter)));
                penyedia_layanan = getSharedPreferenceLocal(getString(R.string.NamaDokter));
                Log.d("TAG","Penyedia Layanan  Lokal : " + getSharedPreferenceLocal(getString(R.string.NamaDokter)));
            }
        }
        else{ //jika intent tidak null
            jns_layanan = getSharedPreferenceLocal(getString(R.string.JenisLayanan));
            if (jns_layanan.equals(getString(R.string.RumahSakit))){ // jika jenis layanannya adalah rumah sakit
                penyedia_layanan = getSharedPreferenceLocal(getString(R.string.NamaRS));
            }
            else{ //jika jenis layanannya adalah dokter
                penyedia_layanan = getSharedPreferenceLocal(getString(R.string.NamaDokter));

            }
        }

        final String final_jns_layanan = jns_layanan;

        final String final_penyedia_layanan = penyedia_layanan;
        Log.d("TAG","Penyedia Layanan :" + penyedia_layanan);
        Log.d("TAG","final_penyedia_layanan" + final_penyedia_layanan);

        androidx.appcompat.widget.Toolbar ToolbarNavigation = (Toolbar) findViewById(R.id.ToolbarVerifikasiPasien);

        setSupportActionBar(ToolbarNavigation);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarText = (TextView) findViewById(R.id.toolbar_text);
        toolbarText.setText("Verifikasi Pasien");

        RLJenisPasien = findViewById(R.id.RLJenisPasien);
        TVJnsPasien = findViewById(R.id.TVJnsPasien);
        RLJenisPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(VerifikasiPasien.this);
                dialog.setContentView(R.layout.pop_up_patient_type);
                CardView CVPasienLama = dialog.findViewById(R.id.CVPasienLama);
                CardView CVPasienBaru = dialog.findViewById(R.id.CVPasienBaru);
                getUsernameLocal();
                CVPasienLama.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TVNomorRekamMedis[0].setVisibility(View.VISIBLE);
                        TVPeringatanPasienLama[0].setVisibility(View.GONE);
                        ETNomorRekamMedis[0].setVisibility(View.VISIBLE);
                        BtnGoToVerifikasiPasienLama.setVisibility(View.VISIBLE);

                        TVNama.setVisibility(View.GONE);
                        TVNIK.setVisibility(View.GONE);
                        TVTL.setVisibility(View.GONE);
                        TVAlamat.setVisibility(View.GONE);
                        ETTTL.setVisibility(View.GONE);
                        hint.setVisibility(View.GONE);
                        tanggal.setVisibility(View.GONE);
                        TVPeringatanNama.setVisibility(View.GONE);
                        TVPeringatanNIK.setVisibility(View.GONE);
                        TVPeringatanTTL.setVisibility(View.GONE);
                        TVPeringatanAlamat.setVisibility(View.GONE);
                        ETNamaPasienBaru.setVisibility(View.GONE);
                        ETNIK.setVisibility(View.GONE);
                        ETAlamat.setVisibility(View.GONE);
                        BtnGoToPilihJadwal.setVisibility(View.GONE);
                        view.setVisibility(View.GONE);

                        TVJnsPasien.setText(getString(R.string.PasienLama));
                        if (getIntent().getExtras() != null){
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tampungan").child("users").child(email_key_new).child("JenisLayanan");
                            reference.setValue(before.getStringExtra("JenisLayanan"));
                            if (before.getStringExtra("JenisLayanan").equals(getString(R.string.RumahSakit))){
                                reference = FirebaseDatabase.getInstance().getReference().child("Tampungan").child("users").child(email_key_new).child("NamaRS");
                                reference.setValue(before.getStringExtra("NamaRS"));
                            }
                            else{
                                reference = FirebaseDatabase.getInstance().getReference().child("Tampungan").child("users").child(email_key_new).child("NamaDokter");
                                reference.setValue(before.getStringExtra("NamaDokter"));
                            }
                            BtnGoToVerifikasiPasienLama.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!ETNomorRekamMedis[0].getText().toString().isEmpty()){
                                        TVPeringatanPasienLama[0].setVisibility(View.GONE);
                                    }

                                    BtnGoToVerifikasiPasienLama.setEnabled(false);
                                    if (!ETNomorRekamMedis[0].getText().toString().isEmpty()){ //Edit Text Nomor Rekam Medis terisi
                                        DatabaseReference reference1;
                                        reference1 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.NomorRekamMedis)).child(getString(R.string.Dokter)).child(StringFunction.DeleteDotFromString(final_penyedia_layanan));

                                        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                ArrayList<NomorRekamMedis> dataList = new ArrayList<>();
                                                boolean lanjut = false;
                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                                    NomorRekamMedis nomorRekamMedis = dataSnapshot1.getValue(NomorRekamMedis.class);
                                                    dataList.add(nomorRekamMedis);
                                                    Log.d("TAG","Nomor Rekam Medis : "+nomorRekamMedis.getNomor());
                                                    if (nomorRekamMedis.getNomor().equals(ETNomorRekamMedis[0].getText().toString())){
                                                        lanjut = true;
                                                        Log.d("TAG","break");
                                                        break;
                                                    }
                                                }
                                                int index = dataList.size() - 1;
                                                Log.d("TAG","index =" + index);
                                                Log.d("TAG","size =" + dataList.size());
                                                if (lanjut){ //Nomor Rekam Medis ditemukan di database
                                                    Intent goToVerifikasiPasienLama = new Intent(getApplicationContext(),VerifikasiPasienLama.class);
                                                    goToVerifikasiPasienLama.putExtra(getString(R.string.NomorRekamMedis),dataList.get(index).getNomor());
                                                    goToVerifikasiPasienLama.putExtra(getString(R.string.JenisLayanan),final_jns_layanan);

                                                    if (final_jns_layanan.equals(getString(R.string.RumahSakit))){
                                                        goToVerifikasiPasienLama.putExtra(getString(R.string.NamaRS),final_penyedia_layanan);
                                                    }
                                                    else{
                                                        goToVerifikasiPasienLama.putExtra(getString(R.string.NamaDokter),final_penyedia_layanan);
                                                    }
                                                    startActivity(goToVerifikasiPasienLama);
                                                }
                                                else{ //Nomor Rekam Medis Tidak ditemukan di database
                                                    TVPeringatanPasienLama[0].setVisibility(View.VISIBLE);
                                                    Toast.makeText(getApplicationContext(),"Nomor Rekam Medis tidak ditemukan",Toast.LENGTH_LONG).show();
                                                    BtnGoToVerifikasiPasienLama.setEnabled(true);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    else{ //Edit Text Nomor Rekam Medis belum diisi
                                        TVPeringatanPasienLama[0].setVisibility(View.VISIBLE);
                                        Toast.makeText(getApplicationContext(),"Isi Nomor Rekam Medis pasien",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else{ //Intent kosong
                            BtnGoToVerifikasiPasienLama.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!ETNomorRekamMedis[0].getText().toString().isEmpty()){
                                        TVPeringatanPasienLama[0].setVisibility(View.GONE);
                                    }

                                    BtnGoToVerifikasiPasienLama.setEnabled(false);
                                    if (!ETNomorRekamMedis[0].getText().toString().isEmpty()){ //Edit Text Nomor Rekam Medis terisi
                                        DatabaseReference reference1;
                                        reference1 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.NomorRekamMedis)).child(getString(R.string.Dokter)).child(StringFunction.DeleteDotFromString(final_penyedia_layanan));

                                        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                ArrayList<NomorRekamMedis> dataList = new ArrayList<>();
                                                boolean lanjut = false;
                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                                    NomorRekamMedis nomorRekamMedis = dataSnapshot1.getValue(NomorRekamMedis.class);
                                                    dataList.add(nomorRekamMedis);
                                                    Log.d("TAG","Nomor Rekam Medis : "+nomorRekamMedis.getNomor());
                                                    if (nomorRekamMedis.getNomor().equals(ETNomorRekamMedis[0].getText().toString())){
                                                        lanjut = true;
                                                        Log.d("TAG","break");
                                                        break;
                                                    }
                                                }
                                                int index = dataList.size() - 1;
                                                Log.d("TAG","index =" + index);
                                                Log.d("TAG","size =" + dataList.size());
                                                if (lanjut){ //Nomor Rekam Medis ditemukan di database
                                                    Intent goToVerifikasiPasienLama = new Intent(getApplicationContext(),VerifikasiPasienLama.class);
                                                    goToVerifikasiPasienLama.putExtra(getString(R.string.NomorRekamMedis),dataList.get(index).getNomor());
                                                    goToVerifikasiPasienLama.putExtra(getString(R.string.JenisLayanan),final_jns_layanan);

                                                    if (final_jns_layanan.equals(getString(R.string.RumahSakit))){
                                                        goToVerifikasiPasienLama.putExtra(getString(R.string.NamaRS),final_penyedia_layanan);
                                                    }
                                                    else{
                                                        goToVerifikasiPasienLama.putExtra(getString(R.string.NamaDokter),final_penyedia_layanan);
                                                    }
                                                    startActivity(goToVerifikasiPasienLama);
                                                }
                                                else{ //Nomor Rekam Medis Tidak ditemukan di database
                                                    TVPeringatanPasienLama[0].setVisibility(View.VISIBLE);
                                                    Toast.makeText(getApplicationContext(),"Nomor Rekam Medis tidak ditemukan",Toast.LENGTH_LONG).show();
                                                    BtnGoToVerifikasiPasienLama.setEnabled(true);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    else{ //Edit Text Nomor Rekam Medis belum diisi
                                        TVPeringatanPasienLama[0].setVisibility(View.VISIBLE);
                                        Toast.makeText(getApplicationContext(),"Isi Nomor Rekam Medis pasien",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }

                        dialog.dismiss();
                    }
                });

                CVPasienBaru.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.Tampungan)).child("users").child(email_key_new).child("TelahMemilihTanggal");
                        reference1.setValue("0");

                        TVJnsPasien.setText(getString(R.string.PasienBaru));
                        TVNomorRekamMedis[0].setVisibility(View.GONE);
                        TVPeringatanPasienLama[0].setVisibility(View.GONE);
                        ETNomorRekamMedis[0].setVisibility(View.GONE);
                        BtnGoToVerifikasiPasienLama.setVisibility(View.GONE);

                        TVNama.setVisibility(View.VISIBLE);
                        TVNIK.setVisibility(View.VISIBLE);
                        TVTL.setVisibility(View.VISIBLE);
                        TVAlamat.setVisibility(View.VISIBLE);
                        ETTTL.setVisibility(View.VISIBLE);
                        hint.setVisibility(View.VISIBLE);
                        tanggal.setVisibility(View.GONE);
                        TVPeringatanNama.setVisibility(View.GONE);
                        TVPeringatanNIK.setVisibility(View.GONE);
                        TVPeringatanTTL.setVisibility(View.GONE);
                        TVPeringatanAlamat.setVisibility(View.GONE);
                        ETNamaPasienBaru.setVisibility(View.VISIBLE);
                        ETNIK.setVisibility(View.VISIBLE);
                        ETAlamat.setVisibility(View.VISIBLE);
                        BtnGoToPilihJadwal.setVisibility(View.VISIBLE);
                        view.setVisibility(View.VISIBLE);

                        ETTTL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatePickerDialog datePickerDialog = new DatePickerDialog(VerifikasiPasien.this, new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                        Calendar newDate = Calendar.getInstance();
                                        newDate.set(year, monthOfYear, dayOfMonth);
                                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

                                        String tanggalTemp = dateFormatter.format(newDate.getTime());

                                        getUsernameLocal();

                                        tanggal.setText(tanggalTemp);
                                        tanggal.setVisibility(View.VISIBLE);
                                        hint.setVisibility(View.GONE);
                                        getUsernameLocal();
                                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.Tampungan)).child("users").child(email_key_new).child("TelahMemilihTanggal");
                                        reference1.setValue("1");
                                        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.Tampungan)).child("users").child(email_key_new).child(getString(R.string.TanggalLahirPasien));
                                        reference2.setValue(tanggalTemp);

                                    }

                                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                                datePickerDialog.show();
                            }
                        });
                        BtnGoToPilihJadwal.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                if (!ETNamaPasienBaru.getText().toString().isEmpty()){
                                    TVPeringatanNama.setVisibility(View.GONE);
                                }

                                if (!ETNIK.getText().toString().isEmpty()){
                                    TVPeringatanNIK.setVisibility(View.GONE);
                                }
                                getUsernameLocal();
                                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.Tampungan)).child("users").child(email_key_new).child("TelahMemilihTanggal");
                                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        boolean TMT = dataSnapshot.getValue().toString().equals("1");
                                        if (TMT) {
                                            TVPeringatanTTL.setVisibility(View.GONE);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                if (!ETAlamat.getText().toString().isEmpty()){
                                    TVPeringatanAlamat.setVisibility(View.GONE);
                                }

                                final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.Tampungan)).child("users").child(email_key_new).child("TelahMemilihTanggal");
                                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        boolean lanjut = dataSnapshot.getValue().toString().equals("1");
                                        if (!ETNamaPasienBaru.getText().toString().isEmpty() && !ETNIK.getText().toString().isEmpty() && !ETAlamat.getText().toString().isEmpty() && lanjut){
                                            final Intent goToPilihJadwal = new Intent(getApplicationContext(),PilihJadwal.class);
                                            goToPilihJadwal.putExtra("NamaPasien",ETNamaPasienBaru.getText().toString());
                                            getUsernameLocal();
                                            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.Tampungan)).child("users").child(email_key_new).child(getString(R.string.TanggalLahirPasien));
                                            reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String stringTanggal = dataSnapshot.getValue().toString();
                                                    DateFunction dateFunction = new DateFunction();
                                                    int tanggal;
                                                    String bulan;
                                                    int tahun;
                                                    tanggal = dateFunction.getTanggalFromDate(stringTanggal);
                                                    bulan = dateFunction.getBulanFromDate(stringTanggal);
                                                    tahun = dateFunction.getTahunFromDate(stringTanggal);
                                                    String ttl = tanggal+bulan+tahun;

                                                    goToPilihJadwal.putExtra(getString(R.string.NamaPasien),ETNamaPasienBaru.getText().toString());
                                                    goToPilihJadwal.putExtra(getString(R.string.TTLPasien),ttl);
                                                    goToPilihJadwal.putExtra(getString(R.string.AlamatPasien),ETAlamat.getText().toString());
                                                    goToPilihJadwal.putExtra(getString(R.string.NIKPasien),ETNIK.getText().toString());

                                                    goToPilihJadwal.putExtra(getString(R.string.JenisLayanan),final_jns_layanan);
                                                    if (final_jns_layanan.equals(getString(R.string.RumahSakit))){
                                                        goToPilihJadwal.putExtra(getString(R.string.NamaRS),final_penyedia_layanan);
                                                    }
                                                    else{
                                                        goToPilihJadwal.putExtra(getString(R.string.NamaDokter),final_penyedia_layanan);
                                                    }

                                                    startActivity(goToPilihJadwal);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                        else{
                                            if (ETNamaPasienBaru.getText().toString().isEmpty()){
                                                TVPeringatanNama.setVisibility(View.VISIBLE);
                                            }

                                            if (ETNIK.getText().toString().isEmpty()) {
                                                TVPeringatanNIK.setVisibility(View.VISIBLE);
                                            }

                                            if (!lanjut){
                                                TVPeringatanTTL.setVisibility(View.VISIBLE);
                                            }

                                            if (ETAlamat.getText().toString().isEmpty()){
                                                TVPeringatanAlamat.setVisibility(View.VISIBLE);
                                            }
                                            ////////////////////////////////////////////////
                                            if (ETNamaPasienBaru.getText().toString().isEmpty()){
                                                Toast.makeText(getApplicationContext(),"Isi nama pasien",Toast.LENGTH_SHORT).show();
                                            }

                                            try {
                                                TimeUnit.SECONDS.sleep(2);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                            if (ETNIK.getText().toString().isEmpty()) {
                                                Toast.makeText(getApplicationContext(),"Isi NIK pasien",Toast.LENGTH_SHORT).show();
                                            }

                                            try {
                                                TimeUnit.SECONDS.sleep(2);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                            if (!lanjut){
                                                Toast.makeText(getApplicationContext(),"Isi tanggal lahir pasien",Toast.LENGTH_SHORT).show();;
                                            }

                                            try {
                                                TimeUnit.SECONDS.sleep(2);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                            if (ETAlamat.getText().toString().isEmpty()){
                                                Toast.makeText(getApplicationContext(),"Isi alamat pasien",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {

        super.onSaveInstanceState(outState);
        //outState.putString(STATE_RESULT, tvResult.getText().toString());
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        email_key_new = sharedPreferences.getString(email_key,"");
    }



    private void setSharedPreferenceLocal(String key, String value){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.Tampungan),MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    private String getSharedPreferenceLocal(String key){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.Tampungan),MODE_PRIVATE);
        return sharedPreferences.getString(key,"Belum terisi");
    }
}
