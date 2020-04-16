package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class CurrentTasksActivity extends AppCompatActivity {
    ArrayList<Task> tasks = new ArrayList<>();
    TaskAdapter taskAdapter;
    ListView tasksList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_tasks);
        tasksList = (ListView) findViewById(R.id.tasks_list);
        /**initTaskList();*/
        TaskDB taskDB =  new TaskDB(getApplicationContext());
        taskDB.getTasks();
    }

    public void sortByPriority(View v){
        Collections.sort(tasks, new SortByPriority());
        taskAdapter.notifyDataSetChanged();
    }

    public void sortByDate(View v){
        Collections.sort(tasks, new SortByDate());
        taskAdapter.notifyDataSetChanged();
    }

    /**private void initTaskList(){
        tasks.add(new Task("Задача 1","lala", Priority.Low, new Date(2019, 12,23), false));
        tasks.add(new Task("Задача 2","lala", Priority.Low, new Date(2019, 12,23), false));
        tasks.add(new Task("Задача 3", "lala", Priority.Low, new Date(2019, 12,23), false));
        tasks.add(new Task("Задача 4","lala", Priority.Low, new Date(2019, 12,23), false));
        tasks.add(new Task("Задача 5","lala", Priority.Low, new Date(2019, 12,23), false));
        taskAdapter = new TaskAdapter(this, R.layout.task_list_item, tasks);
        tasksList.setAdapter(taskAdapter);
    }*/

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
}
