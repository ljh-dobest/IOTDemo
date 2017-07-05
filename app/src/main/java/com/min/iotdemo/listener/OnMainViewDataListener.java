package com.min.iotdemo.listener;

import com.min.iotdemo.bean.Equiment;

import java.util.List;

/**
 * Created by Min on 2017/7/1.
 */

public interface OnMainViewDataListener {
    void showError();
    void returnEquimentData(List<Equiment> data);
    void succeedToControl(String msg);
    void succeedToSet();
}
