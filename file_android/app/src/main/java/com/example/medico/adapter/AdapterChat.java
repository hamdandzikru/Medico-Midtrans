package com.example.medico.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.medico.R;
import com.example.medico.model.Message;

import java.util.ArrayList;
import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int CHAT_ME = 100;
    private final int CHAT_YOU = 200;

    private List<Message> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Message obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterChat(Context context) {
        ctx = context;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView text_content;
        public TextView text_time;
        public View lyt_parent;

        public ItemViewHolder(View v) {
            super(v);
            text_content = v.findViewById(R.id.text_content);
            text_time = v.findViewById(R.id.text_time);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == CHAT_ME) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_me, parent, false);
            vh = new ItemViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_you, parent, false);
            vh = new ItemViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final Message m = items.get(position);
            ItemViewHolder vItem = (ItemViewHolder) holder;
            vItem.text_content.setText(m.getContent());
            vItem.text_time.setText(m.getDate());
            vItem.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, m, position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position).isFromMe() ? CHAT_ME : CHAT_YOU;
    }

    public void insertItem(Message item) {
        this.items.add(item);
        notifyItemInserted(getItemCount());
    }

    public void setItems(List<Message> items) {
        this.items = items;
    }
}
