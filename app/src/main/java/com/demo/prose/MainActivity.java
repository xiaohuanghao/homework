package com.demo.prose;


import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.demo.prose.base.BaseFragment;
import com.demo.prose.fragment.CollectFragment;
import com.demo.prose.fragment.MapaFragment;
import com.demo.prose.fragment.NewsFragment;
import com.demo.prose.fragment.OtherFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private RadioGroup mRg_main;
    private List<BaseFragment> mBaseFragment;

    private int position;
    private BaseFragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化view
        initView();
        //初始化initFragment
        initFragment();
        setListner();
    }

    private void setListner() {
        mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        mRg_main.check(R.id.rb_map);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

            switch (checkedId) {
                case R.id.rb_map:
                    position = 0;
                    break;

                case R.id.rb_collect:
                    position = 1;
                    break;

                case R.id.rb_news:
                    position = 2;
                    break;

                case R.id.rb_other:
                    position = 3;
                    break;

                default:
                    position = 0;
                    break;
            }
            BaseFragment to = getFragment();
            switchFragment(mContent,to);
        }

    }

    /**
     *  @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to 马上要切换到的Fragment，一会要显示*/
    private void switchFragment(BaseFragment from, BaseFragment to) {
        if(from != to){
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if(!to.isAdded()){
                //to没有被添加
                //from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //添加to
                if(to != null){
                    ft.add(R.id.fl_content,to).commit();
                }
            }else{
                //to已经被添加
                // from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //显示to
                if(to != null){
                    ft.show(to).commit();
                }
            }
        }

    }

 private void switchFragment(BaseFragment fragment) {
        //1.得到FragmentManger
        FragmentManager fm = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        //3.替换
        transaction.replace(R.id.fl_content, fragment);
        //4.提交事务
        transaction.commit();
    }

    private BaseFragment getFragment() {
        BaseFragment fragment=mBaseFragment.get(position);
        return fragment;
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new MapaFragment());
        mBaseFragment.add(new CollectFragment());
        mBaseFragment.add(new NewsFragment());
        mBaseFragment.add(new OtherFragment());
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mRg_main = (RadioGroup) findViewById(R.id.rg_main);

    }

}
