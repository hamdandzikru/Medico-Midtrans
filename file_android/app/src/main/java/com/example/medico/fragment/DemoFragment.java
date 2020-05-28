package com.example.medico.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medico.R;
import com.example.medico.Space;
import com.example.medico.adapter.DemoAdapter;
import com.example.medico.model.DemoItem;

import java.util.ArrayList;
import java.util.List;

public class DemoFragment extends Fragment {

    public DemoFragment() {
    }


    public static DemoFragment newInstance() {
        DemoFragment fragment = new DemoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        RecyclerView recyclerViewDemo = view.findViewById(R.id.recyclerViewDemo);
        recyclerViewDemo.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewDemo.addItemDecoration(new Space(20,1));
        recyclerViewDemo.setAdapter(new DemoAdapter(feedItems(), getContext()));
        return view;
    }

    private List<DemoItem> feedItems() {
        String[] Titles = {"Taylor Swift - Look What You Made Me", "Bebe Rexha - Meant to Be", "Andra & Mara - Sweet Dreams", "Sam Smith - Too Good At Goodbyes "};
        String[] Description = {"By TaylorSwiftVEVO ", "By Bebe Rexha", "BySamSmithWorldVEVO", "SamSmithWorldVEVO "};
        String[] ImageUrls = {"https://cdn.pixabay.com/photo/2016/01/14/06/09/guitar-1139397_640.jpg", "https://cdn.pixabay.com/photo/2017/10/30/10/35/dance-2902034_640.jpg", "https://cdn.pixabay.com/photo/2017/09/17/11/10/luck-2758147_640.jpg", "https://cdn.pixabay.com/photo/2016/12/17/16/59/guitar-1913836_640.jpg"};
        List<DemoItem> demoItems = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < Titles.length; j++) {
                DemoItem demoItem = new DemoItem(Titles[j], Description[j], ImageUrls[j]);
                demoItems.add(demoItem);
            }
        }
        return demoItems;
    }


}
