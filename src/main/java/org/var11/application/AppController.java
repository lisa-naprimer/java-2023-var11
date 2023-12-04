package org.var11.application;

import java.util.List;

public class AppController {
    private final Database database;

    public AppController(Database database) {
        this.database = database;
    }
    public void addTeacher(String fullName, int age) {
        database.insertTeacher(fullName, age);
    }
    public void addCourseForTeacher(Teacher teacher, String title, int hours) {
        database.insertCourse(title, hours);
        int courseId = database.getCourseIdByTitle(title);
        database.assignCourseToTeacher(teacher.getId(), courseId);
    }
    public void removeTeacher(Teacher teacher) {
        database.removeTeacher(teacher);
    }
    public void removeCourse(Course course) {
        database.removeCourse(course);
    }
    public void editTeacher(Teacher teacher, String fullName, int age) {
        database.updateTeacher(teacher.getId(), fullName, age);
    }
    public void editCourse(Course course, String title, int hours) {
        database.updateCourse(course.getId(), title, hours);
    }
    public List<Teacher> getAllTeachers() {
        return database.getAllTeachers();
    }
    public List<Course> getAllCourses() {
        return database.getAllCourses();
    }
    public List<Course> getCoursesForTeacher(Teacher teacher) {
        return database.getCoursesForTeacher(teacher);
    }
}
