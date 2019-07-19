package com.demo.prose.collect;

import android.view.View;
import android.widget.Button;


public class Pictures {
    private int img_show;
    private Button btn_start;

    public Pictures() {
    }

    public Pictures(int img_show, Button btn_start) {
        this.img_show = img_show;
        this.btn_start = btn_start;
    }

    public int getImg_show() {
        return img_show;
    }

    public void setImg_show(int img_show) {
        this.img_show = img_show;
    }

    public View.OnClickListener getBtn_start() {
        return (View.OnClickListener) btn_start;
    }

    public void setBtn_start(Button btn_start) {
        this.btn_start = btn_start;
    }

    @Override
    public String toString() {
        return "Pictures{" +
                "img_show=" + img_show +
                ", btn_start=" + btn_start +
                '}';
    }
}
