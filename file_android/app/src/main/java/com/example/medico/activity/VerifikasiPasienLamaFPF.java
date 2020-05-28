package com.example.medico.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerifikasiPasienLamaFPF extends AppCompatActivity {
    String email_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_pasien_lama_fpf);

        Toolbar ToolbarVerifikasiPasienLama = findViewById(R.id.ToolbarVerifikasiPasienLama);
        setSupportActionBar(ToolbarVerifikasiPasienLama);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbar_text = findViewById(R.id.toolbar_text);
        toolbar_text.setText("Verifikasi Pasien");

        getUsernameLocal();
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.Tampungan)).child("users").child(email_key_new).child(getString(R.string.TelahMemilihTanggal));
        reference1.setValue("1");

        Intent before = getIntent();

        if (getIntent().getExtras() != null){
            final String NomorRekamMedis = before.getStringExtra(getString(R.string.NomorRekamMedis));
            setSharedPreferenceLocal(getString(R.string.NomorRekamMedis),NomorRekamMedis);
            final String JnsLayanan = before.getStringExtra(getString(R.string.JenisLayanan));
            setSharedPreferenceLocal(getString(R.string.JenisLayanan),JnsLayanan);
            String PenyediaLayanan = "";
            if (JnsLayanan.equals(getString(R.string.RumahSakit))){
                PenyediaLayanan = before.getStringExtra(getString(R.string.NamaRS));
                setSharedPreferenceLocal(getString(R.string.NamaRS),PenyediaLayanan);
            }
            else {
                PenyediaLayanan = before.getStringExtra(getString(R.string.NamaDokter));
                setSharedPreferenceLocal(getString(R.string.NamaDokter),PenyediaLayanan);
            }
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(getString(R.string.NomorRekamMedis)).child(JnsLayanan).child(StringFunction.DeleteDotFromString(PenyediaLayanan)).child(NomorRekamMedis);
            final String finalPenyediaLayanan = PenyediaLayanan;
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String nama = dataSnapshot.child("nama").getValue().toString();
                    String NIK = dataSnapshot.child("nik").getValue().toString();
                    String alamat = dataSnapshot.child("alamat").getValue().toString();

                    String tanggal_lahir = dataSnapshot.child("tanggal_lahir").getValue().toString();
                    String bulan_lahir = dataSnapshot.child("bulan_lahir").getValue().toString();
                    String tahun_lahir = dataSnapshot.child("tahun_lahir").getValue().toString();

                    DateFunction dateFunction = new DateFunction();

                    String TL = dateFunction.CovertToDateFixPatientVerification(tanggal_lahir,bulan_lahir,tahun_lahir);

                    RelativeLayout ETTTL = findViewById(R.id.ETTTL);

                    EditText ETNomorRekamMedis = findViewById(R.id.ETNomorRekamMedis);
                    final EditText ETNama = findViewById(R.id.ETNama);
                    final EditText ETNIK = findViewById(R.id.ETNIK);
                    final EditText ETAlamat = findViewById(R.id.ETAlamat);

                    final TextView hint = findViewById(R.id.hint);
                    final TextView tanggal = findViewById(R.id.tanggal);

                    ETNomorRekamMedis.setEnabled(false);
                    ETNomorRekamMedis.setText(NomorRekamMedis);

                    ETNama.setText(nama);
                    ETNIK.setText(NIK);
                    ETAlamat.setText(alamat);

                    hint.setVisibility(View.GONE);
                    tanggal.setVisibility(View.VISIBLE);
                    tanggal.setText(TL);

                    TextView TVPeringatanNomorRekamMedis = findViewById(R.id.TVPeringatanNomorRekamMedis);
                    final TextView TVPeringatanNama = findViewById(R.id.TVPeringatanNama);
                    final TextView TVPeringatanNIK = findViewById(R.id.TVPeringatanNIK);
                    TextView TVPeringatanTTL = findViewById(R.id.TVPeringatanTTL);
                    final TextView TVPeringatanAlamat = findViewById(R.id.TVPeringatanAlamat);

                    TVPeringatanNomorRekamMedis.setVisibility(View.GONE);
                    TVPeringatanNama.setVisibility(View.GONE);
                    TVPeringatanNIK.setVisibility(View.GONE);
                    TVPeringatanTTL.setVisibility(View.GONE);
                    TVPeringatanAlamat.setVisibility(View.GONE);

                    final Calendar newCalendar = Calendar.getInstance();

                    ETTTL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatePickerDialog datePickerDialog = new DatePickerDialog(VerifikasiPasienLamaFPF.this, new DatePickerDialog.OnDateSetListener() {

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

                    Button BtnGoToPilihJadwal = findViewById(R.id.BtnGoToPilihJadwal);
                    BtnGoToPilihJadwal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!ETNama.getText().toString().isEmpty()){
                                TVPeringatanNama.setVisibility(View.GONE);
                            }
                            if (!ETNIK.getText().toString().isEmpty()){
                                TVPeringatanNIK.setVisibility(View.GONE);
                            }
                            if (!ETAlamat.getText().toString().isEmpty()){
                                TVPeringatanAlamat.setVisibility(View.GONE);
                            }

                            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.Tampungan)).child("users").child(email_key_new).child("TelahMemilihTanggal");
                            reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!ETNama.getText().toString().isEmpty() && !ETNIK.getText().toString().isEmpty() && !ETAlamat.getText().toString().isEmpty() && dataSnapshot.getValue().toString().equals("1")){
                                        Intent goToPilihJadwal = new Intent(getApplicationContext(),PilihJadwalLamaFPF.class);

                                        goToPilihJadwal.putExtra(getString(R.string.NamaPasien),ETNama.getText().toString());
                                        goToPilihJadwal.putExtra(getString(R.string.TTLPasien),tanggal.getText().toString());
                                        goToPilihJadwal.putExtra(getString(R.string.AlamatPasien),ETAlamat.getText().toString());
                                        goToPilihJadwal.putExtra(getString(R.string.NIKPasien),ETNIK.getText().toString());

                                        goToPilihJadwal.putExtra(getString(R.string.JenisLayanan),JnsLayanan);
                                        if (JnsLayanan.equals(getString(R.string.RumahSakit))){
                                            goToPilihJadwal.putExtra(getString(R.string.NamaRS), finalPenyediaLayanan);
                                        }
                                        else{
                                            goToPilihJadwal.putExtra(getString(R.string.NamaDokter),finalPenyediaLayanan);
                                        }

                                        startActivity(goToPilihJadwal);
                                    }
                                    else{ //jika ada form yang blm terisi
                                        if (ETNama.getText().toString().isEmpty()){
                                            TVPeringatanNama.setVisibility(View.VISIBLE);
                                        }
                                        if (ETNIK.getText().toString().isEmpty()){
                                            TVPeringatanNIK.setVisibility(View.VISIBLE);
                                        }
                                        if (ETAlamat.getText().toString().isEmpty()){
                                            TVPeringatanAlamat.setVisibility(View.VISIBLE);
                                        }
                                        ///////////////////////////////////////////////////////
                                        if (ETNama.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Isi nama pasien",Toast.LENGTH_SHORT).show();
                                        }
                                        else if (ETNIK.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Isi NIK pasien",Toast.LENGTH_SHORT).show();
                                        }
                                        else if (ETAlamat.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Isi Alamat pasien",Toast.LENGTH_SHORT).show();
                                        }

                                        try {
                                            TimeUnit.SECONDS.sleep(2);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        if (ETNIK.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Isi NIK pasien",Toast.LENGTH_SHORT).show();
                                        }
                                        else if (ETAlamat.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Isi Alamat pasien",Toast.LENGTH_SHORT).show();
                                        }

                                        try {
                                            TimeUnit.SECONDS.sleep(2);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        if (ETAlamat.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Isi Alamat pasien",Toast.LENGTH_SHORT).show();
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
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //
                }
            });
        }
        else{ //Intent kosong
            final String NomorRekamMedis = getSharedPreferenceLocal(getString(R.string.NomorRekamMedis));
            final String JnsLayanan = getSharedPreferenceLocal(getString(R.string.JenisLayanan));
            String PenyediaLayanan = "";
            if (JnsLayanan.equals(getString(R.string.RumahSakit))){
                PenyediaLayanan = getSharedPreferenceLocal(getString(R.string.NamaRS));
            }
            else {
                PenyediaLayanan = getSharedPreferenceLocal(getString(R.string.NamaDokter));
            }
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(getString(R.string.NomorRekamMedis)).child(JnsLayanan).child(StringFunction.DeleteDotFromString(PenyediaLayanan)).child(NomorRekamMedis);
            final String finalPenyediaLayanan = PenyediaLayanan;
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String nama = dataSnapshot.child("nama").getValue().toString();
                    String NIK = dataSnapshot.child("nik").getValue().toString();
                    String alamat = dataSnapshot.child("alamat").getValue().toString();

                    String tanggal_lahir = dataSnapshot.child("tanggal_lahir").getValue().toString();
                    String bulan_lahir = dataSnapshot.child("bulan_lahir").getValue().toString();
                    String tahun_lahir = dataSnapshot.child("tahun_lahir").getValue().toString();

                    DateFunction dateFunction = new DateFunction();

                    String TL = dateFunction.CovertToDateFixPatientVerification(tanggal_lahir,bulan_lahir,tahun_lahir);

                    RelativeLayout ETTTL = findViewById(R.id.ETTTL);

                    EditText ETNomorRekamMedis = findViewById(R.id.ETNomorRekamMedis);
                    final EditText ETNama = findViewById(R.id.ETNama);
                    final EditText ETNIK = findViewById(R.id.ETNIK);
                    final EditText ETAlamat = findViewById(R.id.ETAlamat);

                    final TextView hint = findViewById(R.id.hint);
                    final TextView tanggal = findViewById(R.id.tanggal);

                    ETNomorRekamMedis.setEnabled(false);
                    ETNomorRekamMedis.setText(NomorRekamMedis);

                    ETNama.setText(nama);
                    ETNIK.setText(NIK);
                    ETAlamat.setText(alamat);

                    hint.setVisibility(View.GONE);
                    tanggal.setVisibility(View.VISIBLE);
                    tanggal.setText(TL);

                    TextView TVPeringatanNomorRekamMedis = findViewById(R.id.TVPeringatanNomorRekamMedis);
                    final TextView TVPeringatanNama = findViewById(R.id.TVPeringatanNama);
                    final TextView TVPeringatanNIK = findViewById(R.id.TVPeringatanNIK);
                    TextView TVPeringatanTTL = findViewById(R.id.TVPeringatanTTL);
                    final TextView TVPeringatanAlamat = findViewById(R.id.TVPeringatanAlamat);

                    TVPeringatanNomorRekamMedis.setVisibility(View.GONE);
                    TVPeringatanNama.setVisibility(View.GONE);
                    TVPeringatanNIK.setVisibility(View.GONE);
                    TVPeringatanTTL.setVisibility(View.GONE);
                    TVPeringatanAlamat.setVisibility(View.GONE);

                    final Calendar newCalendar = Calendar.getInstance();

                    ETTTL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatePickerDialog datePickerDialog = new DatePickerDialog(VerifikasiPasienLamaFPF.this, new DatePickerDialog.OnDateSetListener() {

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

                    Button BtnGoToPilihJadwal = findViewById(R.id.BtnGoToPilihJadwal);
                    BtnGoToPilihJadwal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!ETNama.getText().toString().isEmpty()){
                                TVPeringatanNama.setVisibility(View.GONE);
                            }
                            if (!ETNIK.getText().toString().isEmpty()){
                                TVPeringatanNIK.setVisibility(View.GONE);
                            }
                            if (!ETAlamat.getText().toString().isEmpty()){
                                TVPeringatanAlamat.setVisibility(View.GONE);
                            }

                            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.Tampungan)).child("users").child(email_key_new).child("TelahMemilihTanggal");
                            reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!ETNama.getText().toString().isEmpty() && !ETNIK.getText().toString().isEmpty() && !ETAlamat.getText().toString().isEmpty() && dataSnapshot.getValue().toString().equals("1")){
                                        Intent goToPilihJadwal = new Intent(getApplicationContext(),PilihJadwalLama.class);

                                        goToPilihJadwal.putExtra(getString(R.string.NamaPasien),ETNama.getText().toString());
                                        goToPilihJadwal.putExtra(getString(R.string.TTLPasien),tanggal.getText().toString());
                                        goToPilihJadwal.putExtra(getString(R.string.AlamatPasien),ETAlamat.getText().toString());
                                        goToPilihJadwal.putExtra(getString(R.string.NIKPasien),ETNIK.getText().toString());

                                        goToPilihJadwal.putExtra(getString(R.string.JenisLayanan),JnsLayanan);
                                        if (JnsLayanan.equals(getString(R.string.RumahSakit))){
                                            goToPilihJadwal.putExtra(getString(R.string.NamaRS), finalPenyediaLayanan);
                                        }
                                        else{
                                            goToPilihJadwal.putExtra(getString(R.string.NamaDokter),finalPenyediaLayanan);
                                        }

                                        startActivity(goToPilihJadwal);
                                    }
                                    else{ //jika ada form yang blm terisi
                                        if (ETNama.getText().toString().isEmpty()){
                                            TVPeringatanNama.setVisibility(View.VISIBLE);
                                        }
                                        if (ETNIK.getText().toString().isEmpty()){
                                            TVPeringatanNIK.setVisibility(View.VISIBLE);
                                        }
                                        if (ETAlamat.getText().toString().isEmpty()){
                                            TVPeringatanAlamat.setVisibility(View.VISIBLE);
                                        }
                                        ///////////////////////////////////////////////////////
                                        if (ETNama.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Isi nama pasien",Toast.LENGTH_SHORT).show();
                                        }
                                        else if (ETNIK.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Isi NIK pasien",Toast.LENGTH_SHORT).show();
                                        }
                                        else if (ETAlamat.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Isi Alamat pasien",Toast.LENGTH_SHORT).show();
                                        }

                                        try {
                                            TimeUnit.SECONDS.sleep(2);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        if (ETNIK.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Isi NIK pasien",Toast.LENGTH_SHORT).show();
                                        }
                                        else if (ETAlamat.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Isi Alamat pasien",Toast.LENGTH_SHORT).show();
                                        }

                                        try {
                                            TimeUnit.SECONDS.sleep(2);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        if (ETAlamat.getText().toString().isEmpty()){
                                            Toast.makeText(getApplicationContext(),"Isi Alamat pasien",Toast.LENGTH_SHORT).show();
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
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        String email_key = "";
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
