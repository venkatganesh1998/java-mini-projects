package com.example.studentmanagement.dao;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public void addStudent(Student student) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "INSERT INTO students (name, email) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, student.getName());
        stmt.setString(2, student.getEmail());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}
