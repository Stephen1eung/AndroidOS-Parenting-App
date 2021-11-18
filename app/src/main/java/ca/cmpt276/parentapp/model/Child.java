package ca.cmpt276.parentapp.model;

import android.net.Uri;

public class Child {
    private String name;
    private String imgPath;
    public Child(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "Child's Name: " + name;
    }
}
