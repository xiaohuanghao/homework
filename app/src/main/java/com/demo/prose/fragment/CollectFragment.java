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


import com.demo.prose.R;
import com.demo.prose.adapter.PAdapter;
import com.demo.prose.base.BaseFragment;
import com.demo.prose.collect.GetSet;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

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
        view=View.inflate(mContext, R.layout.collect,null);
        listView=(ListView)view.findViewById(R.id.captureList);

        //准备数据
        getSets = new ArrayList<GetSet>();
        imageFor = getResources().getStringArray(R.array.imageFor);
        for (int i = 0; i < 3; i++) {
            GetSet inflate = new GetSet();
            //Global Values
            inflate.setUid(String.valueOf(i));
            //inflate.setLabel();
            inflate.setHaveImage(false);
            inflate.setSubtext(imageFor[i]);
            inflate.setStatus(true);

            getSets.add(inflate);

        }
        pAdapter = new PAdapter(getSets, getContext());
        listView.setAdapter(pAdapter);

        return view;
    }
    /**
     * Capture Image and save into database
     */


    /**
     * Set capture image to database and set to image preview
     *
     * @param data
     */
    private void onCaptureImageResult(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        //call this method to get the uri from the bitmap
        Uri tempUri = getImageUri(getContext(), imageBitmap, imageTempName);
        String picturePath = getRealPathFromURI(tempUri);
       pAdapter.setImageInItem(position, imageBitmap, picturePath);


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
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    public Uri getImageUri(Context inContext, Bitmap inImage, String imageName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, imageName, null);
        return Uri.parse(path);
    }
    public Bitmap convertSrcToBitMap(String imageSrc){
        Bitmap myBitmap=null;
        File imgFile=new File(imageSrc);
        if(imgFile.exists()){
            myBitmap= BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return myBitmap;
    }
}


