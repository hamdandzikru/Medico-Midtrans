package com.example.medico.fragment;

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
import com.example.medico.adapter.ProfilRSFragmentAdapter;

public class ProfilRSFragment extends Fragment {
    View view;
    public ProfilRSFragment() {
    }

    public static ProfilRSFragment newInstance(){
        ProfilRSFragment profilRSFragment = new ProfilRSFragment();
        Bundle args = new Bundle();
        profilRSFragment.setArguments(args);
        return profilRSFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profilrs, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new Space(20,1));
        recyclerView.setAdapter(new ProfilRSFragmentAdapter(getContext()));
        return view;
    }
}
