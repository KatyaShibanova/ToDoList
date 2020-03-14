package com.example.todolist.model;

import java.util.Date;

public class Task {
    public String name;
    public boolean isDone;
    public Date date;
    public Priority priority;

    public  Task(String name, boolean isDone, Date date, Priority priority){
        this.name = name;
        this.isDone = isDone;
        this.date = date;
        this.priority = priority;
    }
}
