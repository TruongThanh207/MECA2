package com.example.meca.service;

import com.example.meca.model.Devices;

import java.util.List;

public interface DeviseData {
    List<Devices> getDataDocument();

    String getMd5(String input);
}
