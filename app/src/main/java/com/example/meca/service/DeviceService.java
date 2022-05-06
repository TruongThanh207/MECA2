package com.example.meca.service;

import com.example.meca.R;
import com.example.meca.adapter.CabinetAdapter;
import com.example.meca.model.Devices;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class DeviceService implements DeviseData{
    @Override
    public List<Devices> getDataDocument() {
        List<Devices> myListData = new ArrayList<Devices>();
                myListData.add(new Devices("Tủ điện điều khiển", R.drawable.plc).setDevice(false));
                myListData.add(new Devices("Tủ điện động lực", R.drawable.hmi).setDevice(false));
                myListData.add(new Devices("PLC", R.drawable.plc).setType("tủ điện điều khiển"));
                myListData.add(new Devices("HMI", R.drawable.hmi).setType("tủ điện điều khiển"));
                myListData.add(new Devices("Inverter", R.drawable.inverter).setType("tủ điện động lực"));
                myListData.add(new Devices("Motor medium", R.drawable.motor_br));
                myListData.add(new Devices("Senser low", R.drawable.senser_br));
                myListData.add(new Devices("Senser medium", R.drawable.senser_br));
                myListData.add(new Devices("Senser high", R.drawable.senser_br));

        return myListData;
    }

    @Override
    public String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
