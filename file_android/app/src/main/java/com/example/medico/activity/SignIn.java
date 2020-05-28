package com.example.medico.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medico.R;
import com.example.medico.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignIn extends AppCompatActivity {
    private TextView TVLogin, TV1, TV2;
    private Button mButton;
    private EditText ETpassword,ETEmail;
    private ImageView PasswordToggle, LineToVisibleGone;
    private User user;
    private TextView WarningEmailSignIn, WarningPasswordLogin;
    private ImageView LogoWarningEmailLogin,LogoWarningPasswordLogin;

    DatabaseReference reference;

    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.ToolbarLogin);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TVLogin = findViewById(R.id.TVLogin);
        TV1 = findViewById(R.id.TVBelumPunyaAkun);
        TV2 = findViewById(R.id.TVDaftarSekarang);
        PasswordToggle = findViewById(R.id.PasswordToggle);
        ETEmail = findViewById(R.id.ETEmailLogin);
        ETpassword = findViewById(R.id.ETPasswordLogin);
        mButton = findViewById(R.id.ButtonLogin);
        LineToVisibleGone = findViewById(R.id.LineToVisibleGone);
        WarningEmailSignIn = findViewById(R.id.WarningEmailSignIn);
        WarningPasswordLogin = findViewById(R.id.WarningPasswordLogin);
        LogoWarningEmailLogin = findViewById(R.id.LogoWarningEmailLogin);
        LogoWarningPasswordLogin = findViewById(R.id.LogoWarningPasswordLogin);

        WarningEmailSignIn.setText("");
        WarningPasswordLogin.setText("");
        LogoWarningEmailLogin.setVisibility(View.GONE);
        LogoWarningPasswordLogin.setVisibility(View.GONE);

        LineToVisibleGone.setVisibility(View.VISIBLE);
        ETpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); //to hide
        ETpassword.setSelection(ETpassword.length());

        Typeface mTF = Typeface.createFromAsset(getAssets(),"font/NunitoSans-Regular.ttf");

        TV1.setTypeface(mTF);
        TV2.setTypeface(mTF);
        TVLogin.setTypeface(mTF);
        mButton.setTypeface(mTF);

        PasswordToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LineToVisibleGone.getVisibility() == View.VISIBLE){
                    //kalau togglenya terlihat, maka toggle di nonaktifkan dan visibilty passwordnya terlihat
                    LineToVisibleGone.setVisibility(View.GONE);
                    ETpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ETpassword.setSelection(ETpassword.length());

                }
                else{
                    // kalau togglenya tidak terlihat, maka toggle di aktifkan dan visibility passwordnya tidak terlihat
                    LineToVisibleGone.setVisibility(View.VISIBLE);
                    ETpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ETpassword.setSelection(ETpassword.length());
                }
            }
        });

        TV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunActivitySignUp(user);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunMainActivity(user);
            }
        });
    }

    private void RunActivitySignUp(User user) {
        Intent i = new Intent(this,SignUp.class);
        startActivity(i);
        finish();
    }

    private void MakeWarningToast() {
        if (ETEmail.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Isi alamat email",Toast.LENGTH_SHORT).show();
        }
        else if (ETpassword.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Isi password anda",Toast.LENGTH_SHORT).show();
        }
        else if (!isValidEmail(ETEmail.getText().toString())){
            Toast.makeText(getApplicationContext(),"Email is incorrect",Toast.LENGTH_SHORT).show();
        }
        else if (!isValidPassword(ETpassword.getText().toString())){
            Toast.makeText(getApplicationContext(),"Password is incorrect",Toast.LENGTH_SHORT).show();
        }
    }

    private void RunMainActivity(User user) {
        if (isValidEmail(ETEmail.getText().toString()) && isValidPassword(ETpassword.getText().toString())){
            reference = FirebaseDatabase.getInstance().getReference().child("users").child(ConvertEmailToID(ETEmail.getText().toString()));
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){ //jika id ditemukan
                        //ambil data password dari firebase
                        String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                        if (ETpassword.getText().toString().equals(passwordFromFirebase)){ //cek validasi password
                            //simpan username
                            // menyimpan data kepada local storage (handphone)
                            String user_id = ConvertEmailToID(ETEmail.getText().toString());
                            SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(username_key,user_id);
                            editor.apply();

                            Intent gotohome = new Intent(SignIn.this, MainActivity.class);
                            startActivity(gotohome);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"password salah",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{ // jika id tidak ditemukan
                        Toast.makeText(getApplicationContext(),"Email tidak ditemukan",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            MakeWarningToast();
        }
    }

    private Boolean isValidPassword(String password){
        if (password.isEmpty()){
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private Boolean isStringContainNumber(String string){
        Pattern P = Pattern.compile("[0-9]");
        Matcher m = P.matcher(string);
        return m.find();
    }

    private static String ConvertEmailToID(String email){
        int jmlh = email.length();
        int i = jmlh - 1;
        char temp = email.charAt(i);
        while (i > 0){
            temp = email.charAt(i);
            if (temp ==  '.'){
                break;
            }
            i--;
        }
        return email.substring(0,i) + "-" + email.substring(i+1,email.length());
    }
}
