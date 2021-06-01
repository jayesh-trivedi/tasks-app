package com.jayesh.tasks.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jayesh.tasks.utils.TaskDbHelper;

import java.util.ArrayList;

public class Task {
    private int id;
    private String task;
    private int userId;
    private TaskDbHelper dbHelper;

    public Task(int userId, TaskDbHelper dbHelper) {
        this.userId = userId;
        this.dbHelper = dbHelper;
    }

    public Task(String task, int userId, TaskDbHelper dbHelper) {
        this.task = task;
        this.userId = userId;
        this.dbHelper = dbHelper;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public ArrayList<String> getTasks() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskDbHelper.TBL_TASK, new String[]{TaskDbHelper.TASKS_TITLE},
                TaskDbHelper.TASKS_USER_ID_FK + " =?", new String[]{String.valueOf(getUserId())}, null,
                null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskDbHelper.TASKS_TITLE);
            taskList.add(cursor.getString(idx));
        }
        db.close();
        return taskList;
    }

    public void addTask() {
        SQLiteDatabase readableDb = dbHelper.getReadableDatabase();
        Cursor cursor = readableDb.query(TaskDbHelper.TBL_TASK, null, TaskDbHelper.TASKS_TITLE + " =? AND " +
                        TaskDbHelper.TASKS_USER_ID_FK + " =?", new String[]{getTask(), String.valueOf(getUserId())}, null,
                null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TaskDbHelper.TASKS_ID))));
            setTask(cursor.getString(cursor.getColumnIndex(TaskDbHelper.TASKS_TITLE)));
            setUserId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TaskDbHelper.TASKS_USER_ID_FK))));
        } else {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TaskDbHelper.TASKS_TITLE, getTask());
            values.put(TaskDbHelper.TASKS_USER_ID_FK, getUserId());
            db.insert(TaskDbHelper.TBL_TASK,
                    null,
                    values);
            db.close();
        }
        readableDb.close();
    }

    public void deleteTask() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TaskDbHelper.TBL_TASK, TaskDbHelper.TASKS_TITLE + " =? AND " + TaskDbHelper.TASKS_USER_ID_FK +
                " =?", new String[]{getTask(), String.valueOf(getUserId())});
        db.close();
    }
}
