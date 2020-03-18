package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.todolist.model.Priority;
import com.example.todolist.model.Task;

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
        initTaskList();
    }

    public void sortByPriority(View v){
        Collections.sort(tasks, new SortByPriority());
        taskAdapter.notifyDataSetChanged();
    }

    public void sortByDate(View v){
        Collections.sort(tasks, new SortByDate());
        taskAdapter.notifyDataSetChanged();
    }

    private void initTaskList(){
        tasks.add(new Task("Задача 1", false, new Date(2019, 12,23), Priority.Low));
        tasks.add(new Task("Задача 2", false, new Date(), Priority.Medium));
        tasks.add(new Task("Задача 3", true, new Date(), Priority.Low));
        tasks.add(new Task("Задача 4", false, new Date(), Priority.High));
        tasks.add(new Task("Задача 5", true, new Date(), Priority.High));
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
            return a.date.getTime() > b.date.getTime() ? 1 : -1;
        }
    }
}
