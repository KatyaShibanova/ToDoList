package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.todolist.model.Priority;
import com.example.todolist.model.Task;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Objects;

import android.database.sqlite.SQLiteOpenHelper;



public class CurrentTasksActivity extends AppCompatActivity {
    ArrayList<Task> tasks = new ArrayList<>();
    TaskAdapter taskAdapter;
    ListView tasksList;
    TaskDB taskDB;
    boolean isDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_tasks);
        tasksList = (ListView) findViewById(R.id.tasks_list);
        this.taskDB =  new TaskDB(getApplicationContext());

        /*Log.i("CurrentTasksActivity", "Work with database, info");
        Log.e("CurrentTasksActivity", "Work with database, error");
        Log.w("CurrentTasksActivity", "Work with database, warning");*/

        initTaskList();

    }

    public void sortByPriority(View v) {
        Collections.sort(tasks, new SortByPriority());
        taskAdapter.notifyDataSetChanged();
    }

    public void sortByDate(View v) {
        Collections.sort(tasks, new SortByDate());
        taskAdapter.notifyDataSetChanged();
    }

     private void initTaskList(){
        tasks = getTasks();
        taskAdapter = new TaskAdapter(this, R.layout.task_list_item, tasks);
        tasksList.setAdapter(taskAdapter);
     }

    private class SortByPriority implements Comparator<Task> {
        public int compare(Task a, Task b) {
            return a.priority.compareTo(b.priority);
        }
    }

    private class SortByDate implements Comparator<Task> {
        public int compare(Task a, Task b) {
            return a.deadline.getTime() > b.deadline.getTime() ? 1 : -1;
        }
    }

    public ArrayList<Task> getTasks(){ //метод getTasks, возвращающий данные типа ArrayList<Task>
        //SQLiteDatabase db = this.getWritableDatabase();
        //taskDB.getWritableDatabase();
//        taskAdapter = new TaskAdapter(this, R.layout.task_list_item, tasks);
//        tasksList.setAdapter(taskAdapter);

        ArrayList<Task> taskList = new ArrayList<>();
        String query = "SELECT name, description, priority, deadline, isDone FROM "+ TaskDB.TABLE_TASKS;
        Log.d("DB_debug", query);
        Cursor cursor = TaskDB.db.query(TaskDB.TABLE_TASKS, null, null,null,null,null,null, null);
        while (cursor.moveToNext()){
            Priority priority =  Priority.valueOf(cursor.getString(cursor.getColumnIndex(TaskDB.KEY_PRIORITY)));
            /*String s = cursor.getString(cursor.getColumnIndex(TaskDB.KEY_PRIORITY));
            if(s == "null"){
                priority = Priority.valueOf(cursor.getString(cursor.getColumnIndex(TaskDB.KEY_PRIORITY)));
            }*/

            Task task = new Task(cursor.getString(cursor.getColumnIndex(TaskDB.KEY_NAME)),
                    cursor.getString(cursor.getColumnIndex(TaskDB.KEY_DESCRIPTION)),
                    priority,
                    new Date(cursor.getString(cursor.getColumnIndex(TaskDB.KEY_DEADLINE))),
                    cursor.getString(cursor.getColumnIndex(TaskDB.KEY_ISDONE)) == "1");
            taskList.add(task);
        }
        TaskDB.db.close();
        return taskList;
    }



   /* static void taskDone(boolean isDone, String id){
        //SQLiteDatabase db = TaskDB.getWritableDatabase();
// New value for one column
        //String title = "MyNewTitle";
        ContentValues values = new ContentValues();
        values.put(TaskDB.KEY_ISDONE, isDone);
// Which row to update, based on the title
        //long[] ids = tasksList.getCheckedItemIds();
        String selection = TaskDB.KEY_ID + " LIKE ?";
        String[] selectionArgs = { id };

        //Log.d(ids.toString(),);
        int count = TaskDB.db.update(TaskDB.TABLE_TASKS, values, "id="+id, null);

//        long[] ids = tasksList.getCheckedItemIds();
//
//        ListIterator<String> listIter = ids.listIterator();
//
//        while(listIter.hasNext()){
//
//            System.out.println(listIter.next());
//        }
//        String query = "DELETE FROM "+ TaskDB.TABLE_TASKS + "WHERE id=" + view.getId();
//        Log.d("DB_debug", query);
//        Cursor cursor = TaskDB.db.query(TaskDB.TABLE_TASKS, null, null,null,null,null,null, null);
//
//
//        long id = view.getId();
//        SQLiteDatabase db =
//        int delCount = db.delete(TaskDB.TABLE_TASKS, "id = " + id, null);
//        Log.d("LOG_TAG", "deleted rows count = " + delCount);
//        TaskDB.db.close();
    }*/
}



