package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import android.database.sqlite.SQLiteOpenHelper;



public class CurrentTasksActivity extends AppCompatActivity {
    ArrayList<Task> tasks = new ArrayList<>();
    TaskAdapter taskAdapter;
    ListView tasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_tasks);
        tasksList = (ListView) findViewById(R.id.tasks_list);
        TaskDB taskDB =  new TaskDB(getApplicationContext());

        /*Log.i("CurrentTasksActivity", "Work with database, info");
        Log.e("CurrentTasksActivity", "Work with database, error");
        Log.w("CurrentTasksActivity", "Work with database, warning");*/

        getTasks(taskDB);
    }

    public void sortByPriority(View v) {
        Collections.sort(tasks, new SortByPriority());
        taskAdapter.notifyDataSetChanged();
    }

    public void sortByDate(View v) {
        Collections.sort(tasks, new SortByDate());
        taskAdapter.notifyDataSetChanged();
    }

    /**
     * private void initTaskList(){
     * tasks.add(new Task("Задача 1","lala", Priority.Low, new Date(2019, 12,23), false));
     * tasks.add(new Task("Задача 2","lala", Priority.Low, new Date(2019, 12,23), false));
     * tasks.add(new Task("Задача 3", "lala", Priority.Low, new Date(2019, 12,23), false));
     * tasks.add(new Task("Задача 4","lala", Priority.Low, new Date(2019, 12,23), false));
     * tasks.add(new Task("Задача 5","lala", Priority.Low, new Date(2019, 12,23), false));
     * taskAdapter = new TaskAdapter(this, R.layout.task_list_item, tasks);
     * tasksList.setAdapter(taskAdapter);
     * }
     */

    private static class SortByPriority implements Comparator<Task> {
        public int compare(Task a, Task b) {
            return a.priority.compareTo(b.priority);
        }
    }

    private static class SortByDate implements Comparator<Task> {
        public int compare(Task a, Task b) {
            return a.deadline.getTime() > b.deadline.getTime() ? 1 : -1;
        }
    }

    public ArrayList<HashMap<String, String>> getTasks(TaskDB taskDB){
        //SQLiteDatabase db = this.getWritableDatabase();
        //taskDB.getWritableDatabase();
        ArrayList<HashMap<String, String>> taskList = new ArrayList<>();
        String query = "SELECT name, description, priority, deadline, isDone FROM "+ TaskDB.TABLE_TASKS;
        Log.d("DB_debug", query);
        Cursor cursor = TaskDB.db.query(TaskDB.TABLE_TASKS, null, null,null,null,null,null, null);
        while (cursor.moveToNext()){
            HashMap<String,String> tasks = new HashMap<>();
            tasks.put("name",cursor.getString(cursor.getColumnIndex(TaskDB.KEY_NAME)));
            tasks.put("description",cursor.getString(cursor.getColumnIndex(TaskDB.KEY_DESCRIPTION)));
            tasks.put("priority",cursor.getString(cursor.getColumnIndex(TaskDB.KEY_PRIORITY)));
            tasks.put("deadline",cursor.getString(cursor.getColumnIndex(TaskDB.KEY_DEADLINE)));
            tasks.put("isDone",cursor.getString(cursor.getColumnIndex(TaskDB.KEY_ISDONE)));
            taskList.add(tasks);
        }
        TaskDB.db.close();
        return taskList;
    }
}



