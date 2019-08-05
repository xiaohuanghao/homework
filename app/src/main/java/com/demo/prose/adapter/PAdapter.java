package com.demo.prose.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.prose.MainActivity;
import com.demo.prose.R;
import com.demo.prose.collect.GetSet;
import com.demo.prose.fragment.CollectFragment;

import java.util.List;

public class PAdapter extends BaseAdapter implements View.OnTouchListener, View.OnFocusChangeListener, View.OnClickListener, View.OnLongClickListener {
    private List<GetSet> data;
    private int selectedlabelPosition = -1;
   Context mContext;
    ViewHolder v;

    static class ViewHolder {
        ImageView imageView;
        EditText label;
        ImageButton clickImage, removeImage;
        Button buttonUpload;

       /* public ViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.img_show);
            label = (label) convertView.findViewById(R.id.describe);
        }*/
    }
    /*et_test=describe*/
    public PAdapter() {
    }

    public PAdapter(List<GetSet> getData, Context mContext) {
        this.mContext = mContext;
        this.data = getData;
    }


   

    /* public ViewHolder(View convertView){
         image
     }*/
    //返回集合数据的数量
    @Override
    public int getCount() {
        Log.e("TAG", "getCount()");
       return data.size();
    }

    //listview
    //返回指定下标对应的数据对象
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //返回指定下标所对应的item的view对象
    public View getView(int position,  View convertView, ViewGroup parent) {
       /* View view = convertView;
       
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.collect_item, null);
            v = new ViewHolder(convertView);
            convertView.setTag(v);
            *//*viewHolder.imageView=(ImageView)convertView.findViewById(R.id.img_show);*//*
        } else {
            v = (ViewHolder) convertView.getTag();
        } */
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.collect_item, null);
        } else {
            view = convertView;
        }
        v = new ViewHolder();
        v.imageView = (ImageView) view.findViewById(R.id.show);
        v.clickImage = (ImageButton) view.findViewById(R.id.capture);
        v.removeImage = (ImageButton) view.findViewById(R.id.cancel);
        v.label=view.findViewById(R.id.describe);
        v.label.setOnTouchListener(this);
        v.label.setOnFocusChangeListener(this);
        v.label.setTag(position);
        //set data in listview
    final   GetSet dataSet = (GetSet) data.get(position);
        dataSet.setListItemPosition(position);
        if (!dataSet.isHaveImage()) {
            Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
            v.imageView.setImageBitmap(icon);
        } else {
            v.imageView.setImageBitmap(dataSet.getImage());

        }
        v.label.setText(dataSet.getLabel().toString());
        if (dataSet.isStatus()) {
            v.clickImage.setVisibility(View.VISIBLE);
            v.removeImage.setVisibility(View.GONE);

        } else {
            v.removeImage.setVisibility(View.VISIBLE);
            v.clickImage.setVisibility(View.GONE);
        }
        v.clickImage.setFocusable(false);
        v.removeImage.setFocusable(false);
        v.clickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mContext.startActivity(intent);
               // mContext.startActivityForResult(intent, 100);

                //call parent method of activity to click image
                //((CollectFragment)mContext).captureImage(dataSet.getListItemPosition(),dataSet.getLabel().toString());
            }
        });
        v.removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataSet.setStatus(true);
                dataSet.setHaveImage(false);
                notifyDataSetChanged();
            }
        });
        if (selectedlabelPosition != -1 && position == selectedlabelPosition) {
            v.label.requestFocus();
        } else {
            v.label.clearFocus();
        }
       String text=data.get(position).getLabel();
        v.label.setText(text);
        v.label.setSelection(v.label.length());

        view.setTag(R.id.item_root, position);//应该在这里让convertView绑定
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);

        return view;
    }
    public void setImageInItem(int position, Bitmap imageSrc, String imagePath) {
        GetSet dataSet = (GetSet) data.get(position);
        dataSet.setImage(imageSrc);
        dataSet.setStatus(false);
        dataSet.setHaveImage(true);
        notifyDataSetChanged();
    }

    /*public void updateData(int position,String path){
        GetSet ptest = data.get(position);
        ptest.setPath(path);
        ptest.setImageCaptured(true);
        data.set(position,ptest);
        notifyDataSetChanged();
    }*/
    //ListView套用EditText完美解决方案
    // https://blog.csdn.net/qiantanlong/article/details/77839925
    private TextWatcher textWatcher = new MyTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (selectedlabelPosition != -1) {
                Log.w("MyEdiAdapter", "onTextPosition" + selectedlabelPosition
                );
                GetSet getSet = (GetSet) getItem(selectedlabelPosition);
                getSet.setLabel(s.toString());
            }
        }
    };

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == motionEvent.ACTION_UP) {
            EditText label = (EditText) view;
            selectedlabelPosition = (int) label.getTag();
        }
        return false;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        EditText label = (EditText) view;
        if (hasFocus) {
            label.addTextChangedListener(textWatcher);

        } else {
            label.removeTextChangedListener(textWatcher);
        }


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.item_root) {
            int position = (int) view.getTag(R.id.item_root);
            Toast.makeText(mContext,"点击第"+(position+1)+"个item",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view.getId() == R.id.item_root) {
            int position = (int) view.getTag(R.id.item_root);
            Toast.makeText(mContext,"点击第"+(position+1)+"个item",Toast.LENGTH_SHORT).show();}
        return false;
    }



}
