package com.example.todolist;

import android.content.ContentValues;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.Objects;


public class NewTaskActivity extends AppCompatActivity {
    //инициализация view
    TextInputEditText name, description;
    RadioGroup priority;
    DatePicker deadline;
    Button saveButton;
    private static Priority inputPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task); //соединяем с xml
        //final DatePicker datePicker;
        Calendar calendar = Calendar.getInstance(); //объявляем календарь
        name = (TextInputEditText) findViewById(R.id.newTask_name); //name присваиваем view с id: newTask_name
        description = (TextInputEditText) findViewById(R.id.newTask_description);
        priority = (RadioGroup) findViewById(R.id.priority);
        deadline = (DatePicker) findViewById(R.id.date_picker);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        saveButton = (Button)findViewById(R.id.button_save);
    }

    public void save_click(View view){
        saveButton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String inputName = Objects.requireNonNull(name.getText()).toString(); //локальная переменная для передачи содержимого name в метод setTask
                String inputDescription = Objects.requireNonNull(description.getText()).toString();

                priority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //стандартный метод для работы с RadioGroup
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) { //перегрузка стандартного метода при изменении RadioButton
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

                Date inputDate = new Date();
                try {
                    inputDate = dateParse(deadline.getYear(), deadline.getMonth(), deadline.getDayOfMonth()); //используем уже написанный нами метод
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                /*Log.i("NewTaskActivity", "Work with database, info");
                Log.e("NewTaskActivity", "Work with database, error");
                Log.w("NewTaskActivity", "Work with database, warning");*/

                TaskDB taskDB = new TaskDB(getApplicationContext());
                setTask(taskDB, inputName, inputDescription, inputPriority, inputDate); //используем уже написанный нами метод
            }
        });
    }

    public Date dateParse(int year, int month, int dayOfMonth) throws ParseException { //метод для обработки даты
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        String dateString = (String.valueOf(year) + String.valueOf(month) + String.valueOf(dayOfMonth));
        return dateFormat.parse(dateString);
    }

    public void setTask(TaskDB taskDB, String name, String description, Priority priority, Date deadline){//метод для записи данных в базу
        taskDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_ID, id);
        values.put(TaskDB.KEY_NAME, name);
        values.put(TaskDB.KEY_DESCRIPTION, description);
        values.put(TaskDB.KEY_PRIORITY, String.valueOf(priority));
        values.put(TaskDB.KEY_DEADLINE, deadline.toString());
        boolean isDone;
        values.put(TaskDB.KEY_ISDONE, isDone = false);

        long newRowId = TaskDB.db.insert(TaskDB.TABLE_TASKS, null, values);
        TaskDB.db.close();
    }
}

