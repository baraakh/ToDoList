package com.bik.todolist.model;

public class ListsModel {
    private String listName;
    private long listcount;

    public ListsModel() {
    }

    public ListsModel(String listName, long listcount) {
        this.listName = listName;
        this.listcount = listcount;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public long getListcount() {
        return listcount;
    }

    public void setListcount(long listcount) {
        this.listcount = listcount;
    }
}
