# Student Management System 

- This project builds a student management system using Java and a database, allowing users to add, view, update, and delete student, course, instructor, and enrollment information, while ensuring data integrity and security.

## Project Structure
```
com.example.studentmanagement/
├── dao/
│   ├── StudentDAO.java
│   ├── CourseDAO.java
│   ├── EnrollmentDAO.java
│   └── InstructorDAO.java
├── model/
│   ├── Student.java
│   ├── Course.java
│   ├── Enrollment.java
│   └── Instructor.java
├── util/
│   └── DatabaseConnection.java
├── service/
│   ├── StudentService.java
│   ├── CourseService.java
│   ├── EnrollmentService.java
│   └── InstructorService.java
├── exception/
│   └── DatabaseException.java
└── main/
    └── StudentManagementApp.java
```

## Database Schema with Relationships

```sql
-- Students table
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    age INT,
    address VARCHAR(200),
    join_date DATE
);

-- Instructors table
CREATE TABLE instructors (
    instructor_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    specialization VARCHAR(100),
    hire_date DATE
);

-- Courses table
CREATE TABLE courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    credits INT NOT NULL,
    instructor_id INT,
    FOREIGN KEY (instructor_id) REFERENCES instructors(instructor_id)
);

-- Enrollments table (junction table for many-to-many relationship)
CREATE TABLE enrollments (
    enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    enrollment_date DATE NOT NULL,
    grade VARCHAR(2),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id),
    UNIQUE KEY (student_id, course_id) -- Prevents duplicate enrollments
);
```

## Relationship Types Implemented

1. **One-to-Many**: Instructor to Courses
   - One instructor can teach multiple courses
   - Each course has one instructor

2. **Many-to-Many**: Students to Courses (through Enrollments)
   - Students can enroll in multiple courses
   - Courses can have multiple students enrolled
   - The Enrollment table serves as a junction table

## Updated Package Details

### 1. Model Classes
- **Student.java**: Core student information
- **Course.java**: Course details with instructor reference
- **Instructor.java**: Instructor information
- **Enrollment.java**: Junction entity for student-course relationship with additional attributes

### 2. DAO Layer
- **StudentDAO.java**: CRUD for student data
- **CourseDAO.java**: CRUD for course data, including instructor relationship
- **InstructorDAO.java**: CRUD for instructor data
- **EnrollmentDAO.java**: Manages student-course relationships, including:
  - `enrollStudentInCourse(int studentId, int courseId)`
  - `getCoursesForStudent(int studentId)`
  - `getStudentsInCourse(int courseId)`
  - `updateGrade(int studentId, int courseId, String grade)`

### 3. Service Layer
- **StudentService.java**: Student management logic
- **CourseService.java**: Course management logic
- **InstructorService.java**: Instructor management logic
- **EnrollmentService.java**: Handles enrollment business rules, including:
  - Validates student and course existence before enrollment
  - Prevents duplicate enrollments
  - Manages grade assignments and updates

## Implementation Details for Relationships

### Join Operations
- Implements SQL JOIN operations to fetch related data:
  ```java
  // Example methods in DAO classes
  List<Course> getCoursesWithInstructors();
  List<Student> getStudentsWithEnrollments();
  ```

### Cascading Operations
- Implements cascading delete for certain relationships:
  - When deleting a course, handle existing enrollments
  - When deleting a student, handle their enrollments

### Transaction Management
- Enhanced to ensure data integrity across related tables:
  ```java
  // Example enrollment transaction
  connection.setAutoCommit(false);
  try {
      // Insert enrollment record
      // Update course enrollment count
      connection.commit();
  } catch (SQLException e) {
      connection.rollback();
      throw new DatabaseException("Enrollment failed", e);
  } finally {
      connection.setAutoCommit(true);
  }
  ```

### Reporting Queries
- Implementation of complex queries spanning multiple tables:
  - List courses with enrollment counts
  - Find most popular courses
  - Calculate grade distribution for a course
