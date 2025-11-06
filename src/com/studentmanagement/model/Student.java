package com.studentmanagement.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Student {
    private int id;
    private String name;
    private String email;
    private Map<Course, Double> courses = new LinkedHashMap<>();

    public Student(int id, String name, String email) {
        this.id=id;
        this.name=name;
        this.email=email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setCourses(Map<Course, Double> courses) {
        this.courses = courses;
    }
    public void addOrUpdateCourseGrade(Course course, double grade) {
        courses.put(course, grade);
    }

    public boolean removeCourse(Course course) {
        return courses.remove(course) != null;
    }

    public double average() {
        if (courses.isEmpty())
            return 0.0;
        double sum=0.0;
        for(double g:courses.values())
            sum+=g;
        return sum/courses.size();
    }

    @Override public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + "', avg=" + String.format("%.2f", average()) + ", courses=" + coursesToString() + "}";
    }

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

    @Override public boolean equals(Object o) {
        if(this==o) return true;
        if(o==null||getClass()!=o.getClass())
            return false;
        Student s=(Student)o;
            return id==s.id;
    }

    @Override public int hashCode() {
        return Objects.hash(id);
    }
}