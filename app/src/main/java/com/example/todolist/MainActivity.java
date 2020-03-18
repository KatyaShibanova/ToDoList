package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void current_tasks_click(View view) {
        Intent intent = new Intent(MainActivity.this, CurrentTasksActivity.class);
        startActivity(intent);
    }
    public void new_task_click(View view) {
        Intent intent = new Intent(MainActivity.this, new_task.class);
        startActivity(intent);
    }

    public void database() {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS tasks (id INTEGER, importance INTEGER, description TEXT, deadline TEXT)");
    }


}