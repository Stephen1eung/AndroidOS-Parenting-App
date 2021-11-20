package ca.cmpt276.parentapp.model.Tasks;

public class Task {
    private String taskDesc;

    public Task(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    @Override
    public String toString() {
        return "Task: " + taskDesc + "\n";
    }
}
