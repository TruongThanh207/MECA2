package com.example.meca.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meca.InfoDeviceActivity;
import com.example.meca.R;
import com.example.meca.model.DeviceAdapter;
import com.example.meca.model.Devices;

import java.io.Serializable;
import java.util.List;

public class CabinetAdapter extends RecyclerView.Adapter<CabinetAdapter.ViewHolder>{
    private final List<Devices> listdata;
    private final Context context;

    // RecyclerView recyclerView;
    public CabinetAdapter(List<Devices> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item_cabinet, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CabinetAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(listdata.get(position).getImgId());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoDeviceActivity.class);
                intent.putExtra("data", (Serializable) listdata.get(position));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.recyclerViewCabinet);
        }
    }
}

