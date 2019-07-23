package com.demo.prose.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
    //item
    private ImageView img_show;
    private Button btn_start;
    //定义一个保存图片的File变量
    private File currentImageFile =null;

    private ListView listView;
    private List<Pictures> data;

    @Override
    protected View initView() {
        view = inflate(mContext, R.layout.collect2, null);
        img_show = (ImageView) view.findViewById(R.id.img_show);
        btn_start = (Button) view.findViewById(R.id.btn_start);
        bindViews();
        listView=(ListView) view.findViewById(R.id.listView);
        //准备数据?
        data = new ArrayList<Pictures>();
        data.add(new Pictures(R.id.img_show, btn_start));
        //baseAdapter创立?l
        PAdapter adapter = new PAdapter();
        //设置Adapter
        listView.setAdapter(adapter);
        return view;

    }

    private void bindViews() {
        //点击监听,拍摄
       /* img_show = (ImageView) view.findViewById(R.id.img_show);
        btn_start = (Button) view.findViewById(R.id.btn_start);*/
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

    }

    class PAdapter extends BaseAdapter {
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
            return 0;
        }
        //返回指定下标所对应的item的view对象
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {

                convertView = inflate(getContext(), R.layout.collect_item, null);
            }
            Pictures pictures = data.get(position);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.img_show);
            Button button = (Button) convertView.findViewById(R.id.btn_start);
            //设置数据
            imageView.setImageResource(pictures.getImg_show());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File dir = new File(Environment.getExternalStorageDirectory(),"pictures");
                    if(dir.exists()){
                        dir.mkdirs();
                    }
                    currentImageFile = new File(dir,System.currentTimeMillis() + ".jpg");
                    if(!currentImageFile.exists()){
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
            return convertView;
        }
    }
}


