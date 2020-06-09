package com.example.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.model.Priority;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class NewTaskActivity extends AppCompatActivity {
    //присваиваем имена объектам разметки
    TextInputEditText name, description;
    RadioGroup priority;
    DatePicker deadline;
    Button saveButton;
    private static Priority inputPriority;
    boolean isDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task); //соединяем с xml
        //final DatePicker datePicker;
//        Calendar calendar = Calendar.getInstance(); //объявляем календарь
        name = (TextInputEditText) findViewById(R.id.newTask_name); //name присваиваем view с id: newTask_name
        description = (TextInputEditText) findViewById(R.id.newTask_description);
        priority = (RadioGroup) findViewById(R.id.priority);
        deadline = (DatePicker) findViewById(R.id.date_picker);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
        saveButton = (Button)findViewById(R.id.button_save);

        priority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //стандартный метод для работы с RadioGroup
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) { //метод при изменении RadioButton
                switch (checkedId) {
                    case R.id.priority_high:
                        inputPriority = Priority.High;
                        break;
                    case R.id.priority_medium:
                        inputPriority = Priority.Medium;
                        break;
                    default:
                        inputPriority = Priority.Low;
                        break;
                }
            }
        });
    }

    public void save_click(View view){
        saveButton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String inputName = Objects.requireNonNull(name.getText()).toString(); //локальная переменная для передачи содержимого name в метод setTask
                String inputDescription = Objects.requireNonNull(description.getText()).toString();

                int year = deadline.getYear();
                int month = deadline.getMonth();
                int day = deadline.getDayOfMonth();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                Log.d("date1", String.valueOf(calendar.getTime()));
                Date selectedDate = calendar.getTime();

                Date inputDate = new Date();

                inputDate = selectedDate; //используем уже написанный нами метод

                Log.d("date1", String.valueOf(inputDate));
                Log.d("date1", String.valueOf(deadline));

                /*Log.i("NewTaskActivity", "Work with database, info");
                Log.e("NewTaskActivity", "Work with database, error");
                Log.w("NewTaskActivity", "Work with database, warning");*/

                TaskDB taskDB = new TaskDB(getApplicationContext()); //создается объект класса TaskDB
                setTask(inputName, inputDescription, inputPriority, inputDate); //используем уже написанный нами метод

                toCurrentTasks();
            }
        });
    }

    /*public Date dateParse(int year, int month, int dayOfMonth) throws ParseException { //метод для обработки даты
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD", Locale.getDefault());
        String dateString = (String.valueOf(year) +"-"+ String.valueOf(month) +"-"+ String.valueOf(dayOfMonth));
        return dateFormat.parse(dateString);
    }*/

    public void setTask(String name, String description, Priority priority, Date deadline){//метод для записи данных в базу
        // taskDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_ID, id);
        values.put(TaskDB.KEY_NAME, name);
        values.put(TaskDB.KEY_DESCRIPTION, description);
        if(priority == null) {
            values.put(TaskDB.KEY_PRIORITY, String.valueOf(Priority.Low));
        } else {
            values.put(TaskDB.KEY_PRIORITY, String.valueOf(priority));
        }

        values.put(TaskDB.KEY_DEADLINE, deadline.toString());

        values.put(TaskDB.KEY_ISDONE, isDone);

        long newRowId = TaskDB.db.insert(TaskDB.TABLE_TASKS, null, values);
        TaskDB.db.close();
    }

    public void toCurrentTasks() { //интент с переходом на активность новой задачи
        Intent intent = new Intent(NewTaskActivity.this, CurrentTasksActivity.class);
        startActivity(intent);
    }
}