package com.example.medico.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medico.R;
import com.squareup.picasso.Picasso;

public class IkonJenisKelaminAdapter extends RecyclerView.Adapter<IkonJenisKelaminAdapter.ViewHoldder> {
    private int selectedPos = RecyclerView.NO_POSITION;
    private Context context;

    public IkonJenisKelaminAdapter(Context context1){
        context = context1;
    }

    @NonNull
    @Override
    public ViewHoldder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View v = layoutInflater.inflate(R.layout.item_ikon_jenis_kelamin,viewGroup,false);
        return new IkonJenisKelaminAdapter.ViewHoldder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldder viewHoldder, final int i) {
        if (i == 0){
            Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/medico-8c179.appspot.com/o/storage%2Fikon%2Flaki_laki.png?alt=media&token=6343b981-84bc-4137-9b66-bd32af678fdf").into(viewHoldder.imageView);
            viewHoldder.textView.setText("Laki - laki");
        }
        else{
            Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/medico-8c179.appspot.com/o/storage%2Fikon%2Fperempuan.png?alt=media&token=b28e6957-e664-407d-999f-c94439e03eda").into(viewHoldder.imageView);
            viewHoldder.textView.setText("Perempuan");
        }
        viewHoldder.itemView.setSelected(selectedPos == i);
        viewHoldder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selectedPos);
                selectedPos = i;
                notifyItemChanged(selectedPos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHoldder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ConstraintLayout constraintLayout;
        public ViewHoldder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
