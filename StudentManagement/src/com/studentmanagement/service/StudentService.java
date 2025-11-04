package com.studentmanagement.service;

import com.studentmanagement.model.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private List<Student> students;

    public StudentService() {
        this.students = new ArrayList<>();
    }

    // Create
    public void addStudent(Student student) {
        students.add(student);
    }

    // Read
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Student getStudentById(String id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Update
    public boolean updateStudent(Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(updatedStudent.getId())) {
                students.set(i, updatedStudent);
                return true;
            }
        }
        return false;
    }

    // Delete
    public boolean deleteStudent(String id) {
        return students.removeIf(student -> student.getId().equals(id));
    }
}