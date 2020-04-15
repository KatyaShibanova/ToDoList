package com.example.todolist;

import com.example.todolist.model.Task;

import java.text.ParseException;

public interface IDataBase {
    void setTask (Task task);
    Task getTasks() throws ParseException;
}
