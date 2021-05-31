package com.jayesh.tasks.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "taskDatabase";
    private static final Integer DB_VERSION = 1;

    public static final String TBL_TASK = "tasks";
    public static final String TBL_USER = "users";

    public static final String TASKS_ID = "id";
    public static final String TASKS_TITLE = "title";
    public static final String TASKS_USER_ID_FK = "userId";
    public static final String USER_ID = "id";
    public static final String USER_USERNAME = "userName";

    public TaskDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TBL_USER + " ( " + USER_ID + " INTEGER PRIMARY KEY" +
                " AUTOINCREMENT, " + USER_USERNAME + " TEXT NOT NULL UNIQUE);";
        String createTaskTable = "CREATE TABLE " + TBL_TASK + " ( " + TASKS_ID + " INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, " + TASKS_TITLE + " TEXT NOT NULL UNIQUE, " +
                TASKS_USER_ID_FK + " INTEGER NOT NULL, FOREIGN KEY (" +
                TASKS_USER_ID_FK + ") REFERENCES " + TBL_USER + " (" + USER_ID + "));";
        db.execSQL(createUserTable);
        db.execSQL(createTaskTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_TASK);
        onCreate(db);
    }
}
