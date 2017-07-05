package com.min.iotdemo.interfaces;

import com.min.iotdemo.bean.Equiment;

import java.util.List;

/**
 * Created by Min on 2017/7/1.
 */

public interface IMainView {
    void showError();
    void setEquimentData(List<Equiment> data);
    void succeedToControl(String msg);
    void succeedToSet();
}
