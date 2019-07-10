package com.demo.prose.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.demo.prose.R;
import com.demo.prose.adapter.CommonFrameFragmentAdapter;
import com.demo.prose.base.BaseFragment;




public class MapaFragment extends BaseFragment {

    private CommonFrameFragmentAdapter adapter;

    MapView mMapView = null;
    private AMap aMap;
    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.basicmap_fragment, null);
        return view;


    }
    @Override
    protected void initData() {
        super.initData();
    }

}

   /* @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapView.onCreate(savedInstanceState);


    }

    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }*/

   /* private static MapaFragment fragment = null;
    public static final int POSITION = 0;

    public static Fragment newInstance() {
        if (fragment == null) {
            synchronized (MapaFragment.class) {
                if (fragment == null) {
                    fragment = new MapaFragment();
                }
            }
        }
        return fragment;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapView mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

    }
}*/

        /*MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap aMap = mapView.getMap();*/


