package com.example.meca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.meca.adapter.CabinetAdapter;
import com.example.meca.model.Devices;
import com.example.meca.service.DeviceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CabinetActivity extends AppCompatActivity {
    Devices device;
    DeviceService ds = new DeviceService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet);
        device = (Devices) getIntent().getSerializableExtra("data");
        getSupportActionBar().setTitle(device.getName());
        getDeviceInCabinet(device.getName().toLowerCase());
    }

    private void getDeviceInCabinet(String name) {
        List<Devices> listdata = new ArrayList<>();
        ds.getDataDocument().forEach(e -> {
            if (Objects.equals(e.getType(), name)) {
                listdata.add(e);
            }
        });
        setRecycleView(listdata);

    }

    public void setRecycleView(List<Devices> myListData) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCabinet);
        CabinetAdapter adapter = new CabinetAdapter(myListData, CabinetActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}