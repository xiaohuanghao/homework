package com.demo.prose;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.amap.api.maps2d.MapFragment;
import com.demo.prose.base.BaseFragment;
import com.demo.prose.fragement.CollectFragment;
import com.demo.prose.fragement.MapaFragment;
import com.demo.prose.fragement.NewsFragment;
import com.demo.prose.fragement.OtherFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup mRg_main;
    private List<BaseFragment > mBaseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化view
        initView();
        //初始化initFragment
        initFragment();


    }

    private void initFragment() {
        mBaseFragment=new ArrayList<>();
        mBaseFragment.add(new MapaFragment());
        mBaseFragment.add(new CollectFragment());
        mBaseFragment.add(new NewsFragment());
        mBaseFragment.add(new OtherFragment());
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        findViewById(R.id.rb_map).setOnClickListener(this);
        findViewById(R.id.rb_collect).setOnClickListener(this);
        findViewById(R.id.rb_news).setOnClickListener(this);
        findViewById(R.id.rb_other).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_map:
                startActivity(new Intent(this, MapaFragment.class));
                break;

            case R.id.rb_collect:
                break;

            case R.id.rb_news:
                break;

            case R.id.rb_other:
                break;

            default:
                break;
        }
    }
}
