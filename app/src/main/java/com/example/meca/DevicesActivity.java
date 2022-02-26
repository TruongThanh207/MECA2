package com.example.meca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DevicesActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
//    Button btntest,btntest1;
    TextView tvpdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        myRef.setValue("Hello, World!");


//        btntest= findViewById(R.id.buttontest);
//        btntest1= findViewById(R.id.buttontest1);
        tvpdf = findViewById(R.id.textViewpdf);
//        btntest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDatabase.child("Muto").child("Value").setValue("OFF");
//            }
//        });
//        btntest1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDatabase.child("Muto").child("Value").setValue("ON");
//            }
//        });
        tvpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DevicesActivity.this, ViewpdfActivity.class));
            }
        });
    }
}