package com.example.meca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meca.model.Devices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InfoDeviceActivity extends AppCompatActivity {
//    private TextView tvtest;
    private Devices device;
    private DatabaseReference mDatabase;
    Button btnVPdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_device);
//        tvtest =findViewById(R.id.textView4);
        device = (Devices) getIntent().getSerializableExtra("data");
//        tvtest.setText(device.getName());
        getSupportActionBar().setTitle(device.getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        myRef.setValue("Hello, World!");
//
//        Toast.makeText(InfoDeviceActivity.this, "device context", Toast.LENGTH_SHORT).show();

        btnVPdf = findViewById(R.id.buttonViewpdf);
        btnVPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoDeviceActivity.this, ViewpdfActivity.class));

            }
        });
    }
}