package com.studentmanagement.model;

public class Course {
    private String name;
    private double note;

    public Course(String name, double note) {
        this.name = name;
        this.note = note;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", note=" + note +
                '}';
    }
}