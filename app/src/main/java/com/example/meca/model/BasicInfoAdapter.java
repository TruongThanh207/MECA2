package com.example.meca.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meca.R;

import java.util.List;
import java.util.Map;

public class BasicInfoAdapter extends RecyclerView.Adapter<BasicInfoAdapter.ViewHolder>{
    private Context context;
    private List<Map<String, Object>> listdata;

    public BasicInfoAdapter(List<Map<String, Object>> listdata, Context context) {
        this.context = context;
        this.listdata = listdata;
    }

    public BasicInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item_ttcb, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BasicInfoAdapter.ViewHolder holder, int position) {
        holder.textView1.setText(listdata.get(position).get("line1").toString());
        holder.textView2.setText(listdata.get(position).get("line2").toString());
        holder.textView3.setText(listdata.get(position).get("line3").toString());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView1 = (TextView) itemView.findViewById(R.id.textView_line1);
            this.textView2 = (TextView) itemView.findViewById(R.id.textView_line2);
            this.textView3 = (TextView) itemView.findViewById(R.id.textView_line3);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayoutTtcb);
        }
    }
}
