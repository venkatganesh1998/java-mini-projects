package com.example.studentmanagement.service;
import com.example.studentmanagement.dao.StudentDAO;
import com.example.studentmanagement.model.Student;
import java.sql.SQLException;

public class StudentService {
    private StudentDAO studentDAO = new StudentDAO();

    public void registerStudent(Student student) {
        try {
            studentDAO.addStudent(student);
        } catch (SQLException e) {
            System.err.println("Error registering student: " + e.getMessage());
        }
    }
}
