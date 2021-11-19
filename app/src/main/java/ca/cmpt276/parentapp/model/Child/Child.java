package ca.cmpt276.parentapp.model.Child;

import androidx.annotation.NonNull;

public class Child {
    private String name;

    public Child(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Child's Name: " + name;
    }
}
