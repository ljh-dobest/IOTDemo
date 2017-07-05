package com.min.iotdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ike.mylibrary.util.T;
import com.ike.mylibrary.widget.dialog.LoadDialog;
import com.min.iotdemo.adapter.ElvChildEquimentCheckAdapter;
import com.min.iotdemo.adapter.ElvChildEquipmentAdapter;
import com.min.iotdemo.adapter.ElvMainEquimentAdapter;
import com.min.iotdemo.adapter.ElvMainEquimentCheckAdapter;
import com.min.iotdemo.base.BaseMvpActivity;
import com.min.iotdemo.bean.Equiment;
import com.min.iotdemo.interfaces.IMainView;
import com.min.iotdemo.presenter.MainDataPresenter;
import com.min.iotdemo.utils.RedisControl;
import com.min.iotdemo.view.MyElvListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity<IMainView, MainDataPresenter> implements IMainView, ExpandableListView.OnGroupClickListener, ExpandableListView.OnGroupCollapseListener {
    @BindView(R.id.ll_main_back)
    LinearLayout llMainBack;
    @BindView(R.id.tv_main_control)
    TextView tvMainControl;
    @BindView(R.id.ex_lv_equipment_main)
    MyElvListView exLvEquipmentMain;
    @BindView(R.id.ex_lv_main_equipment_check)
    MyElvListView exLvMainEquipmentCheck;
    @BindView(R.id.ex_lv_equipment_child)
    MyElvListView exLvEquipmentChild;
    @BindView(R.id.ex_lv_child_equipment_check)
    MyElvListView exLvChildEquipmentCheck;
    private ElvMainEquimentAdapter mainEquimentAdapter;
    private ElvChildEquipmentAdapter childEquipmentAdapter;
    private ElvChildEquimentCheckAdapter childEquimentCheckAdapter;
    private ElvMainEquimentCheckAdapter mainEquimentCheckAdapter;
    private List<Equiment> mainEquiments;
    private List<Equiment> childEquiments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter.getEquimentStatus();
        initView();
    }

    @Override
    public MainDataPresenter initPresenter() {
        return new MainDataPresenter();
    }

    private void initView() {
        initData();
        mainEquimentAdapter = new ElvMainEquimentAdapter(mainEquiments, this);
        exLvEquipmentMain.setAdapter(mainEquimentAdapter);
        mainEquimentCheckAdapter=new ElvMainEquimentCheckAdapter(mainEquiments,this);
        exLvMainEquipmentCheck.setAdapter(mainEquimentCheckAdapter);

        childEquipmentAdapter = new ElvChildEquipmentAdapter(childEquiments, this);
        exLvEquipmentChild.setAdapter(childEquipmentAdapter);
        childEquimentCheckAdapter=new ElvChildEquimentCheckAdapter(childEquiments,this);
        exLvChildEquipmentCheck.setAdapter(childEquimentCheckAdapter);

        exLvEquipmentMain.setOnGroupClickListener(this);
        exLvEquipmentMain.setOnGroupCollapseListener(this);

        exLvEquipmentChild.setOnGroupClickListener(this);
        exLvEquipmentChild.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                exLvChildEquipmentCheck.collapseGroup(0);
            }
        });
    }

    private void initData() {
        mainEquiments = new ArrayList<>();
        mainEquiments.add(new Equiment("Button-001", "按钮开关", "0", false));
        mainEquiments.add(new Equiment("Touch-001", "触控开关", "0", false));
        mainEquiments.add(new Equiment("Reed_switch-001", "磁簧开关", "0", false));
        mainEquiments.add(new Equiment("Light_cover-001", "光遮断", "0", false));
        mainEquiments.add(new Equiment("Mercury_switch-001", "水银开关", "0", false));
        mainEquiments.add(new Equiment("Tilt_switch-001", "倾斜开关", "0", false));
        childEquiments = new ArrayList<>();
        childEquiments.add(new Equiment("LED-001", "LED", "0", false));
        childEquiments.add(new Equiment("Active_sounder-001", "驱动蜂鸣器", "0", false));
        childEquiments.add(new Equiment("Relay-001", "继电器", "0", false));
        childEquiments.add(new Equiment("MQ2-001", "MQ2", "0", false));
        childEquiments.add(new Equiment("MQ3-001", "MQ3", "0", false));
        childEquiments.add(new Equiment("Voice_control-001", "咪头模块", "0", false));
        childEquiments.add(new Equiment("Mini_reed-001", "迷你磁黄", "0", false));
        childEquiments.add(new Equiment("Ultrasonic-001", "超声波测距", "0", false));
        childEquiments.add(new Equiment("laser-001", "光线模块", "0", false));
        childEquiments.add(new Equiment("Shock-001", "震动魔模块", "0", false));
        childEquiments.add(new Equiment("Percussion-001", "敲击模块", "0", false));
    }


    @OnClick({R.id.ll_main_back, R.id.tv_main_control})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_main_back:
                setAllEquimentStatus(childEquiments, mainEquiments);
