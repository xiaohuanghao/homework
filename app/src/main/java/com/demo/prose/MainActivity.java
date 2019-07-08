package com.demo.prose;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.demo.prose.fragement.BasicMapFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup mRg_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                startActivity(new Intent(this, BasicMapFragment.class));
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
