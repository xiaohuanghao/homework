package com.demo.prose.collect;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;


public class GetSet {

    String label;
    Bitmap image;
    String subtext;
    boolean status;
    int listItemPosition;
    String imageSrc;
    private String uid;
    boolean haveImage;
    public GetSet() {
    }

    public GetSet(String label, Bitmap image) {
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
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
        return "GetSet{" +
                "label=" + label +
                ", image=" + image +
                ", subtext='" + subtext + '\'' +
                ", status=" + status +
                ", listItemPosition=" + listItemPosition +
                ", imageSrc='" + imageSrc + '\'' +
                ", uid='" + uid + '\'' +
                ", haveImage=" + haveImage +
                '}';
    }
}
