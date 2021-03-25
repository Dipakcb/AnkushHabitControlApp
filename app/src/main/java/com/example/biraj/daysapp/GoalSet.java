package com.example.biraj.daysapp;

import java.io.Serializable;

public class GoalSet implements Serializable {

    public String id;
    public String name;
    public String start;
    public String end;


    public GoalSet(String name, String start, String end) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }


    @Override
    public String toString() {
        return name;
    }
}
