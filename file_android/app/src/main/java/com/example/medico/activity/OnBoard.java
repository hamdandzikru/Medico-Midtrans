package com.example.medico.activity;

import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.medico.R;
import com.example.medico.model.User;

public class OnBoard extends AppCompatActivity {
    private TextView TVMed;
    private TextView TV1;
    private TextView TV2;
    private TextView TV3;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String status = i.getStringExtra("Status");
        setContentView(R.layout.activity_on_board);

        TVMed = findViewById(R.id.TVmedico);
        TV1 = findViewById(R.id.app_desc);
        TV2 = findViewById(R.id.TVSudahPunyaAkun);
        TV3 = findViewById(R.id.TVLogin);
        mButton = findViewById(R.id.ButtonOnBoard);

        Typeface mTF1 = Typeface.createFromAsset(getAssets(),"font/PlayfairDisplay-Bold.ttf");
        Typeface mTF2 = Typeface.createFromAsset(getAssets(),"font/NunitoSans-Regular.ttf");

        TVMed.setTypeface(mTF1);
        TV1.setTypeface(mTF2);
        TV2.setTypeface(mTF2);
        TV3.setTypeface(mTF2);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunActivitySignUp();
            }
        };

        View.OnClickListener ListenerLogin = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunActivityLogin();
            }
        };
        TV3.setOnClickListener(ListenerLogin);
        mButton.setOnClickListener(listener);
    }

    private void RunMainActivity(User user) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(getString(R.string.NamaDepan),user.getNamaDepan());
        i.putExtra(getString(R.string.NamaBelakang),user.getNamaBelakang());
        i.putExtra(getString(R.string.email),user.getEmail());
        i.putExtra(getString(R.string.password),user.getPassword());
        i.putExtra(getString(R.string.JenisKelamin),user.getJenisKelamin());
        i.putExtra("Status","Destroy");
        startActivity(i);
        finish();
    }

    private void RunActivityLogin() {
        Intent i = new Intent(this,SignIn.class);
        startActivity(i);
    }

    private void RunActivitySignUp(){
        Intent i = new Intent(this,SignUp.class);
        startActivity(i);
    }
}
