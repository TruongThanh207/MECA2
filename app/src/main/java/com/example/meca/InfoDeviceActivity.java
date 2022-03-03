package com.example.meca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.meca.model.Devices;

public class InfoDeviceActivity extends AppCompatActivity {
    private TextView tvtest;
    private Devices device;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_device);
        tvtest =findViewById(R.id.textView4);
        device = (Devices) getIntent().getSerializableExtra("data");
        tvtest.setText(device.getName());
    }
}