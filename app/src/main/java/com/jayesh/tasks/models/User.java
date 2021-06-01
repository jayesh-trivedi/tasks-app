package com.jayesh.tasks.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jayesh.tasks.utils.TaskDbHelper;

public class User {
    private int id;
    private String userName;
    private TaskDbHelper dbHelper;

    public User(String userName, TaskDbHelper dbHelper) {
        this.userName = userName;
        this.dbHelper = dbHelper;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void loginUser() {
        SQLiteDatabase readableDb = dbHelper.getReadableDatabase();
        Cursor cursor = readableDb.query(TaskDbHelper.TBL_USER, null, TaskDbHelper.USER_USERNAME + " =?",
                new String[]{getUserName()}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TaskDbHelper.USER_ID))));
        } else {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TaskDbHelper.USER_USERNAME, userName);
            long id = db.insert(TaskDbHelper.TBL_USER,
                    null,
                    values);
            setId((int) id);
            db.close();
        }
        readableDb.close();
    }
}
