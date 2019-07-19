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
    private ImageView img_show;
    private Button btn_start;
    //定义一个保存图片的File变量
    private File currentImageFele = null;

    private ListView listView;
    private List<Pictures> data;

    @Override
    protected View initView() {
        View view = inflate(mContext, R.layout.collect, null);

        img_show = (ImageView) view.findViewById(R.id.img_show);
        btn_start = (Button) view.findViewById(R.id.btn_start);
        bindViews();
        return view;

    }

    private void bindViews() {
       /* img_show = (ImageView) view.findViewById(R.id.img_show);
        btn_start = (Button) view.findViewById(R.id.btn_start);*/
        btn_start.setOnClickListener(new View.OnClickListener() {
//在按钮点击事件出写上这些东西,这些实在sd卡创建图片文件的

            public void onClick(View view) {
                File dir = new File(Environment.getExternalStorageDirectory(), "pictures");
                if (dir.exists()) {
                    dir.mkdir();
                }
                currentImageFele = new File(dir, System.currentTimeMillis() + ".jpg");
                if (!currentImageFele.exists()) {
                    try {
                        currentImageFele.createNewFile();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFele));
                startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);

            }
        });
    }

    //onActivityResult:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity.DEFAULT_KEYS_DIALER) {
            img_show.setImageURI(Uri.fromFile(currentImageFele));
        }


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView=(R.layout.activity_main);
        //*lv_main = view.findViewById(R.id.lv_main);*//*
        listView=(ListView) view.findViewById(R.id.collect);//需要把layout改成listview
        //准备数据?
        data = new ArrayList<Pictures>();
        data.add(new Pictures(R.id.img_show, btn_start));
        //baseAdapter创立?l
        PAdapter adapter = new PAdapter();
        //设置Adapter
        listView.setAdapter(adapter);
    }

    class PAdapter extends BaseAdapter {
        //返回集合数据的数量
        @Override
        public int getCount() {
            Log.e("TAG", "getCount()");
            return data.size();
        }

        //返回指定下标对应的数据对象
        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {

                convertView = View.inflate(getContext(), R.layout.collect_item, null);
            }
            Pictures pictures = data.get(position);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.img_show);
            Button button = (Button) convertView.findViewById(R.id.btn_start);
            //设置数据
            imageView.setImageResource(pictures.getImg_show());
            button.setOnClickListener(pictures.getBtn_start());
            return convertView;
        }
    }
}


