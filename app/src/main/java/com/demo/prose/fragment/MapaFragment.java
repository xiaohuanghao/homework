package com.demo.prose.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.demo.prose.R;
import com.demo.prose.adapter.CommonFrameFragmentAdapter;
import com.demo.prose.base.BaseFragment;
import com.demo.prose.location.SensorEventHelper;
import com.demo.prose.tool.GeoToScreenActivity;
import com.demo.prose.util.AMapUtil;
import com.demo.prose.util.GeometryUtils;


import java.util.ArrayList;
import java.util.List;


//声明mlocationClient对象

public class MapaFragment extends BaseFragment implements LocationSource, AMapLocationListener, View.OnClickListener, AMap.OnMapClickListener {

    private CommonFrameFragmentAdapter adapter;
    private MapView mapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    //定位
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private SensorEventHelper mSensorHelper;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private boolean mFirstFix = false;
    private Marker mLocMarker;
    private TextView mLocationErrText;
    private List<Marker> markerList = new ArrayList();
    private Circle mCircle;
    public static final String LOCATION_MARKER_FLAG = "mylocation";
    //点
    private MarkerOptions markerOption;
    private LatLng latlng;
    private Button btn_point;
    private Button btn_line;
    //线
    private Polyline polyline;
    private PolylineOptions mPolylineOptions;
    private LatLng latLngStart;
    private LatLng latLngEnd;
    private Point mPoint;
    private LatLng mLatlng;
    private EditText latView, lngView, xView, yView;
    private int x, y;
    UiSettings uiSettings=aMap.getUiSettings();
     //   UiSettings.setScrollGesturesEnabled(true);
    @Override
    protected View initView() {

        view = View.inflate(mContext, R.layout.basicmap, null);
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 卫星地图模式
        }
        setUpMap();
        BPoint();


        onResume();
        onPause();
        deactivate();
        return view;
    }


    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        mSensorHelper = new SensorEventHelper(getContext());
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();

            if (aMap == null) {
                aMap = mapView.getMap();
                //addPointToMap();// 往地图上添加marker
            }

        }


    }

    @Override
    protected void initData() {
        super.initData();

    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker));// 设置小蓝点的图标

        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER));
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        Toast.makeText(getContext(), "setUpMap", Toast.LENGTH_SHORT).show();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
    }

    public void onPause() {
        super.onPause();
        mapView.onPause();
        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
            mSensorHelper.setCurrentMarker(null);
            mSensorHelper.setCurrentMarker(null);
            mSensorHelper = null;
        }
        mapView.onPause();
        deactivate();
        mFirstFix = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mLocMarker.destroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                Toast.makeText(getContext(), "onLocationChanged", Toast.LENGTH_SHORT).show();

                LatLng location = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                if (!mFirstFix) {
                    mFirstFix = true;
                    //addCircle(location, amapLocation.getAccuracy());//添加定位精度圆(蓝点周围的圈)
                    addMarker(location);//添加定位图标
                    mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
                } else {
                    mCircle.setCenter(location);
                    mCircle.setRadius(amapLocation.getAccuracy());
                    mLocMarker.setPosition(location);
                }
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
                mLocationErrText.setVisibility(View.VISIBLE);
                mLocationErrText.setText(errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getContext());
            mLocationOption = new AMapLocationClientOption();

            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

//蓝点周围的圈
   /* private void addCircle(LatLng latlng, double radius) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        mCircle = aMap.addCircle(options);
    }*/

    private void addMarker(LatLng latlng) {
        if (mLocMarker != null) {
            return;
        }
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.navi_map_gps_locked);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
        Toast.makeText(getContext(), "addMarker", Toast.LENGTH_SHORT).show();
