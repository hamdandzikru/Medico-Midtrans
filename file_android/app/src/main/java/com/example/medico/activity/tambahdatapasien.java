package com.example.medico.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.medico.R;

public class tambahdatapasien extends AppCompatActivity {

    EditText ETNama, ETNIK, ETAlamat;
    RelativeLayout ETTTL;
    TextView TVPeringatanNama, TVPeringatanNIK, TVPeringatanTTL, TVPeringatanAlamat;
    Button Btnsimpan;
    TextView hint, tanggal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahdatapasien);



    }

    public void btnSimpan(View view) {

        startActivity(new Intent(this, datapasien.class));


    }
}
