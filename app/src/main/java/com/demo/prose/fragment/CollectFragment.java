package com.demo.prose.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.core.content.FileProvider;

import com.demo.prose.R;
import com.demo.prose.adapter.PAdapter;
import com.demo.prose.base.BaseFragment;
import com.demo.prose.collect.Pictures;

import java.io.ByteArrayOutputStream;
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
    private EditText describe;
    //定义一个保存图片的File变量
    private File currentImageFile =null;

    private ListView listView;

    private Pictures pictures;
    //https://stackoverflow.com/questions/19229550/listview-shows-recent-image-taken-from-camera?r=SearchResults
    int take_image;
    static Bitmap thumbnail;

    @Override
    protected View initView() {
        view = inflate(mContext, R.layout.collect2, null);

        bindViews();
        listView=(ListView) view.findViewById(R.id.listView);

        //准备数据?
       List<Pictures>list=new ArrayList<>();
       Pictures ptest=new Pictures();
       for(int i=0;i<50;i++){
       ptest.setDescribe("第"+i+"项");
       }

    //baseAdapter创立?l
        PAdapter adapter = new PAdapter(list,getContext());
        //设置Adapter
        listView.setAdapter(adapter);
        return view;

    }

    private void bindViews() {
        //点击监听,拍摄
        img_show = (ImageView) view.findViewById(R.id.img_show);
        btn_start = (Button) view.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
//在按钮点击事件出写上这些东西,这些实在sd卡创建图片文件的

            public void onClick(View view) {
                File dir = new File(Environment.getExternalStorageDirectory(), "pictures");
                if (dir.exists()) {
                    dir.delete();
                }
                currentImageFile = new File(dir, System.currentTimeMillis() + ".jpg");
                if (!currentImageFile.exists()) {
                    try {
                        currentImageFile.createNewFile();
                            //拿到照片路径
                            Uri uriForFile = (Uri) FileProvider.getUriForFile(getContext(), "com.demo.prose.", dir);
                       //启动相机
                        Intent it = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        it.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));
                        startActivityForResult(it, take_image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
    }

    //onActivityResult:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity.DEFAULT_KEYS_DIALER) {
            img_show.setImageURI(Uri.fromFile(currentImageFile));

            if(requestCode==take_image){
                //get image
                thumbnail = (Bitmap) data.getExtras().get("data");



                BitmapFactory.Options factoryOptions = new BitmapFactory.Options();

                factoryOptions.inJustDecodeBounds = true;




                int imageWidth = factoryOptions.inDensity=50;
                int imageHeight = factoryOptions.inDensity=50;

                image2 = Bitmap.createScaledBitmap(thumbnail, imageWidth , imageHeight, true);

                //////listview work


                listviewattachment.setItemsCanFocus(true);
               adapter = new PAdapter();
                ListItem listItem = new ListItem();
                myItems.add(listItem);

                listviewattachment.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();

                ////////////////////end of listview work

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();


                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                //encode image
                byte[] b = bytes.toByteArray();
                encodedImageString = Base64.encodeToString(b, Base64.DEFAULT);

            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
/*                                adapter                                         */


   /*class PAdapter extends BaseAdapter {

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

        public  void  add(ContactsContract.RawContacts.Data data){}
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {

                convertView = inflate(getContext(), R.layout.collect_item, null);
            }
            Pictures pictures = data.get(position);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.img_show);
            EditText editText=(EditText)convertView.findViewById(R.id.describe);
            //设置数据
            imageView.setImageResource(pictures.getImg_show());
            editText.setText(getText());
            return convertView;
        }
    }*/
}


