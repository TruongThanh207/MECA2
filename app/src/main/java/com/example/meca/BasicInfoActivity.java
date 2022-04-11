package com.example.meca;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meca.model.BasicInfoAdapter;
import com.example.meca.model.Devices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BasicInfoActivity extends AppCompatActivity {
    Devices device;
    FirebaseFirestore db1 = FirebaseFirestore.getInstance();
    List<Map<String, Object>> mapdata1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        device = (Devices) getIntent().getSerializableExtra("data");

        getBasicInfoDevice();
        getSupportActionBar().setTitle("Thông tin cơ bản "+device.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void getBasicInfoDevice() {
        mapdata1 = new ArrayList<>();
        String url1 = "ttbt/" + device.getName().toLowerCase() + "/ttcb";
        Log.d(TAG, "url  " + url1);
        db1.collection(url1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        mapdata1.add(document.getData());
                    }
                    setRecycleView1(mapdata1);
                } else { Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }
    private void setRecycleView1(List<Map<String, Object>> mapdata1) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewBasicInfo);
        BasicInfoAdapter adapter = new BasicInfoAdapter(mapdata1, BasicInfoActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(BasicInfoActivity.this));
        recyclerView.setAdapter(adapter);
    }
}
