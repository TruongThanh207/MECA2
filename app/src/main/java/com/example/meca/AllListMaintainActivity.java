package com.example.meca;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.meca.model.Devices;
import com.example.meca.model.Maintain;
import com.example.meca.service.DeviceService;
import com.example.meca.service.DeviseData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllListMaintainActivity extends AppCompatActivity implements DeviseData {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<String> documents = new ArrayList<String>();
    HashMap<String, List<Maintain>> listDataChild = new HashMap<String, List<Maintain>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_list_maintain);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        getDocumentID();
        getData(documents);

        eventExpanse();
    }

    private void eventExpanse() {

    }

    private void getDocumentID() {
        getDataDocument().forEach(devices -> {
            documents.add(devices.getName());
        });
    }

    private void getData(List<String> docs) {
        docs.forEach(n->{
            List<Maintain> items = new ArrayList<Maintain>();
            db.collection("ttbt/" + n.toLowerCase() + "/data")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    items.add(new Maintain(document.getData().get("content").toString(), document.getData().get("date").toString()));
                                }
                                listDataChild.put(n.toLowerCase(), items);
                                if(docs.get(docs.size() - 1).equals(n)){
                                    listAdapter = new ExpandableListAdapter(AllListMaintainActivity.this, docs, listDataChild);
                                    expListView.setAdapter(listAdapter);

                                    expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                                        @Override
                                        public boolean onGroupClick(ExpandableListView parent, View v,
                                                                    int groupPosition, long id) {
                                            // Toast.makeText(getApplicationContext(),
                                            // "Group Clicked " + listDataHeader.get(groupPosition),
                                            // Toast.LENGTH_SHORT).show();
                                            return false;
                                        }
                                    });

                                    // Listview Group expanded listener
                                    expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                                        @Override
                                        public void onGroupExpand(int groupPosition) {
                                            Toast.makeText(getApplicationContext(),
                                                    documents.get(groupPosition) + " Expanded",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    // Listview Group collasped listener
                                    expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                                        @Override
                                        public void onGroupCollapse(int groupPosition) {
                                            Toast.makeText(getApplicationContext(),
                                                    documents.get(groupPosition) + " Collapsed",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                    // Listview on child click listener
                                    expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                                        @Override
                                        public boolean onChildClick(ExpandableListView parent, View v,
                                                                    int groupPosition, int childPosition, long id) {
                                            // TODO Auto-generated method stub
                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    documents.get(groupPosition)
                                                            + " : "
                                                            + listDataChild.get(
                                                            documents.get(groupPosition)).get(
                                                            childPosition), Toast.LENGTH_SHORT)
                                                    .show();
                                            return false;
                                        }
                                    });
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }

                    });
        });
    }

    @Override
    public List<Devices> getDataDocument() {
        List<Devices> ds = (new DeviceService()).getDataDocument();
        return ds;
    }
}