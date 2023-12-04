package org.var11.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String DATABASE_URL = "jdbc:sqlite:application.db";
    private Connection connection;

    public Database() {
        try {
            this.connection = DriverManager.getConnection(DATABASE_URL);
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void createTables() {
        String createTeachersTable = "CREATE TABLE IF NOT EXISTS teachers ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "age INTEGER NOT NULL);";

        String createCoursesTable = "CREATE TABLE IF NOT EXISTS courses ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT NOT NULL,"
                + "hours INTEGER NOT NULL);";

        String createTeacherCoursesTable = "CREATE TABLE IF NOT EXISTS teacher_courses ("
                + "teacher_id INTEGER,"
                + "course_id INTEGER,"
                + "FOREIGN KEY (teacher_id) REFERENCES teachers (id),"
                + "FOREIGN KEY (course_id) REFERENCES courses (id),"
                + "PRIMARY KEY (teacher_id, course_id));";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTeachersTable);
            statement.execute(createCoursesTable);
            statement.execute(createTeacherCoursesTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertTeacher(String name, int age) {
        String query = "INSERT INTO teachers (name, age) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertCourse(String title, int hours) {
        String query = "INSERT INTO courses (title, hours) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, hours);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void assignCourseToTeacher(int teacherId, int courseId) {
        String query = "INSERT INTO teacher_courses (teacher_id, course_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, teacherId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateTeacher(int teacherId, String name, int age) {
        String query = "UPDATE teachers SET name = ?, age = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setInt(3, teacherId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateCourse(int courseId, String title, int hours) {
        String query = "UPDATE courses SET title = ?, hours = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, hours);
            preparedStatement.setInt(3, courseId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeTeacher(Teacher teacher) {
        String query = "DELETE FROM teachers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, teacher.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeCourse(Course course) {
        String query = "DELETE FROM courses WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, course.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getCourseIdByTitle(String title) {
        String query = "SELECT id FROM courses WHERE title = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT * FROM teachers";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");

                Teacher teacher = new Teacher(name, age);
                teacher.setId(id);
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int hours = resultSet.getInt("hours");

                Course course = new Course(title, hours);
                course.setId(id);
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    public List<Course> getCoursesForTeacher(Teacher teacher) {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT courses.* FROM courses "
                + "INNER JOIN teacher_courses ON courses.id = teacher_courses.course_id "
                + "WHERE teacher_courses.teacher_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, teacher.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    int hours = resultSet.getInt("hours");

                    Course course = new Course(title, hours);
                    course.setId(id);
                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}