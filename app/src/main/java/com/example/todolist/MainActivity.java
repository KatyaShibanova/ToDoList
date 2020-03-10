package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void current_tasks_click(View view) {
        Intent intent = new Intent(MainActivity.this, current_tasks.class);
        startActivity(intent);
    }
    public void new_task_click(View view) {
        Intent intent = new Intent(MainActivity.this, new_task.class);
        startActivity(intent);
    }


}