package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //при запуске активности вызывается activity_main.xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void current_tasks_click(View view) { //интент с переходом на активность текущих задач
        Intent intent = new Intent(MainActivity.this, CurrentTasksActivity.class);
        startActivity(intent);
    }

    public void new_task_click(View view) { //интент с переходом на активность новой задачи
        Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
        startActivity(intent);
    }
}


