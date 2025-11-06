package com.studentmanagement.service;

import com.studentmanagement.model.Course;
import com.studentmanagement.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student addStudent(int id,String name,String email);

    List<Student> getAllStudents();

    Optional<Student> findById(int id);

    boolean updateStudent(int id,String newName,String newEmail);

    boolean deleteStudent(int id);

    boolean addOrUpdateCourseGrade(int studentId, Course course, double grade);

    boolean removeCourseGrade(int studentId, Course course);

    Double getStudentAverage(int studentId);

    Optional<Student> getBestStudent();

    List<Student> getFailingStudents(double threshold);

}