package com.example.medico.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medico.R;
import com.example.medico.adapter.JadwalAdapter;
import com.example.medico.model.Ticket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class JadwalSayaFragment extends Fragment {
    private JadwalAdapter adapter;

    String email_key = "";
    String email_key_new = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_jadwal_saya, container, false);
        final Context context = getActivity();

        getUsernameLocal();
        final RecyclerView recyclerView = view.findViewById(R.id.RecyclerViewListJadwal);

        Typeface customfont=Typeface.createFromAsset(context.getAssets(),"font/NunitoSans-Bold.ttf");

        Toolbar toolbar = view.findViewById(R.id.ToolbarJadwalSaya);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        TextView TVToolbar = view.findViewById(R.id.TVToolbar);
        TVToolbar.setText(getString(R.string.JadwalSaya));
        TVToolbar.setTypeface(customfont);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tiket").child(email_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Ticket> dataList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Ticket ticket = dataSnapshot1.getValue(Ticket.class);
                    dataList.add(ticket);
                }
                //Set adapter
                adapter = new JadwalAdapter(dataList,getContext());
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("id", MODE_PRIVATE);
        email_key_new = sharedPreferences.getString(email_key,"");
    }



}
