package com.example.todolist;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.model.Priority;
import com.example.todolist.model.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import android.database.sqlite.SQLiteOpenHelper;


public class NewTaskActivity extends AppCompatActivity {
    TextInputEditText name, description;
    RadioGroup priority;
    DatePicker deadline;
    Button saveButton;
    private Object RadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        final DatePicker datePicker;
        Calendar calendar = Calendar.getInstance();
        name = (TextInputEditText) findViewById(R.id.newTask_name);
        description = (TextInputEditText) findViewById(R.id.newTask_description);
        priority = (RadioGroup) findViewById(R.id.priority);
        deadline = (DatePicker) findViewById(R.id.date_picker);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        /**deadline.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(getApplicationContext(),"дата изменена", Toast.LENGTH_SHORT).show();
            }
        });*/
        saveButton = (Button)findViewById(R.id.button_save);
    }

    public void save_click(View view){
        saveButton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String inputName = Objects.requireNonNull(name.getText()).toString();
                String inputDescription = Objects.requireNonNull(description.getText()).toString();
                Priority inputPriority = Priority.valueOf((String) RadioGroup);
                Date inputDate = null;
                try {
                    inputDate = dateParse(deadline.getYear(), deadline.getMonth(), deadline.getDayOfMonth());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.i("NewTaskActivity", "Work with database, info");
                Log.e("NewTaskActivity", "Work with database, error");
                Log.w("NewTaskActivity", "Work with database, warning");


                TaskDB taskDB = new TaskDB(getApplicationContext());
                setTask(taskDB, inputName, inputDescription, inputPriority, inputDate);
                //taskDB.getWritableDatabase();
                //taskDB.setTask(inputName, inputDescription, inputPriority, inputDate);
            }
        });
    }

    public Date dateParse(int year, int month, int dayOfMonth) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD", Locale.ROOT);
        String dateString = (String.valueOf(year) + String.valueOf(month) + String.valueOf(dayOfMonth));
        return dateFormat.parse(dateString);
    }

    public void setTask(TaskDB taskDB, String name, String description, Priority priority, Date deadline){
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

