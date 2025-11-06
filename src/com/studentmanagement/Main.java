package com.studentmanagement;

import com.studentmanagement.model.Course;
import com.studentmanagement.model.Student;
import com.studentmanagement.service.StudentService;
import com.studentmanagement.service.StudentServiceImpl;
import com.studentmanagement.storage.FileStorage;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final String STORAGE_FILE="students.txt";

    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);

        FileStorage storage=new FileStorage(STORAGE_FILE);
        StudentService service=new StudentServiceImpl(storage);

        loop: while(true) {
            printMenu();
            String choice=sc.nextLine().trim();



            switch(choice) {

                case "1":
                    try {
                        System.out.print("Enter id: "); int id=Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Name: "); String name=sc.nextLine().trim();
                        System.out.print("Email: ");
                        String email=sc.nextLine().trim();
                        Student added=service.addStudent(id,name,email);
                        System.out.println("Added: "+added);
                    }
                    catch(Exception e){
                        System.out.println("Error: "+e.getMessage());
                    }
                    break;

                case "2":
                    List<Student> all=service.getAllStudents();
                    if(all.isEmpty())
                        System.out.println("No students.");
                    else
                        all.forEach(System.out::println);
                    break;

                case "3":
                    try{
                        System.out.print("Student id to update: ");
                        int uid=Integer.parseInt(sc.nextLine().trim());
                        Optional<Student> opt=service.findById(uid);

                        if(opt.isEmpty()){
                            System.out.println("Student not found.");
                            break;
                        }
                    Student s=opt.get();
                    System.out.println("Current: "+s);
                    System.out.print("New name: ");
                    String newName=sc.nextLine();
                    System.out.print("New email: ");
                    String newEmail=sc.nextLine();
                    boolean ok=service.updateStudent(uid,newName.isBlank()?null:newName,newEmail.isBlank()?null:newEmail);
                    System.out.println(ok?"Updated.":"Update failed.");
                    }
                    catch(Exception e){
                        System.out.println("Error: "+e.getMessage());
                    }
                    break;

                case "4":
                    try{
                        System.out.print("Student id to delete: ");
                        int did=Integer.parseInt(sc.nextLine().trim());
                        boolean deleted=service.deleteStudent(did);
                        System.out.println(deleted?"Deleted.":"Student not found.");
                    }
                    catch(Exception e){
                        System.out.println("Error: "+e.getMessage());
                    }
                    break;

                case "5":
                    try{
                        System.out.print("Student id: ");
                        int sid=Integer.parseInt(sc.nextLine().trim());
                        Optional<Student> opt=service.findById(sid);

                        if(opt.isEmpty()){
                            System.out.println("Student not found.");
                            break;
                        }
                        System.out.println("Available courses:");

                        for(Course c:Course.values())
                            System.out.println(" - "+c.name());
                        System.out.print("Course: ");
                        String cname=sc.nextLine().trim();
                        Course course=Course.fromString(cname);

                        if(course==null){
                            System.out.println("Unknown course.");
                            break;
                        }
                        System.out.print("Grade (0-20): ");
                        double grade=Double.parseDouble(sc.nextLine().trim());

                        if(grade<0||grade>20){
                            System.out.println("Grade must be 0-20.");
                            break;
                        }
                        boolean addedCourse=service.addOrUpdateCourseGrade(sid,course,grade);
                        System.out.println(addedCourse?"Course grade added/updated.":"Failed.");
                    }
                    catch(Exception e){
                        System.out.println("Error: "+e.getMessage());
                    }
                    break;

                case "6":
                    try{
                        System.out.print("Student id: ");
                        int sid2=Integer.parseInt(sc.nextLine().trim());
                        Optional<Student> opt2=service.findById(sid2);
                        if(opt2.isEmpty()){
                            System.out.println("Student not found.");
                            break;
                        }
                        System.out.println("Courses: "+opt2.get().coursesToString());
                        System.out.print("Course to remove: ");
                        String rc=sc.nextLine().trim();
                        Course c2=Course.fromString(rc);
                        if(c2==null){System.out.println("Unknown course.");
                            break;
                        }
                        boolean removed=service.removeCourseGrade(sid2,c2);
                        System.out.println(removed?"Course removed.":"Course not found.");
                    }
                    catch(Exception e){
                        System.out.println("Error: "+e.getMessage());
                    }
                    break;

                case "7":
                    try{
                        System.out.print("Student id: ");
                        int vsid=Integer.parseInt(sc.nextLine().trim());
                        Optional<Student> opt3=service.findById(vsid);
                        Double avg = service.getStudentAverage(vsid);
                        if (avg == null)
                            System.out.println("No courses.");
                        else
                            System.out.println("Average: " + String.format("%.2f", avg));

                    }
                    catch(Exception e){
                        System.out.println("Error: "+e.getMessage());
                    }
                    break;

                case "8":
                    Optional<Student> best=service.getBestStudent();
                    if(best.isPresent()){
                        Student b=best.get();
                        System.out.println("Best: "+b.getName()+" id:"+b.getId()+" avg:"+String.format("%.2f",b.average()));
                    }
                    else
                        System.out.println("No students with courses.");
                    break;

                case "9":
                    try {
                        double threshold = 10.0; // fixed threshold
                        List<Student> failing = service.getFailingStudents(threshold);

                        if (failing.isEmpty())
                            System.out.println("No failing students below 10 average.");
                        else {
                            System.out.println("=== Failing Students (<10 average) ===");
                            failing.forEach(s -> System.out.println(
                                    s.getName()+" (id:"+s.getId()+") avg:"+String.format("%.2f", s.average())
                            ));
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "10":
                    System.out.println("Closing Student Management Console App.");
                    break loop;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n=== Student Management System ===");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Add/Update Course & Note");
        System.out.println("6. Remove Course");
        System.out.println("7. View Student Courses & Average");
        System.out.println("8. Show Best Student");
        System.out.println("9. Show Failing Students");
        System.out.println("10. Exit");
        System.out.print("Enter your choice: ");
    }
}