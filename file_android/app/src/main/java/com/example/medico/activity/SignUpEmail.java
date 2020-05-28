package com.example.medico.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medico.FungsiFungsi.StringFunction;
import com.example.medico.R;
import com.example.medico.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpEmail extends AppCompatActivity {
    private TextView TV1,TV2;
    private EditText ETPassword, ETEmail;
    private Button mButton;
    private ImageView PasswordToggle, LineToVisibleGone;
    private User user = new User ("Belum di assign","Belum di assign","Belum di assign");
    private TextView WarningEmail,WarningPassword;
    ImageView LogoWarningEmail,LogoWarningPassword;

    DatabaseReference reference;

    String email_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email);

        Intent intent = getIntent();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.ToolbarSignUpEmail);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user.setNamaDepan(intent.getStringExtra(getString(R.string.NamaDepan)));
        user.setNamaBelakang(intent.getStringExtra(getString(R.string.NamaBelakang)));
        user.setJenisKelamin(intent.getStringExtra(getString(R.string.JenisKelamin)));

        TV1 = findViewById(R.id.TVDaftarSignUpEmail);
        TV2 = findViewById(R.id.TVMasukanEmailDanPassword);
        mButton = findViewById(R.id.ButtonSignUpEmail);
        LineToVisibleGone = findViewById(R.id.LineToVisibleGone);
        PasswordToggle = findViewById(R.id.PasswordToggle);
        ETPassword = findViewById(R.id.ETPassword);
        ETEmail = findViewById(R.id.ETEmail);
        WarningEmail = findViewById(R.id.WarningEmail);
        WarningPassword = findViewById(R.id.WarningPassword);
        LogoWarningEmail = findViewById(R.id.LogoWarningEmail);
        LogoWarningPassword = findViewById(R.id.LogoWarningPassword);

        WarningEmail.setText("");
        WarningPassword.setText("");
        LogoWarningEmail.setVisibility(View.GONE);
        LogoWarningPassword.setVisibility(View.GONE);

        Typeface mTF = Typeface.createFromAsset(getAssets(),"font/NunitoSans-Regular.ttf");

        TV1.setTypeface(mTF);
        TV2.setTypeface(mTF);
        mButton.setTypeface(mTF);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunHomeActivity(user);
            }
        });

        PasswordToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LineToVisibleGone.getVisibility() == View.VISIBLE){
                    //kalau togglenya terlihat, maka toggle di nonaktifkan dan visibilty passwordnya terlihat
                    LineToVisibleGone.setVisibility(View.GONE);
                    ETPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ETPassword.setSelection(ETPassword.length());

                }
                else{
                    // kalau togglenya tidak terlihat, maka toggle di aktifkan dan visibility passwordnya tidak terlihat
                    LineToVisibleGone.setVisibility(View.VISIBLE);
                    ETPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ETPassword.setSelection(ETPassword.length());
                }
            }
        });
    }

    private void RunHomeActivity(User user) {
        Intent i = new Intent(this, MainActivity.class);
        user.setEmail(ETEmail.getText().toString());
        user.setPassword(ETPassword.getText().toString());
        final User user2 = user;
        i.putExtra(getString(R.string.NamaDepan),user.getNamaDepan());
        i.putExtra(getString(R.string.NamaBelakang),user.getNamaBelakang());
        i.putExtra(getString(R.string.email),user.getEmail());
        i.putExtra(getString(R.string.password),user.getPassword());
        i.putExtra(getString(R.string.JenisKelamin),user.getJenisKelamin());
        i.putExtra("Status","Destroy");

        String user_id = StringFunction.ConvertEmailToID(user.getEmail());
        //Toast.makeText(getApplicationContext(),user_id,Toast.LENGTH_LONG).show();

        // menyimpan data kepada local storage (hand phone)
        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(email_key,user_id);
        editor.apply();

        //menyimpan pada firebase
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child(getString(R.string.NamaDepan)).setValue(user2.getNamaDepan());
                dataSnapshot.getRef().child(getString(R.string.NamaBelakang)).setValue(user2.getNamaBelakang());
                dataSnapshot.getRef().child(getString(R.string.JenisKelamin)).setValue(user2.getJenisKelamin());
                dataSnapshot.getRef().child(getString(R.string.email)).setValue(user2.getEmail());
                dataSnapshot.getRef().child(getString(R.string.password)).setValue(user2.getPassword());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        startActivity(i);
        finish();
    }
}