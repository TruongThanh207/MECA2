package com.example.meca;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meca.model.Devices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.Locale;

public class InfoDeviceActivity extends AppCompatActivity {
    private Devices device;
    private DatabaseReference mDatabase;
    Button btnVPdf;
    private ImageView imageView;
    private Button btnTtbt, btnTtcb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_device);
        device = (Devices) getIntent().getSerializableExtra("data");
        getSupportActionBar().setTitle(device.getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnVPdf = findViewById(R.id.buttonViewpdf);
        btnVPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoDeviceActivity.this, ViewpdfActivity.class);
                intent.putExtra("data", (Serializable) device);
                startActivity(intent);
            }
        });

        imageView =findViewById(R.id.theme);
        device = (Devices) getIntent().getSerializableExtra("data");
        imageView.setImageResource(device.getImgId());

        btnTtbt = findViewById(R.id.button);
        btnTtbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoDeviceActivity.this, MaintenanceActivity.class);
                intent.putExtra("data", (Serializable) device);
                startActivity(intent);
            }
        });
        btnTtcb = findViewById(R.id.buttonTtcb);
        btnTtcb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoDeviceActivity.this, BasicInfoActivity.class);
                intent.putExtra("data", (Serializable) device);
                startActivity(intent);
            }
        });

    }
}