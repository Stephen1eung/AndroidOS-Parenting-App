package ca.cmpt276.parentapp.model.Tasks;

import ca.cmpt276.parentapp.model.Child.Child;
import ca.cmpt276.parentapp.model.Child.ChildManager;

public class Task {
    private String taskDesc;
    private int index;
    public Task(String taskDesc) {
        this.index = 0;
        this.taskDesc = taskDesc;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public int getIndex() {
        return index;
    }

    public void taskDone() {
        this.index += 1;
    }

    public Child currChild() {
        ChildManager childManager = ChildManager.getInstance();
        return childManager.getChildArrayList().get(index % childManager.getChildArrayList().size());
    }

    @Override
    public String toString() {
        ChildManager childManager = ChildManager.getInstance();
        Child childTurn = childManager.getChildArrayList().get(index % childManager.getChildArrayList().size());
        return "\nTask: " + taskDesc + "\n" +
                "Whose Turn: " + childTurn.getName();
    }
}