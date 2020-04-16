package com.example.todolist;

import android.os.Build;
import android.os.Bundle;
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

/**public class NewTaskActivity extends MainActivity {

    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
    }

    public void save_click(int id, String name, String description, String priority, Date deadline, boolean isDone) {

        TaskDB taskDB = new TaskDB();
        final Task task = new Task(name,description, Priority.Medium,deadline,false);

        final TextInputEditText inputName = findViewById(R.id.newTask_name);
        inputName.addTextChangedListener(new TextWatcher() {
            //@Override
            public void afterTextChanged(Editable s) {}
            //@Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                task.name = s.toString();
            }
        });

        final TextInputEditText inputDescription = findViewById(R.id.newTask_description);
        inputDescription.addTextChangedListener(new TextWatcher() {
            //@Override
            public void afterTextChanged(Editable s) {}
            //@Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                task.description = s.toString();
            }
        });

        taskDB.setTask(task);
    }
}*/

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
                TaskDB taskDB = new TaskDB(getApplicationContext());
                taskDB.setTask(inputName, inputDescription, inputPriority, inputDate);
            }
        });
    }

    public Date dateParse(int year, int month, int dayOfMonth) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD", Locale.ROOT);
        String dateString = (String.valueOf(year) + String.valueOf(month) + String.valueOf(dayOfMonth));
        return dateFormat.parse(dateString);
}

}

