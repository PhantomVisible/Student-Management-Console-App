# Student Management Console App

## A simple Java console application to manage students and their course grades. The project uses file-based storage to save and load student data.

Features

* Add, update, and delete students
* Manage courses and grades for each student
* View student averages and course grades
* Identify the best student
* List students failing below a certain grade threshold
* Persistent storage using a students.txt file
* Input validation for names, emails, and grades

Technologies

* Java 17+ (console application)
* File I/O (FileReader/FileWriter) for persistent storage
* Java Collections (ArrayList, LinkedHashMap)
* Streams and Optional for cleaner code logic

Project Structure

```Project Root/
├─ src/
│  └─ com/
│     └─ studentmanagement/
│        ├─ Main.java           # Entry point, menu-driven console app
│        ├─ model/
│        │  ├─ Student.java    # Student class with ID, email, courses
│        │  └─ Course.java     # Enum of courses
│        ├─ service/
│        │  ├─ StudentService.java       # Interface for student operations
│        │  └─ StudentServiceImpl.java  # Implements StudentService
│        └─ storage/
│           └─ FileStorage.java # Handles file read/write
├─ students.txt                # Persistent storage file
└─ README.md                   # This documentation
```
Menu Options

1. Add Student
2. View All Students
3. Update Student
4. Delete Student
5. Add/Update Course Note
6. Remove Course
7. View Student Courses & Average
8. Show Best Student
9. Show Failing Students
10. Exit

File Storage

* All student data is saved in a file called students.txt located in the project root.
* Format per line: id|name|email|COURSE1=grade1,COURSE2=grade2,...
* Example: 2|Bob|bob@school.com|ENGLISH=12,PE=15

License

This project is open-source and free to use.
I just made it in a group project to learn more about JAVA