package com.example.medico.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medico.R;
import com.example.medico.model.DemoItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DemoAdapter extends RecyclerView.Adapter  {
    List<DemoItem> demoItems;
    Context context;

    public DemoAdapter(List<DemoItem> demoItems, Context context) {
        this.demoItems = demoItems;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.custom_row_demo, parent, false);
        return new DemoItemHolder(row);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DemoItem currentDemoItem = demoItems.get(position);
        DemoItemHolder demoItemHolder = (DemoItemHolder) holder;
        demoItemHolder.Title.setText(currentDemoItem.title);
        demoItemHolder.Description.setText(currentDemoItem.Description);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Picasso.with(context).load(currentDemoItem.imageUrl).centerCrop().resize(displayMetrics.widthPixels, displayMetrics.heightPixels / 3).into(demoItemHolder.Thumbnail);

    }

    @Override
    public int getItemCount() {
        //return demoItems.size();
        return 1;
    }

    public class DemoItemHolder extends RecyclerView.ViewHolder {
        ImageView Thumbnail;
        TextView Title, Description;

        public DemoItemHolder(View itemView) {
            super(itemView);
            Thumbnail = itemView.findViewById(R.id.imageViewThumbnail);
            Title = itemView.findViewById(R.id.textViewTitle);
            Description = itemView.findViewById(R.id.textViewDes);
        }
    }
}
