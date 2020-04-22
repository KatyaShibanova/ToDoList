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

    /**public class OnClickListener implements com.example.todolist.model.OnClickListener {
        @Override
        public void onClick(TextInputEditText name, TextInputEditText description) {
            String inputName = Objects.requireNonNull(name.getText()).toString() + "\n";
            String inputDescription = Objects.requireNonNull(description.getText()).toString();
            //Priority inputPriority = Priority.valueOf(priority.);
            TaskDB taskDB = new TaskDB(getApplicationContext());
            taskDB.setTask(inputName, inputDescription);
            /**intent = new Intent(NewTaskActivity.this,DetailsActivity.class);
             startActivity(intent);
            intent = new Intent(NewTaskActivity.this,DetailsActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Details Inserted Successfully", Toast.LENGTH_SHORT).show();
        }
    }*/
}

