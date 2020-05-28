package com.example.medico.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medico.R;
import com.example.medico.activity.Ambulance;
import com.example.medico.activity.Klinik;
import com.example.medico.activity.SearchDokter;
import com.example.medico.activity.SearchRS;
import com.example.medico.adapter.HomeAdapter;
import com.example.medico.adapter.ViewPagerHomeAdapter;
import com.example.medico.model.Bacaan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {
    Handler h = new Handler();
    RecyclerView recyclerView;
    TextView toolbar_text;
    CardView CVRS, CVDokter;
    CardView CVAmbulans;
    CardView CVKlinik;
    private int delay = 8000;
    int page = 0;
    private ViewPager viewPager;
    Runnable runnable;
    private int[] pagerIndex = {0};

    ArrayList<Bacaan> list;
    HomeAdapter homeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final Context context = getActivity();
        recyclerView = view.findViewById(R.id.RecyclerViewArticleHome);

        Typeface customfont=Typeface.createFromAsset(context.getAssets(),"font/NunitoSans-Regular.ttf");
        Typeface customfontBold=Typeface.createFromAsset(context.getAssets(),"font/NunitoSans-Bold.ttf");
        TextView TVRumahSakit = view.findViewById(R.id.TVRumahSakit);;
        TextView TVDescRS = view.findViewById(R.id.TVDescRS);
        TextView TVKlinik = view.findViewById(R.id.TVKlinik);;
        TextView TVDescKlinik = view.findViewById(R.id.TVDescKlinik);;
        TextView TVDokter = view.findViewById(R.id.TVDokter);;
        TextView TVDescDokter = view.findViewById(R.id.TVDescDokter);;
        TextView TVAmbulans = view.findViewById(R.id.TVAmbulans);;
        TextView TVDescAmbulans = view.findViewById(R.id.TVDescAmbulans);;
        TextView TVBacaanMenarikHome = view.findViewById(R.id.TVBacaanMenarikHome);;
        TextView TVLihatSemuaHome = view.findViewById(R.id.TVLihatSemuaHome);;

        TVRumahSakit.setTypeface(customfontBold);
        TVDescRS.setTypeface(customfont);
        TVKlinik.setTypeface(customfontBold);
        TVDescKlinik.setTypeface(customfont);
        TVDokter.setTypeface(customfontBold);
        TVDescDokter.setTypeface(customfont);
        TVAmbulans.setTypeface(customfontBold);
        TVDescAmbulans.setTypeface(customfont);
        TVBacaanMenarikHome.setTypeface(customfontBold);
        TVLihatSemuaHome.setTypeface(customfont);

        CVRS = view.findViewById(R.id.CVRumahSakit);
        CVRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getActivity(), SearchRS.class);
            startActivity(intent);
            }
        });

        CVDokter = view.findViewById(R.id.CVDokter);
        CVDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchDokter.class);
                startActivity(intent);
            }
        });

        CVKlinik = view.findViewById(R.id.CVKlinik);
        CVKlinik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("TAGAR","COME HERE");
                //Toast.makeText(context,"COME HERE",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), Klinik.class);
                context.startActivity(intent);
            }
        });

        CVAmbulans = view.findViewById(R.id.CVAmbulans);
        CVAmbulans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Ambulance.class);
                context.startActivity(intent);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Bacaan").child("Menarik");
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Bacaan>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Bacaan p = dataSnapshot1.getValue(Bacaan.class);
                    list.add(p);
                }
                homeAdapter = new HomeAdapter(getContext(),list);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(homeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerHomeAdapter(getContext()));
        WormDotsIndicator dotsIndicator = (WormDotsIndicator) view.findViewById(R.id.dots_indicator);
        dotsIndicator.setViewPager(viewPager);


        h.postDelayed(new Runnable() {
            public void run() {
                pagerIndex[0]++;
                if (pagerIndex[0] >= 6) {
                    pagerIndex[0] = 0;
                }

                viewPager.setCurrentItem(pagerIndex[0]);
                runnable=this;

                h.postDelayed(runnable, delay);
            }
        }, delay);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
                pagerIndex[0] = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.ToolbarHome);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar_text = view.findViewById(R.id.toolbar_text);
        toolbar_text.setText("medico");
        toolbar_text.setTypeface(customfontBold);
        return view;
    }
}
