package com.example.meca;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.meca.model.Devices;
import com.example.meca.model.MaintainAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaintenanceActivity extends AppCompatActivity {
    Devices device;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    List<Map<String, Object>> mapdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        device = (Devices) getIntent().getSerializableExtra("data");

        getMaintainData();


    }

    public void getMaintainData(){
        mapdata = new ArrayList<>();
        String url = "ttbt/" + device.getName().toLowerCase() + "/data";
        Log.d(TAG, "url  " + url);
        db.collection(url).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        mapdata.add(document.getData());
                    }
                    setRecycleView(mapdata);
                } else { Log.w(TAG, "Error getting documents.", task.getException());
                }
            }


        });


    }

    private void setRecycleView(List<Map<String, Object>> mapdata) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        MaintainAdapter adapter = new MaintainAdapter(mapdata, MaintenanceActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MaintenanceActivity.this));
        recyclerView.setAdapter(adapter);
    }
}