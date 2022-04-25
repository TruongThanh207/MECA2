package com.example.meca;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private ImageView imgAvata;
    private TextView tvEmail,tvName;
    Switch btnPower, btnMotor;
    TextView tvDataLow,tvDataMedium,tvDataHigh;
    TextView tvHome,tvlogout,tvdevices, tvhotline, tvhelp, tvMaintain, tvdiagram, tvresetpass;
    ImageView img_moto_conveyor, img_conveyor;
    ImageView img_motorH, img_motorM;
    ImageView img_sensorL, img_sensorM, img_sensorH;
    int flag = 0;
    String motor_med_fwd = "OFF", motor_med_rev = "OFF";
    String motor_high_fwd = "OFF", motor_high_rev = "OFF";
    
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHome = findViewById(R.id.home);
        tvlogout =findViewById(R.id.logoutacc);
        tvresetpass=findViewById(R.id.resetpass);
        tvdevices = findViewById(R.id.devices);
        tvMaintain = findViewById(R.id.maintenance);
        tvhotline = findViewById(R.id.phone);
        tvhelp = findViewById(R.id.help);
        tvdiagram = findViewById(R.id.diagram);

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
        tvdiagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "comming soon", Toast.LENGTH_SHORT).show();
            }
        });
        pd = new ProgressDialog(this);
        tvresetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pd.setMessage("Changing Password");
               showChangePasswordDialog();
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
                    if ("Motor Med Forward".equals(Objects.requireNonNull(m.getKey()))) {
                        if (Objects.requireNonNull(m.child("Value").getValue()).toString().equals("ON")) {
                            motor_med_fwd = "ON";
                        }
                        else {
                            motor_med_fwd = "OFF";
                        }
                    }
                    if ("Motor Med Reverse".equals(Objects.requireNonNull(m.getKey()))) {
                        if (Objects.requireNonNull(m.child("Value").getValue()).toString().equals("ON")){
                            motor_med_rev = "ON";
                        }
                        else {
                            motor_med_rev = "OFF";
                        }
                    }

                    if ("Motor High Forward".equals(Objects.requireNonNull(m.getKey()))) {
                        if (Objects.requireNonNull(m.child("Value").getValue()).toString().equals("ON")) {
                            motor_high_fwd = "ON";
                        }
                        else {
                            motor_high_fwd = "OFF";
                        }
                    }
                    if ("Motor High Reverse".equals(Objects.requireNonNull(m.getKey()))) {
                        if (Objects.requireNonNull(m.child("Value").getValue()).toString().equals("ON")){
                            motor_high_rev = "ON";
                        }
                        else {
                            motor_high_rev = "OFF";
                        }
                    }
                });
                if (motor_high_rev.equals("OFF") && motor_high_fwd.equals("OFF")){
                    img_motorH.setImageResource(R.drawable.stepping_motor);
                }
                if (motor_med_rev.equals("ON") && motor_high_fwd.equals("OFF")){
                    img_motorH.setImageResource(R.drawable.stepping_motor_rev);
                }
                if (motor_high_rev.equals("OFF") && motor_high_fwd.equals("ON")){
                    img_motorH.setImageResource(R.drawable.stepping_motor_fwd);
                }
                if (motor_high_rev.equals("ON") && motor_high_fwd.equals("ON")){
                    Toast.makeText(MainActivity.this, "Data Conflict!!", Toast.LENGTH_LONG).show();
                }
                if (motor_med_rev.equals("OFF") && motor_med_fwd.equals("OFF")){
                    img_motorM.setImageResource(R.drawable.stepping_motor);
                }
                if (motor_med_rev.equals("ON") && motor_med_fwd.equals("OFF")){
                    img_motorM.setImageResource(R.drawable.stepping_motor_rev);
                }
                if (motor_med_rev.equals("OFF") && motor_med_fwd.equals("ON")){
                    img_motorM.setImageResource(R.drawable.stepping_motor_fwd);
                }
                if (motor_med_rev.equals("ON") && motor_med_fwd.equals("ON")){
                    Toast.makeText(MainActivity.this, "Data Conflict!!", Toast.LENGTH_LONG).show();
                }
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
    private void showChangePasswordDialog(){
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_change_password,null);
        final EditText oldpasswordedt = view.findViewById(R.id.password_old_edt);
        final EditText newpasswordedt = view.findViewById(R.id.password_change_edt);
        Button updatepassWordbtn = view.findViewById(R.id.updatePassword);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);

        final AlertDialog dialog =builder.create();
        builder.create().show();

        updatepassWordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpassword = oldpasswordedt.getText().toString().trim();
                String newpassword = newpasswordedt.getText().toString().trim();
                if (TextUtils.isEmpty(oldpassword)){
                    Toast.makeText(MainActivity.this,"Nhập mật khẩu hiện tại ",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newpassword.length()<6){
                    Toast.makeText(MainActivity.this,"Nhập mật khẩu mới có ít nhất 6 kí tự ",Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
                updatePassword(oldpassword,newpassword);
            }
        });
    }

    private void updatePassword(String oldpassword, String newpassword) {
        pd.show();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(),oldpassword);
        user.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        user.updatePassword(newpassword)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        pd.dismiss();
                                        Toast.makeText(MainActivity.this,"Password Updated...",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pd.dismiss();
                                        Toast.makeText(MainActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this,"Mật khẩu hiện tại không đúng",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}