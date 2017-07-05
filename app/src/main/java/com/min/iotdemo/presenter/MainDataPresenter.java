package com.min.iotdemo.presenter;

import com.min.iotdemo.base.BasePersenter;
import com.min.iotdemo.bean.Equiment;
import com.min.iotdemo.interfaces.IMainView;
import com.min.iotdemo.listener.OnMainViewDataListener;
import com.min.iotdemo.module.MainDataModule;

import java.util.List;

/**
 * Created by Min on 2017/7/1.
 */

public class MainDataPresenter extends BasePersenter<IMainView> implements OnMainViewDataListener {
    private MainDataModule mainDataModule;
    public MainDataPresenter(){
        mainDataModule=new MainDataModule();
    }

    public void getEquimentStatus(){
        mainDataModule.getEquimentstatus(this);
    }

    public void postControlData(List<Equiment> mainEquiment, List<Equiment> childEquiment){
        mainDataModule.postControlData(mainEquiment,childEquiment,this);
    }

    @Override
    public void showError() {
        if(mView!=null){
            mView.showError();
        }
    }

    @Override
    public void returnEquimentData(List<Equiment> data) {
        if(mView!=null){
            mView.setEquimentData(data);
        }
    }

    @Override
    public void succeedToControl(String msg) {
        if(mView!=null){
            mView.succeedToControl(msg);
        }
    }

    @Override
    public void succeedToSet() {
        if(mView!=null){
            mView.succeedToSet();
        }
    }
}
