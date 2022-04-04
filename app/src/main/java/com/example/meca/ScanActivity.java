package com.example.meca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
//import android.content.Intent;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;


import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.meca.model.Devices;
import com.google.zxing.Result;

import java.io.Serializable;

public class ScanActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private final int CAMERA_REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        setPermission();

        Devices[] myListData = new Devices[] {
                new Devices("PLC", R.drawable.plc),
                new Devices("HMI", R.drawable.hmi),
                new Devices("Inverter", android.R.drawable.ic_delete),
                new Devices("Motor", android.R.drawable.ic_dialog_dialer),
//                new Devices("Alert", android.R.drawable.ic_dialog_alert),
//                new Devices("Map", android.R.drawable.ic_dialog_map),
//                new Devices("Email", android.R.drawable.ic_dialog_email),
//                new Devices("Info", android.R.drawable.ic_dialog_info),
//                new Devices("Delete", android.R.drawable.ic_delete),
//                new Devices("Dialer", android.R.drawable.ic_dialog_dialer),
//                new Devices("Alert", android.R.drawable.ic_dialog_alert),
//                new Devices("Map", android.R.drawable.ic_dialog_map),
        };
        codeScanned(myListData);
    }

    private void codeScanned(Devices[] myListData) {
        CodeScannerView scannerView = findViewById(R.id.scan_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!result.getText().isEmpty()){
//                            String root = "com.example.meca.";
//                            String activity = root.concat(result.getText().concat("Activity"));
//                            Intent intent = null;
//                            try {
//                                intent = new Intent(ScanActivity.this, Class.forName(activity));
//                            } catch (ClassNotFoundException e) {
//                                e.getException();
//                            }
//                            intent.putExtra("DATA", result.getText());
//                            startActivity(intent);
//                            finish();
//   //                         startActivity(new Intent(ScanActivity.this, InfoDeviceActivity.class));
                            for(Devices num : myListData){
                                if (num.getName().equals(result.getText())){
                                    Intent intent = new Intent(ScanActivity.this, InfoDeviceActivity.class);
                                    intent.putExtra("data",(Serializable) num);
                                    startActivity(intent);
                                    break;
                                }

                            }

                        }else{
                            Toast.makeText(ScanActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }
    private void setPermission(){
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permission!= PackageManager.PERMISSION_GRANTED){
            makeRequest();
        }
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE );
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ScanActivity.this, "You need camera permission to be able use this app", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

}