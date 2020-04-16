package com.example.todolist;

import com.example.todolist.model.Priority;
import com.example.todolist.model.Task;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public interface IDataBase {
    void setTask (String name, String description, Priority priority, Date deadline);
    ArrayList<HashMap<String, String>> getTasks() throws ParseException;
}
