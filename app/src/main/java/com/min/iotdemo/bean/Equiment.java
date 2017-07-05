package com.min.iotdemo.bean;

/**
 * Created by Min on 2017/7/1.
 */

public class Equiment {
    private String id;
    private String name;
    private String status;
    private boolean isCheck;

    public Equiment(String id, String name, String status, boolean isCheck) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.isCheck = isCheck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
