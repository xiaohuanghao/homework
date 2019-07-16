package com.demo.prose.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.demo.prose.R;
import com.demo.prose.base.BaseFragment;

import java.io.File;
import java.io.IOException;

import static android.view.View.inflate;

public class CollectFragment extends BaseFragment {
    private ImageView img_show;
    private Button btn_start;
    //定义一个保存图片的File变量
    private File currentImageFele=null;

    @Override
    protected View initView() {
        View view=inflate(mContext, R.layout.collect, null);
        bindViews();
        return view;

    }
    private void bindViews() {
        img_show = (ImageView) findViewById(R.id.img_show);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
//在按钮点击事件出写上这些东西,这些实在sd卡创建图片文件的

    public void onClick(View view){
        File dir=new File(Environment.getExternalStorageDirectory(),"pictures");
        if(dir.exists()){
            dir.mkdir();
        }
        currentImageFele=new File(dir,System.currentTimeMillis()+".jpg");
        if (!currentImageFele.exists()){
            try{currentImageFele.createNewFile();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Intent it=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFele));
        startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);

    }
    });
    }
    //onActivityResult:
    @Override
    public void  onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==Activity.DEFAULT_KEYS_DIALER){
            img_show.setImageURI(Uri.fromFile(currentImageFele));
        }

    }

}

