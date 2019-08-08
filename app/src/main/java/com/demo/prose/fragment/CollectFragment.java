package com.demo.prose.fragment;

import android.app.Activity;
import android.app.DirectAction;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
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
import android.widget.ListView;
import android.widget.Toast;


import com.demo.prose.R;
import com.demo.prose.base.BaseFragment;
import com.demo.prose.collect.GetSet;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
https://ask.csdn.net/questions/372517
* https://www.cnblogs.com/wdht/p/6119487.html
* https://trinitytuts.com/capturing-images-from-camera-and-setting-into-listview-in-android/
* */
public class CollectFragment extends BaseFragment {

    ArrayList<GetSet> getSets;
    ListView listView;
    ImageButton  imageButton;
    EditText label;
    private File currentImageFile = null;
   ImageView img_show;
    //Temp save listItem position
    int position;
    int imageCount;
    String imageTempName="name";
    String[] imageFor;
    ImageButton ibCapture;
    PAdapter pAdapter = new PAdapter(getSets, getActivity());
    List<GetSet> list = new ArrayList<GetSet>();

    @Override
    protected View initView() {
        view = View.inflate(mContext, R.layout.collect, null);
        listView = (ListView) view.findViewById(R.id.captureList);
        ibCapture=(ImageButton)view.findViewById(R.id.capture);
        img_show=(ImageView)view.findViewById(R.id.show);
        label=(EditText) view.findViewById(R.id.describe);
        //准备数据
        List<GetSet> list = new ArrayList<GetSet>();
        getSets = new ArrayList<GetSet>();
        PAdapter pAdapter = new PAdapter(getSets,getActivity());

      addData();

        init();
        pAdapter.notifyDataSetChanged();
        Button1();
        return view;
    }


    private void addData() {
        //准备数据

        getSets = new ArrayList<GetSet>();
        imageFor = getResources().getStringArray(R.array.imageFor);
        for (int i = 0; i < 3; i++) {
            GetSet inflate = new GetSet();
            //Global Values
            inflate.setUid(String.valueOf(i));
            //  inflate.setLabel(getText());
            inflate.setHaveImage(false);
            inflate.setSubtext(imageFor[i]);
            inflate.setStatus(true);

            getSets.add(inflate);
            Toast.makeText(getContext(), "addData", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {

        for(int i=0;i<list.size();i++){
            Map<ImageView,EditText>item=new HashMap<ImageView, EditText>();
            item.put(img_show,label);
        }
                list.add(new GetSet());

        PAdapter pAdapter = new PAdapter(getSets, getActivity());
        listView.setAdapter(pAdapter);
    }
public void Button1(){
        imageButton=(ImageButton)view.findViewById(R.id.capture);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file=new File(Environment.getExternalStorageDirectory(),"picture");
                if(file.exists()){
                    file.delete();
                }
                currentImageFile =new File(file,System.currentTimeMillis()+"jpg");
                if (!currentImageFile.exists()){
                    try {
                        currentImageFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));
                startActivityForResult(intent, 100);
            }
        });
    }


    /**
     * Capture Image and save into database
     */
    public void captureImage(int pos, String imageName) {
        position = pos;
        imageTempName = imageName;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
        Toast.makeText(getContext(), "capture", Toast.LENGTH_SHORT).show();
    }

    /**
     * Set capture image to database and set to image preview
     *
     * @param data
     */
    public void onCaptureImageResult(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        //call this method to get the uri from the bitmap
        Uri tempUri = getImageUri(mContext.getApplicationContext(), imageBitmap, imageTempName);
        String picturePath = getRealPathFromURI(tempUri);
        pAdapter.setImageInItem(position, imageBitmap, picturePath);
        Toast.makeText(getContext(), "onCaptureImageResult", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 100) {
                onCaptureImageResult(data);
            }
        }*/
        if (requestCode == Activity.DEFAULT_KEYS_DIALER) {
//            Bundle bundle = data.getExtras();
//            Bitmap bitmap = (Bitmap) bundle.get("data");
//            img_show.setImageBitmap(bitmap);

            img_show.setImageURI(Uri.fromFile(currentImageFile));
    }}

    private String getRealPathFromURI(Uri uri) {
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage, String imageName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, imageName, null);
        return Uri.parse(path);
    }

    public Bitmap convertSrcToBitMap(String imageSrc) {
        Bitmap myBitmap = null;
        File imgFile = new File(imageSrc);
        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return myBitmap;
    }






    /*-----------------------------------------------------*/



    public class PAdapter extends BaseAdapter implements View.OnTouchListener, View.OnFocusChangeListener, View.OnClickListener, View.OnLongClickListener {
        List<GetSet> data;
        private int selectedlabelPosition = -1;
        Context mContext;
       ViewHolder viewHolder;

         class ViewHolder {
            ImageView imageView;
            EditText label;
            ImageButton clickImage, removeImage;
            Button buttonUpload;
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
            View view = convertView;
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = LayoutInflater.from(mContext).inflate(R.layout.collect_item, null);
                viewHolder = new PAdapter.ViewHolder();
                convertView.setTag(viewHolder);
                //*viewHolder.imageView=(ImageView)convertView.findViewById(R.id.img_show);*//*
            } else {
                viewHolder = (PAdapter.ViewHolder) convertView.getTag();
            }
        if (view == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.collect_item, null);
        } else {
            view = convertView;
        }
            viewHolder = new PAdapter.ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.show);
            viewHolder.clickImage = (ImageButton) view.findViewById(R.id.capture);
            viewHolder.removeImage = (ImageButton) view.findViewById(R.id.cancel);
            viewHolder.label=view.findViewById(R.id.describe);
            viewHolder.label.setOnTouchListener(this);
            viewHolder.label.setOnFocusChangeListener(this);
            viewHolder.label.setTag(position);
            //set data in listview
            final GetSet dataSet = (GetSet) data.get(position);
            dataSet.setListItemPosition(position);
            if (!dataSet.isHaveImage()) {
                Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
                viewHolder.imageView.setImageBitmap(icon);
            } else {
                viewHolder.imageView.setImageBitmap(dataSet.getImage());

            }
            viewHolder.label.setText(dataSet.getLabel().toString());
            if (dataSet.isStatus()) {
                viewHolder.clickImage.setVisibility(View.VISIBLE);
                viewHolder.removeImage.setVisibility(View.GONE);

            } else {
                viewHolder.removeImage.setVisibility(View.VISIBLE);
                viewHolder.clickImage.setVisibility(View.GONE);
            }
            viewHolder.clickImage.setFocusable(false);
            viewHolder.removeImage.setFocusable(false);
            viewHolder.clickImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mContext.startActivity(intent);
                    // mContext.startActivityForResult(intent, 100);

                    //call parent method of activity to click image
                    //((CollectFragment)mContext).captureImage(dataSet.getListItemPosition(),dataSet.getLabel().toString());
                }
            });
            viewHolder.removeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataSet.setStatus(true);
                    dataSet.setHaveImage(false);
                    notifyDataSetChanged();
                }
            });
            if (selectedlabelPosition != -1 && position == selectedlabelPosition) {
                viewHolder.label.requestFocus();
            } else {
                viewHolder.label.clearFocus();
            }
            String text=data.get(position).getLabel();
            viewHolder.label.setText(text);
            viewHolder.label.setSelection(viewHolder.label.length());

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
    /*   TextWatcher*/
    class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

}


