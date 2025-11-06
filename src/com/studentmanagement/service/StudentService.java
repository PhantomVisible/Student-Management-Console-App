package com.studentmanagement.service;

import com.studentmanagement.model.Course;
import com.studentmanagement.model.Student;

import java.util.List;
import java.util.Optional;

// Interface defining the operations for managing students
public interface StudentService {

    // Add a new student
    // Throws exception if the ID already exists or inputs are invalid
    Student addStudent(int id,String name,String email);

    // Get a list of all students
    List<Student> getAllStudents();

    // Find a student by ID
    // Returns Optional.empty() if student not found
    Optional<Student> findById(int id);

    // Update a student's name and/or email
    // Returns true if update succeeded, false if student not found
    boolean updateStudent(int id,String newName,String newEmail);

    // Delete a student by ID
    // Returns true if deletion succeeded, false if student not found
    boolean deleteStudent(int id);

    // Add or update a grade for a student's course
    // Returns true if successful, false if student not found
    boolean addOrUpdateCourseGrade(int studentId, Course course, double grade);

    // Remove a course grade from a student
    // Returns true if course was removed, false if student or course not found
    boolean removeCourseGrade(int studentId, Course course);

    // Get a student's average grade
    // Returns null if student not found or has no courses
    Double getStudentAverage(int studentId);

    // Get the student with the highest average
    Optional<Student> getBestStudent();

    // Get all students whose average is below a certain threshold
    List<Student> getFailingStudents(double threshold);

}