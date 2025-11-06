package com.studentmanagement.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Student {
    private int id;
    private String name;
    private String email;
    private Map<Course, Double> courses = new LinkedHashMap<>();

    // Constructor initializes id, name, and email
    public Student(int id, String name, String email) {
        this.id=id;
        this.name=name;
        this.email=email;
    }

    // Standard getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<Course, Double> getCourses() {
        return courses;
    }


    // Adds a new course or updates the grade if it already exists
    public void addOrUpdateCourseGrade(Course course, double grade) {
        courses.put(course, grade);
    }
    // Removes a course; returns true if course was present
    public boolean removeCourse(Course course) {
        return courses.remove(course) != null;
    }
    // Calculates average grade across all courses; returns 0 if no courses
    public double average() {
        if (courses.isEmpty())
            return 0.0;
        double sum=0.0;
        for(double g:courses.values())
            sum+=g;
        return sum/courses.size();
    }

    // Returns a string representation of the student
    @Override public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + "', avg=" + String.format("%.2f", average()) + ", courses=" + coursesToString() + "}";
    }

    // Converts the courses map into a string like "MATH=15,SCIENCE=18"
    public String coursesToString() {
        if (courses.isEmpty()) return "";
        StringBuilder sb=new StringBuilder();
        boolean first=true;
        for(Map.Entry<Course, Double> e:courses.entrySet()) {
            if(!first) sb.append(",");
            sb.append(e.getKey().name()).append("=").append(e.getValue());
            first=false;
        }
        return sb.toString();
    }

    // Students are equal if their IDs match
    @Override public boolean equals(Object o) {
        if(this==o) return true;
        if(o==null||getClass()!=o.getClass())
            return false;
        Student s=(Student)o;
            return id==s.id;
    }

    // Hash code based on student ID
    @Override public int hashCode() {
        return Objects.hash(id);
    }
}