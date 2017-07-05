package com.min.iotdemo.module;

import android.util.Log;

import com.min.iotdemo.bean.Equiment;
import com.min.iotdemo.listener.OnMainViewDataListener;
import com.min.iotdemo.utils.HttpUtils;
import com.min.iotdemo.utils.RedisControl;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;

/**
 * Created by Min on 2017/4/1.
 */

public class MainDataModule {
    private boolean isOpen=false;
  public void postControlData(List<Equiment> mainEquiment, List<Equiment> childEquiment, final OnMainViewDataListener listener){
      if(mainEquiment.size()>0){
          allControl(mainEquiment,childEquiment,listener);
      }else {
          singleControl(childEquiment, listener);
      }
  }

    private void allControl(List<Equiment> mainEquiment, List<Equiment> childEquiment, final OnMainViewDataListener listener) {
             String childName="";
        for (int i = 0; i < childEquiment.size(); i++) {
                childName=childName+childEquiment.get(i).getId()+",";
        }
              HttpUtils.postAllControl("/key_sort",mainEquiment.get(0).getId(),childName, new StringCallback() {
                  @Override
                  public void onError(Call call, Exception e, int id) {
                      listener.showError();
                  }

                  @Override
                  public void onResponse(String response, int id) {
                      listener.succeedToSet();
                  }
              });

    }


    private void singleControl(List<Equiment> childEquiment, final OnMainViewDataListener listener) {
        if(isOpen){
          HttpUtils.postSingleControl("/status", childEquiment.get(0).getId().replace("-001",""), "0", new StringCallback() {
              @Override
              public void onError(Call call, Exception e, int id) {

              }

              @Override
              public void onResponse(String response, int id) {
                  listener.succeedToControl("设备关闭成功");
                  isOpen=false;
              }
          });
      }else{
          HttpUtils.postSingleControl("/status", childEquiment.get(0).getName(), "1", new StringCallback() {
              @Override
              public void onError(Call call, Exception e, int id) {
                   listener.showError();
              }

              @Override
              public void onResponse(String response, int id) {
                     listener.succeedToControl("设备开启成功");
                    isOpen=true;
              }
          });
      }
    }

    public void getEquimentstatus(final OnMainViewDataListener listener){
      Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(RedisControl.getInstance().getValue("Tilt_switch-001"));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String jsonString) throws Exception {
                         String json=jsonString;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("error",throwable.getMessage());
                    }
                });
    }
}
