package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.todolist.model.Priority;
import com.example.todolist.model.Task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;


public class TaskDB extends SQLiteOpenHelper implements IDataBase  {

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

    Context mContext;

    TaskDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        db = this.getWritableDatabase();
        Log.d("DB_debug", "Creating DBHandler");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("DB_debug", "Creating TABLE");
        String CREATE_TASKS = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " VARCHAR" + KEY_DESCRIPTION + " TEXT" + KEY_PRIORITY + " VARCHAR" + KEY_DEADLINE + " VARCHAR" + KEY_ISDONE + " BIT)";
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

    public void setTask(String name, String description, Priority priority, Date deadline){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        /**values.put(KEY_ID, id);*/
        values.put(KEY_NAME, name);
        values.put(KEY_DESCRIPTION, description);
        /**values.put(KEY_PRIORITY, String.valueOf(priority));
        values.put(KEY_DEADLINE, deadline.toString());*/
        /**values.put(KEY_ISDONE, task.isDone);*/

        long newRowId = db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    /** public Task getTasks() throws ParseException {
        Cursor query = db.rawQuery("SELECT * FROM tasks;", null);
        //Task task = new Task();
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
    }*/

   public ArrayList<HashMap<String, String>> getTasks(){
//        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> taskList = new ArrayList<>();
        String query = "SELECT name, description, priority, deadline FROM "+ TABLE_TASKS;
        Log.d("DB_debug", query);
        Cursor cursor = db.query(TABLE_TASKS, null,
                null, null, null, null, null,
                null);
//        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> tasks = new HashMap<>();
            tasks.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            tasks.put("description",cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
            tasks.put("priority",/**Priority.valueOf()*/cursor.getString(cursor.getColumnIndex(KEY_PRIORITY)));
            tasks.put("deadline",cursor.getString(cursor.getColumnIndex(KEY_DEADLINE)));
            taskList.add(tasks);
        }
        db.close();
        return taskList;
    }
}
