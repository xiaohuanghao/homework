package com.demo.prose.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.RadioGroup;


import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.MapsInitializer;
import com.demo.prose.R;
import com.demo.prose.adapter.CommonFrameFragmentAdapter;
import com.demo.prose.base.BaseFragment;
import com.demo.prose.location.CustomLocationActivity;


public class MapaFragment extends BaseFragment {

    private CommonFrameFragmentAdapter adapter;
    private MapView mapView;
    private AMap aMap;

    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.basicmap_fragment, null);
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 卫星地图模式
        }

        return view;

    }


    /**
     * 初始化AMap对象
     */
    private void init() {
    }
    @Override
    protected void initData() {
        super.initData();
    }


    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}
   /*
   private AMap mMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basemap_fragment_activity);
		setUpMapIfNeeded();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
		}
	}
   @Override
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


