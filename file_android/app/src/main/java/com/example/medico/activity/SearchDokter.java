package com.example.medico.activity;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.medico.Dialog.SpecialistTypeSearchRSDialog;
import com.example.medico.R;
import com.example.medico.adapter.SearchDokterAdapter;
import com.example.medico.model.Doctor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchDokter extends AppCompatActivity implements SpecialistTypeSearchRSDialog.ButtomSheetListener {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dokter);

        Toolbar mToolbar = findViewById(R.id.ToolbarSearchDokter);
        TextView toolbarText = findViewById(R.id.ToolbarTextDokter);
        TextView TVLihatSemua = findViewById(R.id.TVLihatSemua);
        RelativeLayout RL_sp_umum = findViewById(R.id.RL_sp_umum);
        RelativeLayout RL_sp_anak = findViewById(R.id.RL_sp_anak);
        RelativeLayout RL_sp_gigi = findViewById(R.id.RL_sp_gigi);
        RelativeLayout RL_sp_mata = findViewById(R.id.RL_sp_mata);
        RelativeLayout RL_sp_kandungan = findViewById(R.id.RL_sp_kandungan);
        RelativeLayout RL_sp_tulang = findViewById(R.id.RL_sp_tulang);
        RelativeLayout RL_sp_otak = findViewById(R.id.RL_sp_otak);
        RelativeLayout RL_sp_telinga = findViewById(R.id.RL_sp_telinga);
        final RecyclerView recyclerView = findViewById(R.id.RVSearchDokter);
        mRecyclerView = findViewById(R.id.RVSearchDokter);


        final Boolean[] finish_setup_data = {false};
        final ArrayList<Doctor> dataList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Dokter").child("Praktek");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Doctor doctor = dataSnapshot1.getValue(Doctor.class);
                    dataList.add(doctor);
                }
                finish_setup_data[0] = true;
                SearchDokterAdapter searchDokterAdapter = new SearchDokterAdapter(dataList,getApplicationContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(searchDokterAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(getApplicationContext(),"Tidak dapat terhubung ke internet, periksa koneksi anda",Toast.LENGTH_LONG).show();
            }
        });

        if(toolbarText!=null && mToolbar!=null) {
            toolbarText.setText(getTitle());
            setSupportActionBar(mToolbar);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final SearchDokterAdapter searchDokterAdapter = new SearchDokterAdapter(dataList,getApplicationContext());

        RL_sp_umum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finish_setup_data[0]){
                    final ArrayList<Doctor> dataList = new ArrayList<>();
                    final ArrayList<Doctor> dataList2 = new ArrayList<>();

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Dokter").child("Praktek");
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                Doctor doctor = dataSnapshot1.getValue(Doctor.class);
                                dataList.add(doctor);
                            }
                            int size = dataList.size();
                            Log.d("TAG","Size =" + size + "");
                            int i = 0;
                            if (size > 0){
                                do{
                                    if (dataList.get(i).getSpecialist().equals("Umum")){
                                        dataList2.add(dataList.get(i));
                                    }
                                    if (i == size-1){
                                        break;
                                    }
                                    i++;
                                }while (true);
                            }
                            SearchDokterAdapter searchDokterAdapter1 = new SearchDokterAdapter(dataList2,getApplicationContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(searchDokterAdapter1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //
                        }
                    });
                }
            }
        });

        RL_sp_anak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finish_setup_data[0]){
                    final ArrayList<Doctor> dataList = new ArrayList<>();
                    final ArrayList<Doctor> dataList2 = new ArrayList<>();

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Dokter").child("Praktek");
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                Doctor doctor = dataSnapshot1.getValue(Doctor.class);
                                dataList.add(doctor);
                            }
                            int size = dataList.size();
                            Log.d("TAG","Size =" + size + "");
                            int i = 0;
                            if (size > 0){
                                do{
                                    if (dataList.get(i).getSpecialist().equals("Anak")){
                                        dataList2.add(dataList.get(i));
                                    }
                                    if (i == size-1){
                                        break;
                                    }
                                    i++;
                                }while (true);
                            }
                            SearchDokterAdapter searchDokterAdapter1 = new SearchDokterAdapter(dataList2,getApplicationContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(searchDokterAdapter1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //
                        }
                    });
                }
            }
        });

        RL_sp_gigi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finish_setup_data[0]){
                    final ArrayList<Doctor> dataList = new ArrayList<>();
                    final ArrayList<Doctor> dataList2 = new ArrayList<>();

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Dokter").child("Praktek");
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                Doctor doctor = dataSnapshot1.getValue(Doctor.class);
                                dataList.add(doctor);
                            }
                            int size = dataList.size();
                            Log.d("TAG","Size =" + size + "");
                            int i = 0;
                            if (size > 0){
                                do{
                                    if (dataList.get(i).getSpecialist().equals("Gigi")){
                                        dataList2.add(dataList.get(i));
                                    }
                                    if (i == size-1){
                                        break;
                                    }
                                    i++;
                                }while (true);
                            }
                            SearchDokterAdapter searchDokterAdapter1 = new SearchDokterAdapter(dataList2,getApplicationContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(searchDokterAdapter1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //
                        }
                    });
                }
            }
        });

        RL_sp_mata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finish_setup_data[0]){
                    final ArrayList<Doctor> dataList = new ArrayList<>();
                    final ArrayList<Doctor> dataList2 = new ArrayList<>();

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Dokter").child("Praktek");
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                Doctor doctor = dataSnapshot1.getValue(Doctor.class);
                                dataList.add(doctor);
                            }
                            int size = dataList.size();
                            Log.d("TAG","Size =" + size + "");
                            int i = 0;
                            if (size > 0){
                                do{
                                    if (dataList.get(i).getSpecialist().equals("Mata")){
                                        dataList2.add(dataList.get(i));
                                    }
                                    if (i == size-1){
                                        break;
                                    }
                                    i++;
                                }while (true);
                            }
                            SearchDokterAdapter searchDokterAdapter1 = new SearchDokterAdapter(dataList2,getApplicationContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(searchDokterAdapter1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //
                        }
                    });
                }
            }
        });

        RL_sp_kandungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finish_setup_data[0]){
                    final ArrayList<Doctor> dataList = new ArrayList<>();
                    final ArrayList<Doctor> dataList2 = new ArrayList<>();

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Dokter").child("Praktek");
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                Doctor doctor = dataSnapshot1.getValue(Doctor.class);
                                dataList.add(doctor);
                            }
                            int size = dataList.size();
                            Log.d("TAG","Size =" + size + "");
                            int i = 0;
                            if (size > 0){
                                do{
                                    if (dataList.get(i).getSpecialist().equals("Kandungan")){
                                        dataList2.add(dataList.get(i));
                                    }
                                    if (i == size-1){
                                        break;
                                    }
                                    i++;
                                }while (true);
                            }
                            SearchDokterAdapter searchDokterAdapter1 = new SearchDokterAdapter(dataList2,getApplicationContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(searchDokterAdapter1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //
                        }
                    });
                }
            }
        });

        RL_sp_tulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finish_setup_data[0]){
                    final ArrayList<Doctor> dataList = new ArrayList<>();
                    final ArrayList<Doctor> dataList2 = new ArrayList<>();

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Dokter").child("Praktek");
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                Doctor doctor = dataSnapshot1.getValue(Doctor.class);
                                dataList.add(doctor);
                            }
                            int size = dataList.size();
                            Log.d("TAG","Size =" + size + "");
                            int i = 0;
                            if (size > 0){
                                do{
                                    if (dataList.get(i).getSpecialist().equals("Tulang")){
                                        dataList2.add(dataList.get(i));
                                    }
                                    if (i == size-1){
                                        break;
                                    }
                                    i++;
                                }while (true);
                            }
                            SearchDokterAdapter searchDokterAdapter1 = new SearchDokterAdapter(dataList2,getApplicationContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(searchDokterAdapter1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //
                        }
                    });
                }
            }
        });

        RL_sp_otak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finish_setup_data[0]){
                    final ArrayList<Doctor> dataList = new ArrayList<>();
                    final ArrayList<Doctor> dataList2 = new ArrayList<>();

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Dokter").child("Praktek");
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                Doctor doctor = dataSnapshot1.getValue(Doctor.class);
                                dataList.add(doctor);
                            }
                            int size = dataList.size();
                            Log.d("TAG","Size =" + size + "");
                            int i = 0;
                            if (size > 0){
                                do{
                                    if (dataList.get(i).getSpecialist().equals("Otak")){
                                        dataList2.add(dataList.get(i));
                                    }
                                    if (i == size-1){
                                        break;
                                    }
                                    i++;
                                }while (true);
                            }
                            SearchDokterAdapter searchDokterAdapter1 = new SearchDokterAdapter(dataList2,getApplicationContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(searchDokterAdapter1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //
                        }
                    });
                }
            }
        });

        RL_sp_telinga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finish_setup_data[0]){
                    final ArrayList<Doctor> dataList = new ArrayList<>();
                    final ArrayList<Doctor> dataList2 = new ArrayList<>();

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Dokter").child("Praktek");
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                Doctor doctor = dataSnapshot1.getValue(Doctor.class);
                                dataList.add(doctor);
                            }
                            int size = dataList.size();
                            Log.d("TAG","Size =" + size + "");
                            int i = 0;
                            if (size > 0){
                                do{
                                    if (dataList.get(i).getSpecialist().equals("Telinga")){
                                        dataList2.add(dataList.get(i));
                                    }
                                    if (i == size-1){
                                        break;
                                    }
                                    i++;
                                }while (true);
                            }
                            SearchDokterAdapter searchDokterAdapter1 = new SearchDokterAdapter(dataList2,getApplicationContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(searchDokterAdapter1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //
                        }
                    });
                }
            }
        });

        TVLihatSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpecialistTypeSearchRSDialog bottomSheet = new SpecialistTypeSearchRSDialog();
                bottomSheet.show(getSupportFragmentManager(),"SpecialistTypeSearchRSDialog");
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
        return sharedPreferences.getString(key,"Belum terisi");
    }

    @Override
    public void onButtonClicked(final String text) {
        Log.d("TAG","COME HERE");
        final ArrayList<Doctor> dataList = new ArrayList<>();
        final ArrayList<Doctor> dataList2 = new ArrayList<>();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Dokter").child("Praktek");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Doctor doctor = dataSnapshot1.getValue(Doctor.class);
                    dataList.add(doctor);
                }
                int size = dataList.size();
                Log.d("TAG","Size =" + size + "");
                int i = 0;
                if (size > 0){
                    do{
                        if (dataList.get(i).getSpecialist().equals(text)){
                            dataList2.add(dataList.get(i));
                        }
                        if (i == size-1){
                            break;
                        }
                        i++;
                    }while (true);
                }
                SearchDokterAdapter searchDokterAdapter1 = new SearchDokterAdapter(dataList2,getApplicationContext());
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecyclerView.setAdapter(searchDokterAdapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //
            }
        });
    }
}
