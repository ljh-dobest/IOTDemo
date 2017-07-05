package com.min.iotdemo.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.min.iotdemo.utils.DateUtils;


/**
 * Created by Min on 2017/3/8.
 * 这个activity基类，主要完成与presenter层的绑定，与解绑
 * 或者一些共有的功能或属性
 * V:实现类要实现的接口类型
 * T：实现类要绑定的Presenter类型
 *
 */

public abstract class BaseMvpActivity<V,T extends BasePersenter<V>> extends AppCompatActivity {
    public T presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
             presenter=initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach((V)this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dettach();
    }

    public abstract T initPresenter();

    /**
     * 防止快速点击打开2个页面
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (DateUtils.isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
