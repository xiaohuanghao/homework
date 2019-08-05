package com.demo.prose.fragment;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.demo.prose.R;
import com.demo.prose.adapter.PAdapter;
import com.demo.prose.base.BaseFragment;
import com.demo.prose.collect.GetSet;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.inflate;

/*
https://ask.csdn.net/questions/372517
* https://www.cnblogs.com/wdht/p/6119487.html
* https://trinitytuts.com/capturing-images-from-camera-and-setting-into-listview-in-android/
* */
public class CollectFragment extends BaseFragment {
    PAdapter pAdapter;
    ArrayList<GetSet> getSets;
    ListView listView;

    //Temp save listItem position
    int position;
    int imageCount;
    String imageTempName;
    String[] imageFor;

    @Override
    protected View initView() {
        view = View.inflate(mContext, R.layout.collect, null);
        listView = (ListView) view.findViewById(R.id.captureList);

        //准备数据
      List<GetSet>list=new ArrayList<>();
        getSets = new ArrayList<GetSet>();
        imageFor = getResources().getStringArray(R.array.imageFor);
        for (int i = 0; i <3 ; i++) {
            GetSet inflate = new GetSet();
            //Global Values
            inflate.setUid(String.valueOf(i));
            inflate.setLabel("");//不知道参数填什么,先填了一个肯定能运行的getContext().toString()
            inflate.setHaveImage(false);
            inflate.setSubtext(imageFor[i]);
            inflate.setStatus(true);
            getSets.add(inflate);
        }
        init();


        return view;
    }

    private void addData() {
        //准备数据
        List<GetSet>list=new ArrayList<>();
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
        PAdapter pAdapter = new PAdapter(getSets, getActivity());
        listView.setAdapter(pAdapter);
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
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 100) {
                onCaptureImageResult(data);
            }
        }
    }

    private String getRealPathFromURI(Uri uri) {
        Cursor cursor =mContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage, String imageName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 200, bytes);
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
}


