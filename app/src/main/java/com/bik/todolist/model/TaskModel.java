package com.bik.todolist.model;

public class TaskModel {

    private String taskName;
    private boolean isChecked;
    private Object timeStamp;
    private String desc;

    public TaskModel() {
    }

    public TaskModel(Object timeStamp, String desc) {
        this.timeStamp = timeStamp;
        this.desc = desc;
    }

    public TaskModel(String taskName, boolean isChecked, Object timeStamp, String desc) {
        this.taskName = taskName;
        this.isChecked = isChecked;
        this.timeStamp = timeStamp;
        this.desc = desc;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
