package com.demo.prose.collect;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class GetSet {

    EditText label;
    Bitmap image;
    String subtext;
    boolean status;
    int listItemPosition;
    String imageSrc;
    private String uid;
    boolean haveImage;
    public GetSet() {
    }

    public GetSet(EditText label, Bitmap image) {
        this.label = label;
        this.image = image;

    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getListItemPosition() {
        return listItemPosition;
    }

    public void setListItemPosition(int listItemPosition) {
        this.listItemPosition = listItemPosition;
    }

    public boolean isHaveImage() {
        return haveImage;
    }

    public void setHaveImage(boolean haveImage) {
        this.haveImage = haveImage;
    }

    public EditText getLabel() {
        return label;
    }

    public void setLabel(EditText label) {
        this.label = label;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pictures{" +
                "label='" + label + '\'' +
                ", image=" + image +
                ", subtext='" + subtext + '\'' +
                ", status=" + status +
                '}';
    }


}
