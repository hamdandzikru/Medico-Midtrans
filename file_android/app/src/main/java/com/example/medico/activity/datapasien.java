package com.example.medico.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.medico.R;

public class datapasien extends AppCompatActivity {

    EditText ETNama, ETNIK, ETTTL, ETAlamat;
    Button BtnSimpanDataPasien;
    TextView TVTambahdatapasien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datapasien);
    }

    public void BtnTbhDtPasien(View view) {
        startActivity(new Intent(this, tambahdatapasien.class));

    }
}
