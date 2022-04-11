package com.example.meca;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.meca.model.Devices;
import com.example.meca.model.MaintainAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaintenanceActivity extends AppCompatActivity {
    Devices device;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Map<String, Object>> mapdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        device = (Devices) getIntent().getSerializableExtra("data");

        getMaintainData();
    }

    public void getMaintainData(){
        mapdata = new ArrayList<>();
        String url = "ttbt/" + device.getName().toLowerCase() + "/data";
        Log.d(TAG, "url  " + url);
        db.collection(url).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        mapdata.add(document.getData());
                    }
                    setRecycleView(mapdata);
                } else { Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }

    private void setRecycleView(List<Map<String, Object>> mapdata) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        MaintainAdapter adapter = new MaintainAdapter(mapdata, MaintenanceActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MaintenanceActivity.this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
        final EditText date = dialog.findViewById(R.id.editTextDate);
        Button submitButton = dialog.findViewById(R.id.buttonSubmit);


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

    void createMaintainData(String content, String date) {
       Map<String, Object> data = new HashMap<>();
       data.put("content", content);
       data.put("date", date);
       db.collection("/ttbt/"+ device.getName().toLowerCase() + "/data")
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maintain_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }
}