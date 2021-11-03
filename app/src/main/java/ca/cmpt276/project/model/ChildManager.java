package ca.cmpt276.project.model;

import java.util.ArrayList;
import java.util.Iterator;

public class ChildManager implements Iterable<Child> {
    private static ChildManager instance = new ChildManager();
    private ArrayList<Child> kids = new ArrayList<Child>();
    private ChildManager() {

    }

    public static ChildManager getInstance() {
        return instance;
    }

    public ArrayList<Child> getKids() {
        if (kids == null) {
            kids = new ArrayList<>();
        }
        return this.kids;
    }

    public void setKids(ArrayList<Child> kidsList) { this.kids = kidsList; }

    public void addKid(Child child) {
        if (kids == null) {
            kids = new ArrayList<>();
        }
        kids.add(child);
    }

    public void removeKid(int index) {
        kids.remove(index);
    }

    @Override
    public Iterator<Child> iterator() {
        return kids.iterator();
    }
}
