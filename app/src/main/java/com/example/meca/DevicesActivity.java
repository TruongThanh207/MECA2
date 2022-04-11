package com.example.meca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meca.model.DeviceAdapter;
import com.example.meca.model.Devices;
import com.example.meca.service.DeviceService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DevicesActivity extends AppCompatActivity {
    ArrayList<String> listdevices = new ArrayList<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách thiết bị");

        Devices[] myListData = (new DeviceService()).getDataDocument().toArray(new Devices[0]);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        DeviceAdapter    adapter = new DeviceAdapter(myListData, DevicesActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}