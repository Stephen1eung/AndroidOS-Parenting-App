package ca.cmpt276.parentapp.model.Child;

import java.util.ArrayList;
import java.util.LinkedList;

public class QueueManager {
    private static final QueueManager instance = new QueueManager();
    private LinkedList<Child> QueueList = new LinkedList<>();

    private QueueManager() {

    }

    public static QueueManager getInstance() {
        return instance;
    }

    public LinkedList<Child> getQueueList() {
        if (QueueList == null) {
            QueueList = new LinkedList<>();
        }
        return this.QueueList;
    }

    public void addChild(Child child) {
        QueueList.add(child);
    }

    public void removeChild(int index) {
        QueueList.remove(index);
    }
}
