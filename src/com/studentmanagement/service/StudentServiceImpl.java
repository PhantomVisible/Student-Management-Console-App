package com.studentmanagement.service;

import com.studentmanagement.model.Course;
import com.studentmanagement.model.Student;
import com.studentmanagement.storage.FileStorage;
import java.util.*;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    private final List<Student> students = new ArrayList<>();
    private final FileStorage storage;
    public StudentServiceImpl(FileStorage storage) { this.storage=storage; List<Student> loaded=storage.loadStudents(); if(loaded!=null) students.addAll(loaded); }
    private void persist() { storage.saveStudents(students); }
    @Override
    public Student addStudent(int id, String name, String email) {
        // Check for duplicate ID
        if (findById(id).isPresent()) {
            throw new IllegalArgumentException("A student with id " + id + " already exists.");
        }

        // Validate name (letters and spaces only)
        if (name == null || !name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Name must contain letters and spaces only.");
        }

        // Validate email (simple regex for name@school.com)
        if (email == null || !email.matches("^[a-zA-Z]+@school\\.com$")) {
            throw new IllegalArgumentException("Email must be in the format firstname@school.com");
        }

        // Create student and add to list
        Student s = new Student(id, name, email);
        students.add(s);
        persist();
        return s;
    }

    @Override public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    @Override public Optional<Student> findById(int id) {
        return students.stream().filter(s->s.getId()==id).findFirst();
    }

    @Override public boolean updateStudent(int id,String newName,String newEmail) {
        Optional<Student> opt=findById(id);
        if(opt.isEmpty()) return false;
        Student s=opt.get();
        if(newName!=null&&!newName.trim().isEmpty())
            s.setName(newName);
        if(newEmail!=null&&!newEmail.trim().isEmpty())
            s.setEmail(newEmail);
        persist();
        return true;
    }

    @Override public boolean deleteStudent(int id) {
        boolean removed=students.removeIf(s->s.getId()==id);
        if(removed)
            persist();
        return removed;
    }

    @Override public boolean addOrUpdateCourseGrade(int studentId,Course course,double grade) {
        if(grade<0.0||grade>20.0)
            throw new IllegalArgumentException("Grade must be between 0 and 20.");
        Optional<Student> opt=findById(studentId);
        if(opt.isEmpty()) return false;
        Student s=opt.get();
        s.addOrUpdateCourseGrade(course,grade);
        persist();
        return true;
    }

    @Override public boolean removeCourseGrade(int studentId,Course course) {
        Optional<Student> opt=findById(studentId);
        if(opt.isEmpty()) return false;
        Student s=opt.get();
        boolean removed=s.removeCourse(course);
        if(removed)
            persist();
        return removed;
    }

    @Override public Double getStudentAverage(int studentId) {
        Optional<Student> opt=findById(studentId);
        return opt.map(Student::average).orElse(null);
    }

    @Override public Optional<Student> getBestStudent() {
        return students.stream().filter(s->!s.getCourses().isEmpty()).max(Comparator.comparingDouble(Student::average));
    }

    @Override public List<Student> getFailingStudents(double threshold) {
        return students.stream().filter(s->!s.getCourses().isEmpty()).filter(s->s.average()<threshold).collect(Collectors.toList());
    }

}