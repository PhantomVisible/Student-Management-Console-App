package com.studentmanagement;

import com.studentmanagement.model.Student;
import com.studentmanagement.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static StudentService studentService = new StudentService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    addOrUpdateCourse();
                    break;
                case 6:
                    removeCourse();
                    break;
                case 7:
                    viewStudentCourses();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Student Management System ===");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Add/Update Course & Note");
        System.out.println("6. Remove Course");
        System.out.println("7. View Student Courses & Average");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addStudent() {
        System.out.println("\n=== Add New Student ===");

        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter student email: ");
        String email = scanner.nextLine();

        Student student = new Student(id, name, email);
        studentService.addStudent(student);
        System.out.println("Student added successfully!");
    }

    private static void viewAllStudents() {
        System.out.println("\n=== All Students ===");
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void updateStudent() {
        System.out.println("\n=== Update Student ===");

        System.out.print("Enter student ID to update: ");
        String id = scanner.nextLine();

        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter new name (or press enter to skip): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) {
            existingStudent.setName(name);
        }

        System.out.print("Enter new email (or press enter to skip): ");
        String email = scanner.nextLine();
        if (!email.trim().isEmpty()) {
            existingStudent.setEmail(email);
        }

        if (studentService.updateStudent(existingStudent)) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Failed to update student.");
        }
    }

    private static void deleteStudent() {
        System.out.println("\n=== Delete Student ===");

        System.out.print("Enter student ID to delete: ");
        String id = scanner.nextLine();

        if (studentService.deleteStudent(id)) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found.");
        }

    }
    private static void addOrUpdateCourse() {
        System.out.println("\n=== Add or Update Course ===");
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();

        Student student = studentService.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();

        System.out.print("Enter note (0–20): ");
        double note;
        try {
            note = Double.parseDouble(scanner.nextLine());
            if (note < 0 || note > 20) {
                System.out.println("Invalid note. Must be between 0 and 20.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return;
        }

        student.addOrUpdateCourse(courseName, note);
        studentService.updateStudent(student);
        System.out.println("Course saved successfully!");
    }

    private static void removeCourse() {
        System.out.println("\n=== Remove Course ===");
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();

        Student student = studentService.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course name to remove: ");
        String courseName = scanner.nextLine();

        if (student.removeCourse(courseName)) {
            studentService.updateStudent(student);
            System.out.println("Course removed successfully!");
        } else {
            System.out.println("Course not found.");
        }
    }

    private static void viewStudentCourses() {
        System.out.println("\n=== View Student Courses ===");
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();

        Student student = studentService.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Courses for " + student.getName() + ":");
        student.displayCourses();
    }

}