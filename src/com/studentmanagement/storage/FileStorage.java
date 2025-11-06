package com.studentmanagement.storage;

import com.studentmanagement.model.Course;
import com.studentmanagement.model.Student;

import java.io.*;
import java.util.*;

public class FileStorage {
    private final String filePath;
    public FileStorage(String filePath) {
        this.filePath=filePath;
    }

    public List<Student> loadStudents() {
        List<Student> students=new ArrayList<>();

        File f=new File(filePath);

        if(!f.exists()) return students;

        try(BufferedReader br=new BufferedReader(new FileReader(f))) {
            String line; int lineNo=0;
            while((line=br.readLine())!=null) {
                lineNo++;
                line=line.trim();
                if(line.isEmpty())
                    continue;
                Student s=parseLine(line);
                if(s!=null)
                    students.add(s);
                else
                    System.err.println("Skipping malformed line "+lineNo+": "+line);
            }
        } catch(IOException e) {
            System.err.println("Error reading students file: "+e.getMessage());
        }
        return students;
    }

    public void saveStudents(List<Student> students) {
        File target=new File(filePath);
        File tmp=new File(filePath+".tmp");
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(tmp))) {
            for(Student s:students) {
                bw.write(formatStudent(s)); bw.newLine();
            }
        }
        catch(IOException e) {
            System.err.println("Error writing tmp file: "+e.getMessage());
            return;
        }
        if(!tmp.renameTo(target)) {
            try(BufferedReader br=new BufferedReader(new FileReader(tmp)); BufferedWriter bw=new BufferedWriter(new FileWriter(target))) {
                String line;
                while((line=br.readLine())!=null)
                    bw.write(line+System.lineSeparator());
            }
            catch(IOException e) {
                System.err.println("Error replacing students file: "+e.getMessage()); } finally { tmp.delete();
            }
        }
    }

    private Student parseLine(String line) {
        String[] parts=line.split("\\|",-1);
        if(parts.length<3) return null;
        try {
            int id=Integer.parseInt(parts[0].trim());
            String name=parts[1].trim();
            String email=parts[2].trim();
            Student s=new Student(id,name,email);
            if(parts.length>=4 && !parts[3].trim().isEmpty()) {
                String coursesPart=parts[3].trim();
                String[] coursePairs=coursesPart.split(",");
                for(String cp:coursePairs) {
                    String[] kv=cp.split("=",2);
                    if(kv.length!=2)
                        continue;
                    Course course=Course.fromString(kv[0].trim());
                    if(course==null) {
                        System.err.println("Unknown course '"+kv[0]+"' for student id="+id);
                        continue;
                    }
                    try {
                        double grade=Double.parseDouble(kv[1].trim());
                        s.addOrUpdateCourseGrade(course,grade);
                    }
                    catch(NumberFormatException nfe) {
                        System.err.println("Bad grade '"+kv[1]+"' for student id="+id);
                    }
                }
            }
            return s;
        } catch(NumberFormatException e) { return null; }
    }

    private String formatStudent(Student s) {
        return s.getId() + "|" + escapeField(s.getName()) + "|" + escapeField(s.getEmail()) + "|" + s.coursesToString();
    }

    private String escapeField(String input) {
        if (input == null) return "";
        // replace pipe and newlines with spaces
        return input.replace("|", " ")
                .replace("\n", " ")
                .replace("\r", " ");
    }

}