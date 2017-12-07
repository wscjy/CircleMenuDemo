package com.example.cjy.circlemenudemo;

/**
 * Created by CJY on 2017/12/6.
 */

public class MenuItem {
    public int mImageId;
    public String mTitle;
    public MenuItem(String title, int imageId) {
        this.mTitle = title;
        this.mImageId = imageId;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
