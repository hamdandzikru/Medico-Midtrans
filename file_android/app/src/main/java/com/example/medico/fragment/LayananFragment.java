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
import com.example.medico.adapter.FragmentLayananAdapter;

public class LayananFragment extends Fragment {
    private Context context;
    private String NamaRS;

    public LayananFragment newInstance(Context context1, String NamaRS1){
        LayananFragment layananFragment = new LayananFragment();
        Bundle args = new Bundle();
        layananFragment.setArguments(args);
        NamaRS = NamaRS1;
        context = context1;
        return  layananFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layanan, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new Space(20,1));
        recyclerView.setAdapter(new FragmentLayananAdapter(getContext(),"RS Advent Bandung"));
        return view;
    }
}
