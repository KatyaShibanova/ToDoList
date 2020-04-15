package com.example.todolist;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.example.todolist.model.Priority;
import com.example.todolist.model.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Date;

public class NewTaskActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
    }

    /**
     * id, name, description, priority, deadline, isDone
     */

    public void save_click(int id, String name, String description, String priority, Date deadline, boolean isDone) {

        TaskDB taskDB = new TaskDB();
        final Task task = new Task(name,description, Priority.Medium,deadline,false);

        final TextInputEditText inputName = findViewById(R.id.newTask_name);
        inputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                task.name = s.toString();
            }
        });

        final TextInputEditText inputDescription = findViewById(R.id.newTask_description);
        inputDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                task.description = s.toString();
            }
        });

        taskDB.setTask(task);
    }
}
