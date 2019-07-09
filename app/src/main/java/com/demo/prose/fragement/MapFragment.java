package com.demo.prose.fragement;


import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

import com.demo.prose.R;

public class MapFragment extends Fragment {

       private static MapFragment fragment=null;
  public static final int POSITION=0;
   private MapView mapView;
 private AMap aMap;
   private View mapLayout;

            public static Fragment newInstance(){
            if(fragment==null){
                   synchronized(MapFragment.class){
                        if(fragment==null){
                               fragment=new MapFragment();
                             }
                      }
                 }
             return fragment;
           }
    @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
             if (mapLayout == null) {
                   Log.i("sys", "MF onCreateView() null");
                   mapLayout = inflater.inflate(R.layout.basicmap, null);
                   mapView = (MapView) mapLayout.findViewById(R.id.map);
                   mapView.onCreate(savedInstanceState);
                   if (aMap == null) {
                         aMap = mapView.getMap();
                      }
             }else {
                if (mapLayout.getParent() != null) {
                    ((ViewGroup) mapLayout.getParent()).removeView(mapLayout);
                       }
                 }
             return mapLayout;
           }

         /*   @Override
    public void onAttach(Activity activity) {
             super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(SyncStateContract.Constants.MAP_FRAGMENT);
           }*/
           @Override
    public void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);

          }

            @Override
    public void onResume() {
             Log.i("sys", "mf onResume");
             super.onResume();
             mapView.onResume();
           }

           /**
  74    * 方法必须重写
  75    * map的生命周期方法
  76    */
            @Override
   public void onPause() {
            Log.i("sys", "mf onPause");
             super.onPause();
             mapView.onPause();
           }

           /**
  85    * 方法必须重写
  86    * map的生命周期方法
  87    */
            @Override
    public void onSaveInstanceState(Bundle outState) {
             Log.i("sys", "mf onSaveInstanceState");
            super.onSaveInstanceState(outState);
             mapView.onSaveInstanceState(outState);
           }

            /**
  96    * 方法必须重写
  97    * map的生命周期方法
  98    */
            @Override
   public void onDestroy() {
             Log.i("sys", "mf onDestroy");
             super.onDestroy();
            mapView.onDestroy();
          }
 }