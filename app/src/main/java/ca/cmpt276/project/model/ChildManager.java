package ca.cmpt276.project.model;

import java.util.ArrayList;
import java.util.Iterator;

public class ChildManager implements Iterable<Child> {
    private static ChildManager instance;
    private ArrayList<Child> kids = new ArrayList<>();
    private ChildManager() {

    }

    public static ChildManager getInstance() {
        return instance == null ? new ChildManager() : instance;
    }

    public ArrayList<Child> getKids() {
        return kids;
    }

    @Override
    public Iterator<Child> iterator() {
        return kids.iterator();
    }
}
