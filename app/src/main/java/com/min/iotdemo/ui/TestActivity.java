package com.min.iotdemo.ui;


import android.util.Log;

import com.min.iotdemo.Api;
import com.min.iotdemo.RetrofitProvider;
import com.min.iotdemo.bean.LoginResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;


/**
 * Created by Min on 2017/6/28.
 */

public class TestActivity {
    public static void test1(){
        //创建一个上游 Observable：
       Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        Observer<Integer> observer=new Observer<Integer>(){

            @Override
            public void onSubscribe(Disposable d) {
                Log.d("a","onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.d("a","value");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d("a","onComplete");
            }
        };
        observable.subscribe(observer);
    }
    public static void getInternetInfo(){
        Api api=RetrofitProvider.get().create(Api.class);
        api.getTop250(1,1).subscribeOn(Schedulers.io())
                          .observeOn(AndroidSchedulers.mainThread())
                          .subscribe(new Consumer<Response<ResponseBody>>() {
                              @Override
                              public void accept(Response<ResponseBody> responseBodyResponse) throws Exception {
                                  String MSG=responseBodyResponse.message();
                              }
                          }, new Consumer<Throwable>() {
                              @Override
                              public void accept(Throwable throwable) throws Exception {
                                  Log.e("error",throwable.getMessage());
                              }
                          });
    }

    public static void login(){
        Api api= RetrofitProvider.get().create(Api.class);
        api.login("11","22")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception {
                        String MSG="0";
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("error",throwable.getMessage());
                    }
                });
    }

    public static void testMap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "转换为 " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("a", s);
            }
        });
    }
}
