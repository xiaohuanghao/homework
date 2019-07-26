package com.demo.prose.collect;

import android.content.Context;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.isExternalStorageEmulated;

public class Store extends Fragment {
    //创建文件file,第一个参数传一个缓存地址,第二个传照片名
    public  void p() {
        File file=new File(getExternalStorageDirectory(),"image.jpg");
        if (file.exists()){
            //如果存在,删除文件
            file.delete();

        }
        try{
            //创建这个文件
            file.createNewFile();
            //拿到照片的路径

                Uri uriForFile = (Uri) FileProvider.getUriForFile(getContext(), "com.demo.prose", file);
                //}

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
