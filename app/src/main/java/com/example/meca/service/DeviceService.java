package com.example.meca.service;

import com.example.meca.R;
import com.example.meca.model.Devices;

import java.util.ArrayList;
import java.util.List;

public class DeviceService implements DeviseData{
    @Override
    public List<Devices> getDataDocument() {
        List<Devices> myListData = new ArrayList<Devices>();

                myListData.add(new Devices("PLC", R.drawable.plc));
                myListData.add(new Devices("HMI", R.drawable.hmi));
                myListData.add(new Devices("Inverter", R.drawable.inverter));
                myListData.add(new Devices("Motor conveyor", R.drawable.motor));
                myListData.add(new Devices("Motor high", R.drawable.motor_br));
                myListData.add(new Devices("Motor medium", R.drawable.motor_br));
                myListData.add(new Devices("Senser low", R.drawable.senser_br));
                myListData.add(new Devices("Senser medium", R.drawable.senser_br));
                myListData.add(new Devices("Senser high", R.drawable.senser_br));

        return myListData;
    }
}
