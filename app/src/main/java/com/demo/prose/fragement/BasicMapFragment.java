package com.demo.prose.fragement;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.demo.prose.R;
import com.demo.prose.base.BaseFragment;

public class BasicMapFragment extends BaseFragment {
    @Override
    protected View initView() {
        MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(B);// 此方法必须重写
        AMap aMap = mapView.getMap();
        return null;
    }




        /*MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap aMap = mapView.getMap();*/

}
