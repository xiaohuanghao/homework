package com.demo.prose.adapter;

import android.content.Context;
import android.graphics.Picture;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.demo.prose.R;
import com.demo.prose.collect.Pictures;

import java.util.List;

public class PAdapter extends BaseAdapter implements View.OnTouchListener, View.OnFocusChangeListener {
    private List<Pictures> data;
    private int selectedEditTextPosition = -1;
    private List<Pictures> mlist;
    private Context mContext;

    public PAdapter(List<Pictures> mlist, Context mContext) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    static class ViewHolder {
        public ImageView imageView;
        public EditText editText;

        public ViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.img_show);
            editText = (EditText) convertView.findViewById(R.id.describe);
        }
    }

    /* public ViewHolder(View convertView){
         image
     }*/
    //返回集合数据的数量
    @Override
    public int getCount() {
        Log.e("TAG", "getCount()");
        return mlist.size();
    }

    //listview
    //返回指定下标对应的数据对象
    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //返回指定下标所对应的item的view对象
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.collect_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.editText.setOnTouchListener(this);
        viewHolder.editText.setOnFocusChangeListener(this);
        viewHolder.editText.setTag(position);

        if (selectedEditTextPosition != -1 && position == selectedEditTextPosition) {
            viewHolder.editText.requestFocus();
        } else {
            viewHolder.editText.clearFocus();
        }
        return convertView;
    }
}