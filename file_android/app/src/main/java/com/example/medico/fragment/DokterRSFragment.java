package com.example.medico.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medico.R;
import com.example.medico.Space;
import com.example.medico.adapter.FragmentDokterRSAdapter;

public class DokterRSFragment extends Fragment {
    private Context context;
    private String NamaRS;

    View view;
    public DokterRSFragment() {
    }

    public DokterRSFragment newInstance(Context context1, String NamaRs){
        DokterRSFragment dokterRSFragment = new DokterRSFragment();
        Bundle args = new Bundle();
        NamaRS = NamaRs;
        context = context1;
        return dokterRSFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dokterrs, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new Space(20,1));
        FragmentDokterRSAdapter fragmentDokterRSAdapter = new FragmentDokterRSAdapter(getContext());
        recyclerView.setAdapter(fragmentDokterRSAdapter);
        return view;
    }


}
