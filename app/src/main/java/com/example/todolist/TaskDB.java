package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class TaskDB extends SQLiteOpenHelper {
//объявляем столбцы базы данных
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "taskDB";
    static final String TABLE_TASKS = "tasks";
    private static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_DESCRIPTION ="description";
    static final String KEY_PRIORITY = "priority";
    static final String KEY_DEADLINE = "deadline";
    static final String KEY_ISDONE = "isDone";

    static SQLiteDatabase db;//инициализируем бд

    TaskDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
        Log.d("DB_debug", "Creating DBHandler");
//        onUpgrade(db, 2, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//создаем таблицу
        Log.d("DB_debug", "Creating TABLE");
        String CREATE_TASKS = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_PRIORITY + " TEXT," + KEY_DEADLINE + " TEXT," + KEY_ISDONE + " BIT);";
        Log.d("DB_debug", CREATE_TASKS);
        db.execSQL(CREATE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        // Create tables again
        onCreate(db);
    }
}
