package com.jayesh.tasks.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jayesh.tasks.utils.TaskDbHelper;

public class UserModel {
    private int id;
    private String userName;
    private TaskDbHelper dbHelper;

    public UserModel(TaskDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public UserModel(String userName, TaskDbHelper dbHelper) {
        this.userName = userName;
        this.dbHelper = dbHelper;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public void setDbHelper(TaskDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public int getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void loginUser(String userName) {
        SQLiteDatabase readableDb = dbHelper.getReadableDatabase();
        Cursor cursor = readableDb.query(TaskDbHelper.TBL_USER, null, TaskDbHelper.USER_USERNAME + " =?",
                new String[]{userName}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TaskDbHelper.USER_ID))));
            setUsername(cursor.getString(cursor.getColumnIndex(TaskDbHelper.USER_USERNAME)));
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
