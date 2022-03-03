package com.example.meca;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private DatabaseReference mDatabase;
    Switch btnPower, btnMotor;
    TextView tvDataLow,tvDataMedium,tvDataHigh;
    TextView tvHome,tvlogout,tvdevices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHome = findViewById(R.id.home);
        tvlogout =findViewById(R.id.logoutacc);
        tvdevices = findViewById(R.id.devices);

        tvDataHigh = findViewById(R.id.tvCHigh);
        tvDataMedium = findViewById(R.id.tvCMedium);
        tvDataLow = findViewById(R.id.tvCLow);
        GetDatabase();

        drawerLayout = findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // update toolbar title
        // getSupportActionBar().setTitle(" ten tung phan");

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
                        if (Objects.requireNonNull(m.child("Value").getValue()).toString().equals("ON")) btnMotor.setChecked(true);
                        else btnMotor.setChecked(false);
                    }
                    //  if ("XYZ".equals(Objects.requireNonNull(m.getKey()))) {
                    //      if (Objects.requireNonNull(m.child("Value").getValue()).toString().equals("ON")) btnXYZ.setChecked(true);
                    //  }
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
//        btnmotoron.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDatabase.child("Motor").child("Value").setValue("ON");
//            }
//        });
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
//            case R.id.search:
//                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.about:
//                Toast.makeText(this, "About button selected", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.help:
//                Toast.makeText(this, "Help button selected", Toast.LENGTH_SHORT).show();
//                return true;
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

}