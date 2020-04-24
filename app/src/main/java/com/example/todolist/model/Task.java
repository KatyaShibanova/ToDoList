package com.example.todolist.model;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.NewTaskActivity;
import com.example.todolist.TaskDB;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;
import java.util.Objects;

public class Task extends AppCompatActivity {
    public String name;
    public String description;
    public Priority priority;
    public Date deadline;
    public boolean isDone;

    public Task(String name, String description, Priority priority, Date deadline, boolean isDone){
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.isDone = isDone;
    }
}

