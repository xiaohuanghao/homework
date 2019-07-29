package com.demo.prose.adapter;

import android.content.Context;
import android.graphics.Picture;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.prose.R;
import com.demo.prose.collect.Pictures;

import java.util.List;

public class PAdapter extends BaseAdapter implements View.OnTouchListener, View.OnFocusChangeListener, View.OnClickListener, View.OnLongClickListener {
    private List<Pictures> data;
    private int selectedEditTextPosition = -1;
    private List<Pictures> mlist;
    private Context mContext;
    /*et_test=describe*/

    public PAdapter() {
    }

    public PAdapter(List<Pictures> mlist, Context mContext) {
        this.mContext = mContext;
        this.mlist = mlist;
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
            /*viewHolder.imageView=(ImageView)convertView.findViewById(R.id.img_show);*/
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
        String describe = mlist.get(position).getDescribe();
        viewHolder.editText.setText(describe);
        viewHolder.editText.setSelection(viewHolder.editText.length());

        convertView.setTag(R.id.item_root, position);//应该在这里让convertView绑定
        convertView.setOnClickListener(this);
        convertView.setOnLongClickListener(this);

        return convertView;
    }
    /*public void updateData(int position,String path){
        Pictures ptest = mlist.get(position);
        ptest.setPath(path);
        ptest.setImageCaptured(true);
        mlist.set(position,ptest);
        notifyDataSetChanged();
    }*/
    private TextWatcher textWatcher = new MyTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (selectedEditTextPosition != -1) {
                Log.w("MyEdiAdapter", "onTextPosition" + selectedEditTextPosition
                );
                Pictures pictures = (Pictures) getItem(selectedEditTextPosition);
                pictures.setDescribe(s.toString());
            }
        }
    };

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == motionEvent.ACTION_UP) {
            EditText editText = (EditText) view;
            selectedEditTextPosition = (int) editText.getTag();
        }
        return false;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        EditText editText = (EditText) view;
        if (hasFocus) {
            editText.addTextChangedListener(textWatcher);

        } else {
            editText.removeTextChangedListener(textWatcher);
        }


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.item_root) {
            int position = (int) view.getTag(R.id.item_root);
            Toast.makeText(mContext,"点击第"+position+"个item",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view.getId() == R.id.item_root) {
            int position = (int) view.getTag(R.id.item_root);
            Toast.makeText(mContext,"点击第"+position+"个item",Toast.LENGTH_SHORT).show();}
        return false;
    }



}
