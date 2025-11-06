package com.studentmanagement.service;

import com.studentmanagement.model.Course;
import com.studentmanagement.model.Student;
import com.studentmanagement.storage.FileStorage;
import java.util.*;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {

    // In-memory list of students. Acts as the working data store.
    private final List<Student> students = new ArrayList<>();

    // Storage object for loading/saving students from/to file
    private final FileStorage storage;

    // Constructor loads existing students from storage
    public StudentServiceImpl(FileStorage storage) {
        this.storage=storage;
        List<Student> loaded=storage.loadStudents();
        if(loaded!=null)
            students.addAll(loaded);
    }

    // Save current list of students to storage
    private void persist() {
        storage.saveStudents(students);
    }


    @Override // Add a new student
    public Student addStudent(int id, String name, String email) {

        if (findById(id).isPresent()) { // Check if a student with the same ID already exists
            throw new IllegalArgumentException("A student with id " + id + " already exists.");
        }

        if (name == null || !name.matches("[a-zA-Z ]+")) { // Validate name (only letters and spaces allowed)
            throw new IllegalArgumentException("Name must contain letters and spaces only.");
        }

        if (email == null || !email.matches("^[a-zA-Z]+@school\\.com$")) { // Validate email (simple format: firstname@school.com)
            throw new IllegalArgumentException("Email must be in the format firstname@school.com");
        }

        // Create student, add to list, and persist changes
        Student s = new Student(id, name, email);
        students.add(s);
        persist();
        return s;
    }

    @Override // Return a copy of all students (prevents external modification of the internal list)
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    @Override // Find a student by ID, return as Optional
    public Optional<Student> findById(int id) {
        return students.stream().filter(s->s.getId()==id).findFirst();
    }

    @Override // Update a student's name and/or email
    public boolean updateStudent(int id,String newName,String newEmail) {
        Optional<Student> opt=findById(id);
        if(opt.isEmpty()) return false; // student not found
        Student s=opt.get();
        if(newName!=null&&!newName.trim().isEmpty())
            s.setName(newName);
        if(newEmail!=null&&!newEmail.trim().isEmpty())
            s.setEmail(newEmail);
        persist(); // save changes
        return true;
    }

    @Override // Delete a student by ID
    public boolean deleteStudent(int id) {
        boolean removed=students.removeIf(s->s.getId()==id);
        if(removed) persist(); // save changes if any removed
        return removed;
    }

    @Override // Add or update a grade for a student's course
    public boolean addOrUpdateCourseGrade(int studentId,Course course,double grade) {
        if(grade<0.0||grade>20.0)
            throw new IllegalArgumentException("Grade must be between 0 and 20.");
        Optional<Student> opt=findById(studentId);
        if(opt.isEmpty()) return false;
        Student s=opt.get();
        s.addOrUpdateCourseGrade(course,grade);
        persist();
        return true;
    }

    @Override // Remove a course from a student
    public boolean removeCourseGrade(int studentId,Course course) {
        Optional<Student> opt=findById(studentId);
        if(opt.isEmpty()) return false;
        Student s=opt.get();
        boolean removed=s.removeCourse(course);
        if(removed)
            persist();
        return removed;
    }

    @Override // Get a student's average grade
    public Double getStudentAverage(int studentId) {
        Optional<Student> opt=findById(studentId);
        return opt.map(Student::average).orElse(null); // returns null if student not found
    }

    @Override // Get the student with the highest average
    public Optional<Student> getBestStudent() {
        return students.stream()
                .filter(s->!s.getCourses().isEmpty())// only consider students with courses
                .max(Comparator.comparingDouble(Student::average));
    }

    @Override // Get students with average below a certain threshold
    public List<Student> getFailingStudents(double threshold) {
        return students.stream()
                .filter(s->!s.getCourses().isEmpty())// only students with grades
                .filter(s->s.average()<threshold)
                .collect(Collectors.toList());
    }

}