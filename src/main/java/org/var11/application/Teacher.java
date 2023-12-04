package org.var11.application;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
    private int id;
    private String fullName;
    private int age;
    private List<Course> courses;

    public Teacher(String fullName, int age) {
        this.fullName = fullName;
        this.age = age;
        this.courses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public void setAge(int newAge) {
        this.age = newAge;
    }
}
