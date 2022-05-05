package com.example.meca.model;

import java.io.Serializable;

public class Devices implements Serializable {
    private String name;
    private int imgId;
    private String type;
    private boolean isDevice;
    public Devices(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
        this.type = null;
        this.isDevice = true;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getImgId() {
        return imgId;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getType() {
        return type;
    }

    public boolean isDevice() {
        return isDevice;
    }

    public Devices setDevice(boolean device) {
        isDevice = device;
        return this;
    }

    public Devices setType(String type) {
        this.type = type;
        return this;
    }
}
