package com.demo.prose.collect;

import android.widget.Button;
import android.widget.ImageView;

public class Pictures {
    private ImageView img_show;
    private Button btn_start;

    public Pictures(ImageView img_show, Button btn_start) {
        this.img_show = img_show;
        this.btn_start = btn_start;
    }

    public Pictures() {
    }

    public ImageView getImg_show() {
        return img_show;
    }

    public void setImg_show(ImageView img_show) {
        this.img_show = img_show;
    }

    public Button getBtn_start() {
        return btn_start;
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
