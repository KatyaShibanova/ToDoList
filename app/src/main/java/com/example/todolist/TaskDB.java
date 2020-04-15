package com.example.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.example.todolist.model.Priority;
import com.example.todolist.model.Task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//import IDataBase;

public class TaskDB implements IDataBase{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "taskDB";
    private static final String TABLE_TASKS = "tasks";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION ="description";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_DEADLINE = "deadline";
    private static final String KEY_ISDONE = "isdone";


    private static SQLiteDatabase db;

    public TaskDB (SQLiteDatabase db){
        this.db = db;
        String CREATE_TASKS = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + "(" + KEY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + "VARCHAR" + KEY_DESCRIPTION + "TEXT" + KEY_PRIORITY + "VARCHAR" + KEY_DEADLINE + "VARCHAR" + KEY_ISDONE + "BIT)";
        db.execSQL(CREATE_TASKS);
    }

    public TaskDB() {

    }

    /**int id, String name, String description, String priority, String deadline, boolean isDone*/

    public void setTask(Task task){
        ContentValues values = new ContentValues();
        /**values.put(KEY_ID, id);*/
        values.put(KEY_NAME, task.name);
        values.put(KEY_DESCRIPTION, task.description);
        values.put(KEY_PRIORITY, String.valueOf(task.priority));
        values.put(KEY_DEADLINE, task.deadline.toString());
        /**values.put(KEY_ISDONE, task.isDone);*/

        db.insert(TABLE_TASKS, null, values);
    }

    public Task getTasks() throws ParseException {
        Cursor query = db.rawQuery("SELECT * FROM tasks;", null);
        /**Task task = new Task();*/
        String name; String description; Priority priority; String deadline; boolean isDone;
        query.moveToFirst();
        name = query.getString(1);
        description = query.getString(2);
        priority = Priority.valueOf(query.getString(3));
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        deadline = query.getString(4);
        isDone = Boolean.parseBoolean(query.getString(5));

        Task task = new Task (name, description,priority, dateFormat.parse(deadline), isDone);
        return task;
    }
}
