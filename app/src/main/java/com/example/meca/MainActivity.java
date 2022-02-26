package com.example.meca;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private DatabaseReference mDatabase;
    Button btnPoweron,btnPoweroff, btnmotoron,btnmotoroff, btnPower;

    TextView tvHome,tvlogout,tvdevices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHome = findViewById(R.id.home);
        tvlogout =findViewById(R.id.logoutacc);
        tvdevices = findViewById(R.id.devices);

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
        btnPoweron= findViewById(R.id.buttononpower);
        btnPoweroff= findViewById(R.id.buttonoffpower);
        btnmotoron= findViewById(R.id.buttononmoter);
        btnmotoroff= findViewById(R.id.buttonoffmoter);
        btnPower = findViewById(R.id.power);

        btnPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, btnPower.getText(), Toast.LENGTH_LONG).show();
            }
        });


        btnPoweroff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Power").child("Value").setValue("OFF");
            }
        });
        btnPoweron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Power").child("Value").setValue("ON");
            }
        });
        btnmotoroff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Motor").child("Value").setValue("OFF");
            }
        });
        btnmotoron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Motor").child("Value").setValue("ON");
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

}