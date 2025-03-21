package com.example.studentmanagement.model;

public class Instructor {
    private int id;
    private String name;

    public Instructor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}
