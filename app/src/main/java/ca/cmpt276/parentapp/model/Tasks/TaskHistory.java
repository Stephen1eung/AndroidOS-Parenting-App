package ca.cmpt276.parentapp.model.Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ca.cmpt276.parentapp.model.Child.Child;
import ca.cmpt276.parentapp.model.Child.ChildManager;

public class TaskHistory {
    private String localDateTime;
    private String taskDesc;
    private final int childIndex;

    public TaskHistory(int childIndex, String taskDesc, String localDateTime) {
        this.localDateTime = localDateTime;
        this.childIndex = childIndex;
        this.taskDesc = taskDesc;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public Child currChild() {
        ChildManager childManager = ChildManager.getInstance();
        if (childManager.getChildArrayList().size() > 0) {
            return childManager.getChildArrayList().get(childIndex);
        }
        return null;
    }

    @Override
    public String toString() {
        ChildManager childManager = ChildManager.getInstance();
        if (childManager.getChildArrayList().size() > 0) {
            Child childTurn = childManager.getChildArrayList().get(childIndex % childManager.getChildArrayList().size());

            return "Date: "+ localDateTime + "\nTask: " + taskDesc + "\n" +
                    "Whose Turn: " + childTurn.getName();
        } else {
            return "\nTask" + taskDesc + "\n" +
                    "Whose Turn:\n";
        }
    }
}
