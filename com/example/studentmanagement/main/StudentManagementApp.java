package com.example.studentmanagement.main;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;

public class StudentManagementApp {
    public static void main(String[] args) {
        StudentService studentService = new StudentService();
        Student student = new Student(0, "John Doe", "john@example.com");
        studentService.registerStudent(student);
        System.out.println("Student management operations completed.");
    }
}
