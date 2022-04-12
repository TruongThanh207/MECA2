package com.example.meca;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.meca.model.Devices;
import com.example.meca.service.DeviceService;
import com.example.meca.service.DeviseData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private DatabaseReference mDatabase;
    private ImageView imgAvata;
    private TextView tvEmail,tvName;
    Switch btnPower, btnMotor;
    TextView tvDataLow,tvDataMedium,tvDataHigh;
    TextView tvHome,tvlogout,tvdevices, tvhotline, tvhelp, tvMaintain;
    ImageView img_moto_conveyor, img_conveyor;
    ImageView img_motorH, img_motorM;
    ImageView img_sensorL, img_sensorM, img_sensorH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHome = findViewById(R.id.home);
        tvlogout =findViewById(R.id.logoutacc);
        tvdevices = findViewById(R.id.devices);
        tvMaintain = findViewById(R.id.maintenance);
        tvhotline = findViewById(R.id.phone);
        tvhelp = findViewById(R.id.help);

        tvDataHigh = findViewById(R.id.tvCHigh);
        tvDataMedium = findViewById(R.id.tvCMedium);
        tvDataLow = findViewById(R.id.tvCLow);

        img_moto_conveyor = findViewById(R.id.img_moto_conveyor);
        img_conveyor = findViewById(R.id.img_conveyor);
        img_motorH = findViewById(R.id.imageMotorR);
        img_motorM = findViewById(R.id.imageMotorL);
        img_sensorL = findViewById(R.id.imageSensorL);
        img_sensorM = findViewById(R.id.imageSensorM);
        img_sensorH = findViewById(R.id.imageSensorH);

        GetDatabase();

        drawerLayout = findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        header();

        //maintain
        // update toolbar title
        // getSupportActionBar().setTitle(" ten tung phan");

        tvMaintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AllListMaintainActivity.class));
            }
        });
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logout Successfull!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        tvhotline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hưng\n 0977263230", Toast.LENGTH_LONG).show();
            }
        });
        tvhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Facebook: https://facebook.com/tvgnuh1999\n Zalo: 0977263230", Toast.LENGTH_LONG).show();
            }
        });
        tvdevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DevicesActivity.class));
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        btnPower = findViewById(R.id.power);
        btnMotor = findViewById(R.id.switchmotor);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren().forEach(m -> {
                    if ("Power".equals(Objects.requireNonNull(m.getKey()))) {
                        if (Objects.requireNonNull(m.child("Value").getValue()).toString().equals("ON")) btnPower.setChecked(true);
                        else btnPower.setChecked(false);
                    }
                    if ("Motor".equals(Objects.requireNonNull(m.getKey()))) {
                        if (Objects.requireNonNull(m.child("Value").getValue()).toString().equals("ON")) {
                            btnMotor.setChecked(true);
                            img_moto_conveyor.setImageResource(R.drawable.motor_conveyor_on);
                            img_conveyor.setImageResource(R.drawable.conveyor_on);
                        }
                        else {
                            btnMotor.setChecked(false);
                            img_moto_conveyor.setImageResource(R.drawable.motor_conveyor);
                            img_conveyor.setImageResource(R.drawable.conveyor);
                        }
                    }
                    if ("Motor High Forward".equals(Objects.requireNonNull(m.getKey()))) {
                        if (Objects.requireNonNull(m.child("Value").getValue()).toString().equals("ON")) {
                            img_motorH.setImageResource(R.drawable.stepping_motor_fwd);
                        }
                    }
                    if ("Motor High Reverse".equals(Objects.requireNonNull(m.getKey()))) {
                        if (Objects.requireNonNull(m.child("Value").getValue()).toString().equals("ON")) {
                            img_motorH.setImageResource(R.drawable.stepping_motor_rev);
                        }
                    }
                    String motor_med_fwd = null, motor_med_rev = null;
                    if ("Motor Med Forward".equals(Objects.requireNonNull(m.getKey()))) {
                        motor_med_fwd = m.child("Value").getValue().toString();
                    }
                    if ("Motor Med Reverse".equals(Objects.requireNonNull(m.getKey())) || "Motor Med Forward".equals(Objects.requireNonNull(m.getKey()))) {
                        motor_med_rev = m.child("Value").getValue().toString();
                        motor_med_fwd = m.child("Value").getValue().toString();
                        if (motor_med_fwd == "OFF" && motor_med_rev == "OFF"){
                            img_motorM.setImageResource(R.drawable.stepping_motor);
                        }else if (motor_med_fwd == "ON") {
                            img_motorM.setImageResource(R.drawable.stepping_motor_fwd);
                        }else img_motorM.setImageResource(R.drawable.stepping_motor_rev);
                        Toast.makeText(MainActivity.this, motor_med_fwd + motor_med_rev, Toast.LENGTH_LONG).show();
                    }
//                    if ("Motor Med Reverse".equals(Objects.requireNonNull(m.getKey()))) {
//                        if (Objects.requireNonNull(m.child("Value").getValue()).toString().equals("ON")) {
//                            Toast.makeText(MainActivity.this, "ascd", Toast.LENGTH_LONG).show();
//                            img_motorM.setImageResource(R.drawable.stepping_motor_rev);
//                        }
////                        else
////                            img_motorM.setImageResource(R.drawable.stepping_motor);
//                    }

                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Not found data!", Toast.LENGTH_LONG).show();
            }
        });

        btnPower.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mDatabase.child("Power").child("Value").setValue("ON");
                } else {
                    mDatabase.child("Power").child("Value").setValue("OFF");
                }
            }
        });
        btnMotor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mDatabase.child("Motor").child("Value").setValue("ON");
                } else {
                    mDatabase.child("Motor").child("Value").setValue("OFF");
                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.scan:
                startActivity(new Intent(MainActivity.this, ScanActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void GetDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Count High/Value");
        DatabaseReference myRef1 = database.getReference("Count Medium/Value");
        DatabaseReference myRef2 = database.getReference("Count Low/Value");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                tvDataHigh.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                tvDataMedium.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                tvDataLow.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
    private void header(){
        imgAvata = findViewById(R.id.activity_main_imv_avatar);
        tvName   = findViewById(R.id.activity_main_tv_user_name);
        tvEmail  = findViewById(R.id.activity_main_tv_email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        //check name
        if (name == null){
            tvName.setVisibility(View.GONE); // hint
        }else{
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(name);
        }
        tvEmail.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.ic_avata_default).into(imgAvata);
    };
}