package com.bik.todolist.model;

public class ResultModel {
    private TaskModel taskModel;
    private ListsModel listsModel;

    public ResultModel() {
    }

    public ResultModel(TaskModel taskModel, ListsModel listsModel) {
        this.taskModel = taskModel;
        this.listsModel = listsModel;
    }

    public TaskModel getTaskModel() {
        return taskModel;
    }

    public void setTaskModel(TaskModel taskModel) {
        this.taskModel = taskModel;
    }

    public ListsModel getListsModel() {
        return listsModel;
    }

    public void setListsModel(ListsModel listsModel) {
        this.listsModel = listsModel;
    }
}
