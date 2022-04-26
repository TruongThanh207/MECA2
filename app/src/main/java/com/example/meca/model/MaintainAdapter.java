package com.example.meca.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meca.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MaintainAdapter extends RecyclerView.Adapter<MaintainAdapter.ViewHolder>{
    private Context context;
    private List<Map<String, Object>> listdata;
    private String _url;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // RecyclerView recyclerView;
    public MaintainAdapter(List<Map<String, Object>> listdata, Context context, String url) {
        this.context = context;
        this.listdata = listdata;
        this._url = url;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item_maintain, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText(listdata.get(position).get("content").toString());
        holder.textView2.setText(listdata.get(position).get("date").toString());
        holder.imageView.setOnClickListener(v -> db.collection(_url).document((String) Objects.requireNonNull(listdata.get(position)
                .get("id")))
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                listdata.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, listdata.size());
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textView4);
            this.textView2 = (TextView) itemView.findViewById(R.id.textView2);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView2);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout2);
        }
    }
}
