package com.demo.prose.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.maps2d.MapView;

/**
 * 作者：尚硅谷-杨光福 on 2016/7/21 20:37
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：常用框架的Frament
 */
public class CommonFrameFragmentAdapter extends BaseAdapter {

    private final Context mContext;
    private final MapView mDatas;

    public CommonFrameFragmentAdapter(Context context, MapView datas){
        this.mContext = context;
        this.mDatas = datas;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }


}
