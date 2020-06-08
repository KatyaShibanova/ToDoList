package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.todolist.model.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TaskAdapter extends ArrayAdapter<Task> {
    private LayoutInflater inflater; //из лэйаута делает вью-элемент
    private int layout;
    private ArrayList<Task> items;
//    private OnGroupClickListener onGroupClickListener;

    public TaskAdapter(Context context, int resource, ArrayList<Task> groups) {
        super(context, resource, groups);
        this.items = groups;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
//        this.onGroupClickListener = onGroupClickListener;
    }
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
        viewHolder.doneView.setText(task.name);
        /*viewHolder.doneView.setText(task.description);
        viewHolder.doneView.setText(task.priority.toString());*/

        return convertView;
    }

    private String dateTOString(Date date){ //парсим дату для фронта
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return  dateFormat.format(date);
    }

    private class ViewHolder {
        final TextView dateView;
        final CheckBox doneView;
        //final TextView infoView;
        ViewHolder(View view){
            dateView = (TextView) view.findViewById(R.id.item_date);
            doneView = (CheckBox) view.findViewById(R.id.done);
            //infoView = (TextView) view.findViewById(R.id.)
        }
    }
}

