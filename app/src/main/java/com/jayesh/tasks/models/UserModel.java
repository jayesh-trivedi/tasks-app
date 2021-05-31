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

    public UserModel(int id, String userName, TaskDbHelper dbHelper) {
        this.id = id;
        this.userName = userName;
        this.dbHelper = dbHelper;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public void setDbHelper(TaskDbHelper dbHelper){
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
        Cursor cursor = readableDb.query(dbHelper.TBL_USER, null, dbHelper.USER_USERNAME + " =?", new String[] {userName},null,null,null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.USER_ID))));
            setUsername(cursor.getString(cursor.getColumnIndex(dbHelper.USER_USERNAME)));
        }
        else {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(dbHelper.USER_USERNAME, userName);
            db.insert(dbHelper.TBL_USER,
                    null,
                    values);
            db.close();
        }
        readableDb.close();
    }
}
