package org.var11.application;

public class Course {
    private int id;
    private String title;
    private int hours;

    public Course(String title, int hours) {
        this.title = title;
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getHours() {
        return hours;
    }
}
