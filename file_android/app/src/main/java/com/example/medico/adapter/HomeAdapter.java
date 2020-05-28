package com.example.medico.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.medico.FungsiFungsi.DateFunction;
import com.example.medico.R;
import com.example.medico.activity.ReadArticle;
import com.example.medico.activity.RoundedCornersTransformation;
import com.example.medico.model.Bacaan;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.BacaanViewHolder> {
    private ArrayList<Bacaan> dataList;
    public Context context;

    public HomeAdapter(Context context, ArrayList<Bacaan> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public HomeAdapter.BacaanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.bacaan_item, parent, false);
        return new BacaanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeAdapter.BacaanViewHolder holder, final int position) {
        holder.TVJudulBacaan.setText(dataList.get(position).getJudul());
        holder.TVPublisher.setText(dataList.get(position).getPublisher());
        final Transformation transformation = new RoundedCornersTransformation(30, 0);
        Picasso.with(context).load(dataList.get(position).getUrl_profil()).transform(transformation).placeholder(R.drawable.bacaan_preview_placeholder).fit().into(holder.IVBacaan);
        final String title = dataList.get(position).getJudul();

        DateFunction dateFunction = new DateFunction();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss");
        String currentDate = simpleDateFormat.format(new Date());
        String currentTime = simpleDateFormat1.format(new Date());
        int TanggalSekarang = dateFunction.getTanggalFromDate(currentDate);
        int BulanSekarang = dateFunction.ConvertMonthToInt(dateFunction.getBulanFromDate(currentDate));
        int TahunSekarang = dateFunction.getTahunFromDate(currentDate);
        int MenitSekarang = dateFunction.getMinuteFromTimeWithFormatHHmm(currentTime);
        int JamSekarang = dateFunction.getHourFromTimeWithFormatHHmm(currentTime);

        String PublishDate = dateFunction.ConvertDateFormatDDMMMMYYYYToDDMMYY(dataList.get(position).getPublish_time());
        int TanggalPublish = dateFunction.getTanggalFromDate(PublishDate);
        //int BulanPublish = dateFunction.ConvertMonthToInt(dateFunction.getBulanFromDate(PublishDate));
        int BulanPublish = dateFunction.getMonthIntFromDateWithMonthString(dataList.get(position).getPublish_time());
        Log.d("TAG","Bulan Publish : " + BulanPublish);
        int TahunPublish = dateFunction.getTahunFromDate(PublishDate);
        holder.TVPublishTime.setText(dateFunction.getDateDifferences(TanggalPublish,BulanPublish,TahunPublish,dataList.get(position).getMenit(),dataList.get(position).getJam(),TanggalSekarang, BulanSekarang,TahunSekarang,MenitSekarang,JamSekarang));

        holder.LLBacaanHomeRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ReadArticle.class);
                i.putExtra("judul",title);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class BacaanViewHolder extends RecyclerView.ViewHolder {
        private TextView TVJudulBacaan, TVPublisher, TVPublishTime;
        private ImageView IVBacaan;
        private LinearLayout LLBacaanHomeRV;
        public BacaanViewHolder(@NonNull View itemView) {
            super(itemView);
            TVJudulBacaan = itemView.findViewById(R.id.TVJudulBacaan);
            TVPublisher = itemView.findViewById(R.id.TVPublisher);
            TVPublishTime = itemView.findViewById(R.id.TVPublishTime);
            IVBacaan = itemView.findViewById(R.id.IVBacaan);
            LLBacaanHomeRV = itemView.findViewById(R.id.LLBacaanHomeRV);
        }
    }
}
