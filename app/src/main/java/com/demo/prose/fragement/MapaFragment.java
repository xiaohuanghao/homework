package com.demo.prose.fragement;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.demo.prose.R;
import com.demo.prose.base.BaseFragment;

import static android.content.ContentValues.TAG;


public class MapaFragment extends BaseFragment {
    private ListView mListView;

    private String[] datas;
    private MapaFragment adapter;

    private MapView mapView;
    private AMap aMap;
    private View mapLayout;

    @Override
    protected View initView() {
        Log.e(TAG,"常用框架Fragment页面被初始化了...");
        View view = View.inflate(mContext,R.layout.basicmap,null);
        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data =  datas[position];
                Toast.makeText(mContext, "data=="+data, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapView mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

    }

}
    /*private static MapaFragment fragment=null;
    public static final int POSITION=0;

    private MapView mapView;
    private AMap aMap;
    private View mapLayout;

    public static Fragment newInstance(){
           if(fragment==null){
                  synchronized(MapaFragment.class){
                        if(fragment==null){
                              fragment=new MapaFragment();
                           }
                     }
                }
           return fragment;
       }*/








        /*MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap aMap = mapView.getMap();*/


