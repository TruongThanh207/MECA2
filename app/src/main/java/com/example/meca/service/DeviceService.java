package com.example.meca.service;

import com.example.meca.R;
import com.example.meca.model.Devices;

import java.util.ArrayList;
import java.util.List;

public class DeviceService implements DeviseData{
    @Override
    public List<Devices> getDataDocument() {
        List<Devices> myListData = new ArrayList<Devices>();
        myListData.add(new Devices("PLC",R.drawable.plc));
        myListData.add(new Devices("HMI", R.drawable.hmi));
        myListData.add(new Devices("Invecter", android.R.drawable.ic_delete));
        myListData.add(new Devices("Motor", android.R.drawable.ic_dialog_dialer));
        return myListData;
    }
}