//                setChildEquimentStatus(childEquiments,childEquipmentAdapter);
                break;
            case R.id.tv_main_control:
                LoadDialog.show(this);
                List<List<Equiment>> data = getPostData();
                if (data.get(0).size() > 1) {
                    T.showShort(this, "只能选择一个主控件");
                    LoadDialog.dismiss(this);
                    return;
                }
                if (data.get(1).size() == 0) {
                    T.showShort(this, "至少选择一个副控件");
                    LoadDialog.dismiss(this);
                    return;
                }
                presenter.postControlData(data.get(0), data.get(1));
                break;
        }
    }

    /**
     * 定时查询设备状态
     *
     * @param childEquiments
     * @param mainEquiments
     */
    private void setAllEquimentStatus(final List<Equiment> childEquiments, final List<Equiment> mainEquiments) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<Equiment> allEquiment = new ArrayList<Equiment>();
                allEquiment.addAll(mainEquiments);
                allEquiment.addAll(childEquiments);
                List<String> statusList=new ArrayList<String>();
                try {
                statusList = RedisControl.getInstance().getAllstatus(allEquiment);
                }catch (Exception e){
                }
                final List<String> finalStatusList = statusList;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(finalStatusList.size()==0||finalStatusList==null){
                            return;
                        }
                        for (int i = 0; i < mainEquiments.size(); i++) {
                            String status = finalStatusList.get(i);
                            if (status == null) {
                                status = "0";
                            }
                            mainEquiments.get(i).setStatus(status);
                        }
                        mainEquimentAdapter.notifyDataSetChanged();
                        Log.e("bbb", "主设备已刷新");
                        for (int i = 0; i < childEquiments.size(); i++) {
                            String status = finalStatusList.get(mainEquiments.size() + i);
                            if (status == null) {
                                status = "0";
                            }
                            childEquiments.get(i).setStatus(status);
                        }
                        childEquipmentAdapter.notifyDataSetChanged();
                        Log.e("bbb", "子设备已刷新");
                    }
                });
            }
        }, 0, 3000);
    }

    private List<List<Equiment>> getPostData() {
        List<List<Equiment>> equiments = new ArrayList<>();
        List<Equiment> mainCheckEquimet = new ArrayList<>();
        List<Equiment> childCheckEquimet = new ArrayList<>();
        for (Equiment mainEquiment : mainEquiments) {
            if (mainEquiment.isCheck()) {
                mainCheckEquimet.add(mainEquiment);
            }
        }
        for (Equiment childEquiment : childEquiments) {
            if (childEquiment.isCheck()) {
                childCheckEquimet.add(childEquiment);
            }
        }
        equiments.add(mainCheckEquimet);
        equiments.add(childCheckEquimet);
        return equiments;
    }

    @Override
    public void showError() {
        LoadDialog.dismiss(this);
    }

    @Override
    public void setEquimentData(List<Equiment> data) {

    }

    @Override
    public void succeedToControl(String msg) {
        LoadDialog.dismiss(this);
        T.showShort(this, msg);
    }

    @Override
    public void succeedToSet() {
        LoadDialog.dismiss(this);
        T.showShort(this, "组合设置成功");
    }


    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        switch (parent.getId()) {
            case R.id.ex_lv_equipment_main:
                exLvMainEquipmentCheck.expandGroup(0);
                break;
            case R.id.ex_lv_equipment_child:
                exLvChildEquipmentCheck.expandGroup(0);
                break;
        }
        return false;
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        exLvMainEquipmentCheck.collapseGroup(0);
    }
}
