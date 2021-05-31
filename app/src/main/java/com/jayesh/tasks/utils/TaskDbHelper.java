package com.jayesh.tasks.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "taskDatabase";
    private static final Integer DB_VERSION = 1;

    public static final String TBL_TASK = "tasks";
    public static final String TBL_USER = "users";

    public TaskDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TBL_USER + " ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT NOT NULL UNIQUE);";
        String createTaskTable = "CREATE TABLE " + TBL_TASK + " ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT NOT NULL UNIQUE, userID INTEGER NOT NULL, FOREIGN KEY (userID) REFERENCES " + TBL_USER + " (_ID));";
        db.execSQL(createUserTable);
        db.execSQL(createTaskTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
