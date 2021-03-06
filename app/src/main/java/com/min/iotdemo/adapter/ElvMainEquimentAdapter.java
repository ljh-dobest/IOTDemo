package com.min.iotdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.min.iotdemo.R;
import com.min.iotdemo.bean.Equiment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Min on 2017/6/27.
 */

public class ElvMainEquimentAdapter extends BaseExpandableListAdapter {
    private List<Equiment> dataset = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    public ElvMainEquimentAdapter(List<Equiment> dataset, Context context) {
        this.dataset = dataset;
        this.mContext=context;
        inflater = LayoutInflater.from(mContext);
    }
    public List<Equiment> getData(){
        return dataset;
    }
    //  获得某个父项的某个子项
    @Override
    public Object getChild(int parentPos, int childPos) {
        return dataset.get(parentPos);
    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        return 1;
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int parentPos) {
        return dataset.size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int parentPos) {
        return dataset;
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int parentPos) {
        return parentPos;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int parentPos, int childPos) {
        return childPos;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.elv_parent_item, null);
        }
        TextView text = (TextView) view.findViewById(R.id.tv_parent_title);
        text.setText("主控件");
        return view;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.elv_child_item, null);
        }
        final Equiment equiment=dataset.get(childPos);
        TextView name = (TextView) view.findViewById(R.id.tv_child_name);
        TextView status = (TextView) view.findViewById(R.id.tv_child_status);
        name.setText(equiment.getName());
        status.setText(equiment.getStatus().equals("0")?"关闭":"开启");
        status.setBackgroundColor(equiment.getStatus().equals("0")?mContext.getResources().getColor(R.color.red):mContext.getResources().getColor(R.color.green));
        return view;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}