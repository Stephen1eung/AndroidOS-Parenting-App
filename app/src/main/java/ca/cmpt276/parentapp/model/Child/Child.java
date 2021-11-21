package ca.cmpt276.parentapp.model.Child;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class Child {
    private String name;
    private Bitmap img;
    public Child(String name, Bitmap bitmap) {
        this.name = name;
        this.img = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    @NonNull
    @Override
    public String toString() {
        return "Child's Name: " + name;
    }
}
