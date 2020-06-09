package com.example.todolist;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.todolist.model.Priority;
import com.example.todolist.model.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.todolist.R.drawable.check_high;

public class TaskAdapter extends ArrayAdapter<Task> {
    private LayoutInflater inflater; //из лэйаута делает вью-элемент
    private int layout;
    private ArrayList<Task> items;
    //TaskDB taskDB;
    //this.taskDB =  new TaskDB(getApplicationContext());
//    private OnGroupClickListener onGroupClickListener;

    public TaskAdapter(Context context, int resource, ArrayList<Task> groups) {
        super(context, resource, groups);
        this.items = groups;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
//        this.onGroupClickListener = onGroupClickListener;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getView(int position, View convertView, ViewGroup parent) { //делаем из task_list_item вью-элемент для activity_current_task

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView); //ссылка на конвертВью
            convertView.setTag(viewHolder); //методом сетТег прикрепляем ссылку на ковертВью
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Task task = items.get(position);

        viewHolder.dateView.setText(dateTOString(task.deadline));
        viewHolder.doneView.setChecked(task.isDone);
        viewHolder.nameView.setText(task.name);
        viewHolder.descriptionView.setText(task.description);

        //String priorityString = task.priority.toString();
        switch (task.priority) {
            case High:
                viewHolder.doneView.setBackgroundResource(R.drawable.check_high);
                break;
            case Medium:
                viewHolder.doneView.setBackgroundResource(R.drawable.check_medium);
                break;
            default:
                viewHolder.doneView.setBackgroundResource(R.drawable.check_low);
                break;
        }

        /*boolean isChecked = viewHolder.doneView.isChecked();
        if (isChecked){
            task.isDone = true;
        }*/

        /*if (viewHolder.doneView.isChecked()){

            long taskId = task.getTaskId();
            CurrentTasksActivity.taskDone(true, Long.toString(taskId));
        }*/

        return convertView;
    }

    private String dateTOString(Date date){ //парсим дату для фронта
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return  dateFormat.format(date);
    }

    private static class ViewHolder {
        final TextView dateView;
        final CheckBox doneView;
        final TextView nameView;
        final TextView descriptionView;
        ViewHolder(View view){
            dateView = (TextView) view.findViewById(R.id.currentTasks_date);
            doneView = (CheckBox) view.findViewById(R.id.currentTasks_isDone);
            nameView = (TextView) view.findViewById(R.id.currentTasks_name);
            descriptionView = (TextView) view.findViewById(R.id.currentTasks_description);
        }
    }
}

