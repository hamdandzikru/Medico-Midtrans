package com.example.medico.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.medico.activity.OnBoard;
import com.example.medico.R;
import com.example.medico.activity.Profil;
import com.example.medico.activity.datapasien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class ProfilFragment extends Fragment {
    public static final String TAG = "FrontName";
    String email_key = "";
    String email_key_new = "";
    String EMAIL_KEY = "Email";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        final Context context = getActivity();
        getUsernameLocal();
        RelativeLayout RLSignOut = view.findViewById(R.id.RLSignOut);
        RelativeLayout RLAkun = view.findViewById(R.id.RLAkun);
        RelativeLayout RLDataPasien = view.findViewById(R.id.RLDataPasien);
        RelativeLayout RLBantuan = view.findViewById(R.id.RLBantuan);

        final TextView TVNamaUser = view.findViewById(R.id.TVNamaUser);
        TextView TVToolbarProfil = view.findViewById(R.id.TVToolbarProfil);


        Typeface customfont=Typeface.createFromAsset(context.getAssets(),"font/NunitoSans-Bold.ttf");

        Toolbar ToolbarProfil = view.findViewById(R.id.ToolbarProfil);
        ((AppCompatActivity)getActivity()).setSupportActionBar(ToolbarProfil);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        TVToolbarProfil.setText("Profil");
        TVToolbarProfil.setTypeface(customfont);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(email_key_new).child("Nama Depan");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String FrontName = dataSnapshot.getValue().toString();

                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("users").child(email_key_new).child("Nama Belakang");
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String BackName = dataSnapshot.getValue().toString();
                        String username = FrontName + " " + BackName;
                        TVNamaUser.setText(username);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        RLSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // menghapud data username pada local storage (handphone)
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("id", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(email_key,null);
                editor.apply();

                //menuju On Board Act
                Intent intent = new Intent(getActivity(), OnBoard.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        RLAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //menuju On Board Act
                Intent intent = new Intent(getActivity(), Profil.class);
                startActivity(intent);

            }
        });

        RLDataPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //menuju data pasien

                Intent intent = new Intent(getActivity(), datapasien.class );
                startActivity(intent);



            }
        });

        RLBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //menuju bantuan

                Intent intent = new Intent("com.example.medico.activity.Chat");
                startActivity(intent);



            }
        });
        return view;
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("id", MODE_PRIVATE);
        email_key_new = sharedPreferences.getString(email_key,"");
    }
}
