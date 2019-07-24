package com.demo.prose.collect;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Pictures {
    private int img_show;
    private EditText describe;

    public Pictures(int img_show, EditText describe) {
        this.img_show = img_show;
        this.describe = describe;
    }

    public Pictures() {
    }

    public int getImg_show() {
        return img_show;
    }

    public void setImg_show(int img_show) {
        this.img_show = img_show;
    }

    public EditText getDescribe() {
        return describe;
    }

    public void setDescribe(EditText describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "Pictures{" +
                "img_show=" + img_show +
                ", describe=" + describe +
                '}';
    }
}
