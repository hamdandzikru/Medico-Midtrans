package com.example.medico.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.example.medico.FungsiFungsi.StringFunction;
import com.example.medico.R;
import com.example.medico.adapter.ViewPagerAdapter;
import com.example.medico.fragment.DokterRSFragment;
import com.example.medico.fragment.LayananFragment;
import com.example.medico.fragment.ProfilRSFragment;
import com.example.medico.model.Hospital;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfilRS extends AppCompatActivity {
    String email_key = "";
    String email_key_new = "";

    String NamaRumahSakit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_rs);

        getUsernameLocal();
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.Tampungan)).child("users").child(email_key_new).child(getString(R.string.TelahMemilihKelompokLayanan));
        reference1.setValue("null");

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.Tampungan)).child("users").child(email_key_new);
        reference2.child(getString(R.string.TelahMemilihKelompokLayanan)+"for dokter fragment").setValue("null");
        reference2.child("TelahMemilihHariUntukDokterRSFragment").setValue("null");

        Intent FromSearchRS = getIntent();

        String NamaRs = "";

        if (getIntent().getExtras() != null){
            NamaRs = FromSearchRS.getStringExtra(getString(R.string.NamaRS));
            setSharedPreferenceLocal(getString(R.string.NamaRS),NamaRs);
            getUsernameLocal();
        }
        else{
            NamaRs = NamaRumahSakit;
            setSharedPreferenceLocal(getString(R.string.NamaRS),NamaRs);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final TextView ToolbarTV = findViewById(R.id.ToolbarTV);
        ToolbarTV.setText(" ");
        toolbar.getNavigationIcon().setColorFilter(getColor(R.color.black), PorterDuff.Mode.SRC_IN);

        final View view2 = findViewById(R.id.view2);
        final Button btn_buat_janji = findViewById(R.id.btn_buat_janji);

        LayananFragment layananFragment = new LayananFragment();
        DokterRSFragment dokterRSFragment = new DokterRSFragment();
        //
        ViewPager mViewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(ProfilRSFragment.newInstance(), "Profil RS");
        mViewPagerAdapter.addFragment(layananFragment.newInstance(this,"RS Advent Bandung"), "Layanan");
        mViewPagerAdapter.addFragment(dokterRSFragment.newInstance(this,"RS Advent Bandung"), "Dokter");
        mViewPager.setAdapter(mViewPagerAdapter);

        final ImageView IVRS = findViewById(R.id.IVRS);
        final TextView TVNamaRS = findViewById(R.id.TVNamaRS);
        final TextView TVAlamatRS = findViewById(R.id.TVAlamatRS);

        XTabLayout tabLayout = (XTabLayout) findViewById(R.id.tabs);
        tabLayout.setxTabDisplayNum(3);
        tabLayout.setupWithViewPager(mViewPager);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(getString(R.string.RumahSakit)).child(StringFunction.DeleteDotFromString(NamaRs));
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Hospital hospital = dataSnapshot.getValue(Hospital.class);
                TVNamaRS.setText(hospital.getNama());
                TVAlamatRS.setText(hospital.getAddress());
                Picasso.with(getApplicationContext()).load(hospital.getUrl_profil()).placeholder(R.drawable.placeholder_ivrs_sementara).into(IVRS);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //
            }
        });

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBar);

        final String finalNamaRs = NamaRs;
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    ToolbarTV.setText(finalNamaRs);
                    isShow = true;
                } else if(isShow) {
                    ToolbarTV.setText(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                //
            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0){
                    view2.setVisibility(View.VISIBLE);
                    btn_buat_janji.setVisibility(View.VISIBLE);
                }
                else{
                    view2.setVisibility(View.GONE);
                    btn_buat_janji.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                //
            }
        });

        btn_buat_janji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilRS.this,VerifikasiPasienFromProfilRSFragment.class);
                intent.putExtra(getString(R.string.JenisLayanan),getString(R.string.RumahSakit));
                intent.putExtra(getString(R.string.PenyediaLayanan),getSharedPreferenceLocal("NamaRS"));
                startActivity(intent);
            }
        });

        ImageView btn_location = findViewById(R.id.btn_location);
        //final String finalNamaRs1 = NamaRs;
        final String finalNamaRs1 = StringFunction.DeleteDotFromString(NamaRs);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference().child("Rumah Sakit").child(finalNamaRs1).child("url_alamat");
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String url = dataSnapshot.getValue().toString();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void setSharedPreferenceLocal(String key, String value){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.Tampungan),MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    private String getSharedPreferenceLocal(String key){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.Tampungan),MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }

    private void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        email_key_new = sharedPreferences.getString(email_key,"");
    }
}
