package com.example.meca;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.meca.model.BasicInfoAdapter;
import com.example.meca.model.Devices;
import com.example.meca.model.MaintainAdapter;
import com.example.meca.service.DeviceService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;

public class MaintenanceActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    EditText date;
    Devices device;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Map<String, Object>> mapdata;
    String url;
    DeviceService ds = new DeviceService();

    private int lastSelectedYear;
    private int lastSelectedMonth;
    private int lastSelectedDayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        device = (Devices) getIntent().getSerializableExtra("data");

        drawerLayout = findViewById(R.id.activity_maintain_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        getMaintainData();
        getSupportActionBar().setTitle("Thông tin bảo trì " + device.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getMaintainData() {
        mapdata = new ArrayList<>();
        url = "ttbt/" + ds.getMd5(device.getName().toLowerCase()) + "/data";
        Log.d(TAG, "url  " + url);
        db.collection(url).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map <String , Object > deviceData = document.getData();
                        Map <String , Object > objectId = new HashMap<>();
                        objectId.put("id", document.getId());
//                        int value = (int) deviceData.merge("id", document.getId(), null);
                        deviceData.putAll(objectId);
                        mapdata.add(deviceData);
                    }
                    List<Map<String, Object>> data = sort(mapdata);
                    for (int i = 0; i < data.size(); i++) {
                        Log.d(TAG, "date " + i + " " + new Date(data.get(i).get("date").toString()).getTime());
                    }
                    setRecycleView(data, url);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }

    public List<Map<String, Object>> sort(List<Map<String, Object>> data)
    {
        for(int i = 0; i < data.size() - 1; i++)
        {
            for (int j = i+1; j < data.size(); j++){
                long time1 = new Date((String) data.get(i).get("date")).getTime();
                long time2 = new Date((String) data.get(j).get("date")).getTime();

                if (time1 < time2){
                    Map<String, Object> tmp = data.get(i);
                    data.set(i, data.get(j));
                    data.set(j, tmp);
                }
            }
        }
        return data;
    }

    private void setRecycleView(List<Map<String, Object>> mapdata, String _url) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        MaintainAdapter adapter = new MaintainAdapter(mapdata, MaintenanceActivity.this, _url);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MaintenanceActivity.this));
        recyclerView.setAdapter(adapter);
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
        getMenuInflater().inflate(R.menu.maintain_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.add) {
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    showCreateModalMaintain();
                    return true;
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    void showCreateModalMaintain() {
        final Dialog dialog = new Dialog(MaintenanceActivity.this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.create_modal_maintain);

        //Initializing the views of the dialog.
        final EditText content = dialog.findViewById(R.id.editTextTextPersonName3);
        date = dialog.findViewById(R.id.editTextDate);
        final Button btnSelectDate = dialog.findViewById(R.id.btnSelectDate);
        Button submitButton = dialog.findViewById(R.id.buttonSubmit);

        formatDate(btnSelectDate);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = content.getText().toString();
                String _date = date.getText().toString();
                createMaintainData(name, _date);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void formatDate(Button btnSelectDate){
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(btnSelectDate);
            }
        });

        final Calendar c = Calendar.getInstance();
        this.lastSelectedYear = c.get(Calendar.YEAR);
        this.lastSelectedMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
    }

    void selectDate(Button btnSelectDate){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                date.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                lastSelectedYear = year;
                lastSelectedMonth = monthOfYear;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };

        DatePickerDialog datePickerDialog = null;

        datePickerDialog = new DatePickerDialog(this, dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
        // Show
        datePickerDialog.show();
    }

    void createMaintainData(String content, String date) {
        Map<String, Object> data = new HashMap<>();
        data.put("content", content);
        data.put("date", date);
        db.collection("/ttbt/" + device.getName().toLowerCase() + "/data")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        finish();
                        startActivity(getIntent());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }
}