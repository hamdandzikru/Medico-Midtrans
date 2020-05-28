package com.example.medico.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medico.FungsiFungsi.StringFunction;
import com.example.medico.R;
import com.example.medico.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profil extends AppCompatActivity {
    public static final String TAG = "FrontName";
    String email_key = "";
    String email_key_new = "";
    String EMAIL_KEY = "Email";
    private User user = new User ("Belum di assign","Belum di assign","Belum di assign", "Belum di assign", "Belum di assign", "Belum di assign");
    TextView TVEmail;
    EditText ETPassword,ETNamaDepan,ETNamaBelakang,ETJenisKelamin,ETNoHP;
    ConstraintLayout clsimpan;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        getUsernameLocal();
        TVEmail = findViewById(R.id.TVEmail);
        ETPassword = findViewById(R.id.ETPassword);
        ETNamaDepan = findViewById(R.id.ETNamaDepan);
        ETNamaBelakang = findViewById(R.id.ETNamaBelakang);
        ETJenisKelamin = findViewById(R.id.ETJenisKelamin);
        ETNoHP = findViewById(R.id.ETNoHP);
        clsimpan = findViewById(R.id.clsimpan);


        reference = FirebaseDatabase.getInstance().getReference().child("users").child(email_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){ //jika id ditemukan


                    TVEmail.setText(dataSnapshot.child(getString(R.string.email)).getValue().toString());
                    ETPassword.setText(dataSnapshot.child(getString(R.string.password)).getValue().toString());
                    ETNamaDepan.setText(dataSnapshot.child(getString(R.string.NamaDepan)).getValue().toString());
                    ETNamaBelakang.setText(dataSnapshot.child(getString(R.string.NamaBelakang)).getValue().toString());
                    ETJenisKelamin.setText(dataSnapshot.child(getString(R.string.JenisKelamin)).getValue().toString());
                    if (dataSnapshot.hasChild(getString(R.string.nohp))){
                        ETNoHP.setText(dataSnapshot.child(getString(R.string.nohp)).getValue().toString());
                    }
//                    ETNoHP.setText(dataSnapshot.child(getString(R.string.nohp)).getValue().toString());
                    //ambil data password dari firebase

                }
                else{ // jika id tidak ditemukan
                    Toast.makeText(getApplicationContext(),"Email tidak ditemukan",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        clsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strnamadepan=user.setNamaDepan(ETNamaDepan.getText().toString());
                String strnamabelakang=user.setNamaBelakang(ETNamaBelakang.getText().toString());
                String strjeniskelamin=user.setJenisKelamin(ETJenisKelamin.getText().toString());
                String strpassword=user.setPassword(ETPassword.getText().toString());
                String strnohp=user.setnohp(ETNoHP.getText().toString());
                final User user2 = user;

//                String strnamadepan=ETNamaDepan.getText().toString();
//                String strnamabelakang=ETNamaBelakang.getText().toString();
//                String strjeniskelamin=ETJenisKelamin.getText().toString();
//                String strpassword=ETPassword.getText().toString();
//                String strnohp=ETNoHP.getText().toString();

                User usermodel = new User(strnamadepan, strnamabelakang, strjeniskelamin, email_key_new, strpassword, strnohp);
//                DatabaseReference  dbuser = FirebaseDatabase.getInstance().getReference("users");

//                dbuser.child(email_key_new).setValue(usermodel);
                RunHomeActivity(usermodel);
                Toast.makeText(getApplicationContext(),"Simpan Update",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void RunHomeActivity(User user) {
        Intent i = new Intent(this, MainActivity.class);
        user.setEmail(TVEmail.getText().toString());
        user.setPassword(ETPassword.getText().toString());
        final User user2 = user;
        i.putExtra(getString(R.string.NamaDepan),user.getNamaDepan());
        i.putExtra(getString(R.string.NamaBelakang),user.getNamaBelakang());
        i.putExtra(getString(R.string.email),user.getEmail());
        i.putExtra(getString(R.string.password),user.getPassword());
        i.putExtra(getString(R.string.JenisKelamin),user.getJenisKelamin());
        i.putExtra(getString(R.string.nohp),user.getnohp());
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
                dataSnapshot.getRef().child(getString(R.string.nohp)).setValue(user2.getnohp());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        startActivity(i);
        finish();
    }


    public void getUsernameLocal(){
        SharedPreferences sharedPreferences =getSharedPreferences("id", MODE_PRIVATE);
        email_key_new = sharedPreferences.getString(email_key,"");
    }
}
