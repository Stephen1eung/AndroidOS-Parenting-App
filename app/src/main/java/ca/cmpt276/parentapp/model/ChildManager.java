package ca.cmpt276.parentapp.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;

public class ChildManager implements Iterable<Child> {
    private static final ChildManager instance = new ChildManager();
    private ArrayList<Child> childArrayList = new ArrayList<>();

    private ChildManager() {

    }

    public static ChildManager getInstance() {
        return instance;
    }

    public ArrayList<Child> getChildArrayList() {
        if (childArrayList == null) {
            childArrayList = new ArrayList<>();
        }
        return this.childArrayList;
    }

    public void setChild(ArrayList<Child> kidsList) {
        this.childArrayList = kidsList;
    }

    public void addChild(Child child) {
        if (childArrayList == null) {
            childArrayList = new ArrayList<>();
        }
        childArrayList.add(child);
    }

    public void removeChild(int index) {
        childArrayList.remove(index);
    }

    @NonNull
    @Override
    public Iterator<Child> iterator() {
        return childArrayList.iterator();
    }
}
