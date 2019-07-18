package com.demo.prose.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.demo.prose.R;
import com.demo.prose.base.BaseFragment;
import com.demo.prose.collect.Pictures;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.inflate;

/*
https://ask.csdn.net/questions/372517
* https://www.cnblogs.com/wdht/p/6119487.html
* */
public class CollectFragment extends BaseFragment {
    private ImageView img_show;
    private Button btn_start;
    //定义一个保存图片的File变量
    private File currentImageFile = null;

    ListView lv_main;
    List<Pictures> data;
    private List<Pictures> picturesList = new ArrayList<Pictures>();

    @Override
    protected View initView() {
        View view = inflate(mContext, R.layout.collect_item, null);
        img_show = (ImageView) view.findViewById(R.id.img_show);
        btn_start = (Button) view.findViewById(R.id.btn_start);
        bindViews();
        return view;
    }

    private void bindViews() {

       /* img_show = (ImageView) findViewById(R.id.img_show);
        btn_start = (Button) findViewById(R.id.btn_start);*/
        //调用相机拍摄后,存储照片
        btn_start.setOnClickListener(new View.OnClickListener() {
//在按钮点击事件出写上这些东西,这些实在sd卡创建图片文件的

            public void onClick(View view) {
                File dir = new File(Environment.getExternalStorageDirectory(), "pictures");
                if (dir.exists()) {
                    dir.mkdir();
                }
                currentImageFile = new File(dir, System.currentTimeMillis() + ".jpg");
                if (!currentImageFile.exists()) {
                    try {
                        currentImageFile.createNewFile();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));
                startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);

            }
        });
    }

    //onActivityResult:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity.DEFAULT_KEYS_DIALER) {
            img_show.setImageURI(Uri.fromFile(currentImageFile));
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView=(R.layout.activity_main);

        //准备数据?
        data = new ArrayList<Pictures>();
        data.add(new Pictures());
        //baseAdapter创立?
        lv_main = (ListView) view.findViewById(R.id.lv_main);
    }
}
