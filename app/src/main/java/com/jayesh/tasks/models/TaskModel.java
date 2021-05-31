package com.jayesh.tasks.models;

import com.jayesh.tasks.utils.TaskDbHelper;

public class TaskModel {
    private int id;
    private String task;
    private int userId;
    private TaskDbHelper dbHelper;

    public TaskModel(TaskDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public TaskModel(String task, int userId, TaskDbHelper dbHelper) {
        this.task = task;
        this.userId = userId;
        this.dbHelper = dbHelper;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTask(String task){
        this.task = task;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setDbHelper(TaskDbHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public int getUserId() {
        return userId;
    }
}
