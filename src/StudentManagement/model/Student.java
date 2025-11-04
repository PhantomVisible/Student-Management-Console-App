package StudentManagement.model;

import StudentManagement.model.Course;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String id;
    private String name;
    private String email;
    private List<Course> courses;

    public Student(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.courses = new ArrayList<>();
    }

    // Getters and Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    // Add or update a course for this student
    public void addOrUpdateCourse(String courseName, double note) {
        for (Course c : courses) {
            if (c.getName().equalsIgnoreCase(courseName)) {
                c.setNote(note);
                return;
            }
        }
        courses.add(new Course(courseName, note));
    }

    // Remove a course by name
    public boolean removeCourse(String courseName) {
        return courses.removeIf(c -> c.getName().equalsIgnoreCase(courseName));
    }

    // Calculate average note
    public double calculateAverage() {
        if (courses.isEmpty()) return 0;
        double sum = 0;
        for (Course c : courses) sum += c.getNote();
        return sum / courses.size();
    }

    // Display all courses with notes
    public void displayCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses found for this student.");
        } else {
            for (Course c : courses) {
                System.out.println(" - " + c.getName() + ": " + c.getNote());
            }
            System.out.println("Average: " + String.format("%.2f", calculateAverage()));
        }
    }

}