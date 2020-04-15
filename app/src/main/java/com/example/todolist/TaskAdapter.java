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
    private LayoutInflater inflater;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Task task = items.get(position);

        viewHolder.dateView.setText(dateTOString(task.deadline));
        viewHolder.doneView.setChecked(task.isDone);
        viewHolder.doneView.setText(task.name);

        return convertView;
    }

    protected String dateTOString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return  dateFormat.format(date);
    }

    private class ViewHolder {
        final TextView dateView;
        final CheckBox doneView;
        ViewHolder(View view){
            dateView = (TextView) view.findViewById(R.id.item_date);
            doneView = (CheckBox) view.findViewById(R.id.done);
        }
    }
}

