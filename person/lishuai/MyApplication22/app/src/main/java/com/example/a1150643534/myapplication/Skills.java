package com.example.a1150643534.myapplication;

import android.widget.ImageView;

public class Skills {
    private int id;//区分是第几个技能
    private int[] page;//在第几张图出现
    private ImageView imageView;

    public int[] getPage() {
        return page;
    }

    public void setPage(int[] page) {
        this.page = page;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