//		BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        mLocMarker = aMap.addMarker(options);
        mLocMarker.setTitle(LOCATION_MARKER_FLAG);
    }

    /*标                                  绘                        点                      */
    /*private void addPointToMap() {
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                Toast.makeText(getContext(), "addPointToMap被点击了", Toast.LENGTH_SHORT).show();
                mLocMarker = aMap.addMarker(new MarkerOptions().position(point));
                markerList.add(mLocMarker);
                if (mLocMarker != null) {
                    return;
                }
                Bitmap bMap = (Bitmap) BitmapFactory.decodeResource(getContext().getResources(), R.drawable.navi_map_gps_locked);
                BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
                MarkerOptions options = new MarkerOptions();
                options.icon(des);
                options.anchor(0.5f, 0.5f);
                options.position(latlng);
                mLocMarker = aMap.addMarker(options);
                mLocMarker.setTitle(LOCATION_MARKER_FLAG);
            }
        });


    }*/

    private void addMarkersToMap() {
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latlng)
                .draggable(true);
        aMap.addMarker(markerOption);
        // 点击事件
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.e("TAG", "onMarkerClick:" + marker.getTitle());
                Toast.makeText(getContext(), "addMarkersToMap", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            /*清空地图上所有已经标注的marker*/

            case R.id.clearMap:
                if (aMap != null) {
                    aMap.clear();
                }
                break;

            /*重新标注所有的marker
             */
            case R.id.resetMap:
                if (aMap != null) {
                    aMap.clear();
                    addMarkersToMap();
                }
                break;
            default:
                break;
        }
    }

    private void BPoint() {
        btn_point = (Button) view.findViewById(R.id.btn_point);
        btn_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng point) {
                        Toast.makeText(getContext(), "addPointToMap被点击了", Toast.LENGTH_SHORT).show();
                        mLocMarker = aMap.addMarker(new MarkerOptions().position(point));
                        markerList.add(mLocMarker);
                        if (mLocMarker != null) {
                            return;
                        }
                        Bitmap bMap = (Bitmap) BitmapFactory.decodeResource(getContext().getResources(), R.drawable.navi_map_gps_locked);
                        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);
                        MarkerOptions options = new MarkerOptions();
                        options.icon(des);
                        options.anchor(0.5f, 0.5f);
                        options.position(latlng);
                        mLocMarker = aMap.addMarker(options);
                        mLocMarker.setTitle(LOCATION_MARKER_FLAG);
                    }
                });
            }
        });


    }

    /*                                线                                         */
    private void BLine() {
        btn_line = (Button) view.findViewById(R.id.btn_line);
        final List<LatLng> mTrackLatlngList = new ArrayList<>();

        btn_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
                    @Override
                    public void onTouch(MotionEvent motionEvent) {
                        if (!aMap.getUiSettings().isScrollGesturesEnabled())
                        //地图不能移动的时候再画线 gustures 手势
                        {
                            switch (motionEvent.getAction()) {
                                case MotionEvent.ACTION_DOWN://start点，转换成坐标点
                                    int x = (int) motionEvent.getX();
                                    int y = (int) motionEvent.getY();
                                    latLngStart = toGeoLocation(x, y);
                                    mTrackLatlngList.add(latLngStart);
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    aMap.showMapText(false);
                                    int x1 = (int) motionEvent.getX();
                                    int y1 = (int) motionEvent.getY();
                                    LatLng latlngMove = toGeoLocation(x1, y1);
                                    mTrackLatlngList.add(latlngMove);
                                    if (polyline == null) {
                                        PolylineOptions mPolylineOptioins = new PolylineOptions().color(Color.RED).addAll(mTrackLatlngList);
                                        polyline = aMap.addPolyline(mPolylineOptions);
                                    } else {
                                        polyline.setPoints(mTrackLatlngList);
                                    }
                                    //画线
                                    break;
                                case MotionEvent.ACTION_UP:
                                    //自动首尾相连
                                    mTrackLatlngList.add(latLngStart);
                                    btn_line.setVisibility(View.GONE);
                                    //btn_cancle.setVisibility(View.VISIBLE);
                                    //isOver=true;
                                    aMap.showMapText(true);
                                    uiSettings.setScrollGesturesEnabled(true);

                                    Toast.makeText(getContext(),"绘制完成",Toast.LENGTH_SHORT).show();
                                    //自动收尾相连
                                    PolylineOptions polylineOptions1=new PolylineOptions().color(Color.RED).addAll(mTrackLatlngList);
                                    aMap.addPolyline(polylineOptions1);
                                    if (polyline!=null){
                                        polyline.remove();
                                        polyline=null;
                                    }
                                    List<LatLng> list = collectData(mTrackLatlngList);
                                    double area = Math.abs(GeometryUtils.calculateAreas3(list));
                                    String areaStr = String.format("%.2f亩", area / 666.6667);
                                    // 计算中心点
                                    LatLng midpoint = GeometryUtils.calculateCenterPoint(mTrackLatlngList);
                                    // 添加面积标签
                                    addAreaLable(areaStr, midpoint);
                                    break;

                            }

                        }

                    }
                });

            }
        });
    }


    private LatLng toGeoLocation(int x, int y) {
        if (AMapUtil.IsEmptyOrNullString(x + "") || AMapUtil.IsEmptyOrNullString(y + "")) {
            return null;
        } else {
            mPoint = new Point(x, y);
            LatLng mLatlng = aMap.getProjection().fromScreenLocation(mPoint);
            if (mLatlng != null) {
                return mLatlng;
            }
            return null;

        }

    }
}

   /* private void addLineToMap() {
        mPolylineOptions = new PolylineOptions();

        aMap.setOnPolylineClickListener(new AMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {


            }});}
            public void addPolylineToMap(){
                mPolylineOptions = new PolylineOptions();
           aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
               @Override
               public void onTouch(MotionEvent motionEvent) {
                 }
           }
                 );}*/













             /*    markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latlng)
                .draggable(true);
        aMap.addMarker(markerOption);*/





/*
// 拖拽事件
        aMap.setOnMarkerDragListener(new AMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Log.e("TAG", "onMarkerDragStart:" + marker.getTitle());
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                Log.e("TAG", "onMarkerDrag:" + marker.getTitle());
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.e("TAG", "onMarkerDragEnd:" + marker.getTitle());
            }
        });
    }
*/

    //设置坐标点
 /*   LatLng point1 = new LatLng(39.92235, 116.380338);
    LatLng point2 = new LatLng(39.947246, 116.414977);*/


    /**
     * 对marker标注点点击响应事件
     */
    /*public boolean onMarkerClick(final Marker marker) {
        if (aMap != null) {
            jumpPoint(marker);
        }
        Toast.makeText(getContext(), "您点击了Marker", Toast.LENGTH_LONG).show();
        return true;
    }
    public void jumpPoint(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        final LatLng markerLatlng = marker.getPosition();
        Point markerPoint = proj.toScreenLocation(markerLatlng);
        markerPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(markerPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * markerLatlng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * markerLatlng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }



    Marker marker;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            *//**
     * 清空地图上所有已经标注的marker
                    *//*
            case R.id.clearMap:
                if (aMap != null) {
                    aMap.clear();
                }
                break;
            *//**
     * 重新标注所有的marker
                    *//*
            case R.id.resetMap:
                if (aMap != null) {
                    aMap.clear();
                    addMarkersToMap();
                }
                break;
            default:
                break;
        }*/

