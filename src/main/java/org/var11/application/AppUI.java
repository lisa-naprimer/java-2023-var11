package org.var11.application;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AppUI {
    private AppController controller;
    private JFrame frame;
    public AppUI(AppController controller) {
        this.controller = controller;
        createUI();
    }
    public void createUI() {
        JFrame frame = new JFrame("Управление базой данных");
        frame.setSize(800, 600);

        JButton addButton = new JButton("Добавить преподавателя");
        JButton addCourseButton = new JButton("Добавить курс");
        JButton removeButton = new JButton("Удалить преподавателя");
        JButton removeCourseButton = new JButton("Удалить курс");
        JButton editButton = new JButton("Редактировать преподавателя");
        JButton editCourseButton = new JButton("Редактировать курс");
        JButton showAllButton = new JButton("Показать все курсы");
        JButton showTeachersButton = new JButton("Показать преподавателей");
        JButton showTeachersAndCoursesButton = new JButton("Показать преподавателей и их курсы");

        addButton.addActionListener(e -> {
            String fullName = JOptionPane.showInputDialog("Введите полное имя преподавателя:");
            String ageStr = JOptionPane.showInputDialog("Введите возраст преподавателя:");
            try {
                int age = Integer.parseInt(ageStr);
                controller.addTeacher(fullName, age);
                JOptionPane.showMessageDialog(frame, "Преподаватель успешно добавлен!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Неверный формат возраста. Введите целое число.");
            }
        });

        addCourseButton.addActionListener(e -> {
            String title = JOptionPane.showInputDialog("Введите название курса:");
            String hoursStr = JOptionPane.showInputDialog("Введите количество часов:");
            try {
                int hours = Integer.parseInt(hoursStr);
                List<Teacher> teachers = controller.getAllTeachers();
                String[] teacherNames = teachers.stream().map(Teacher::getFullName).toArray(String[]::new);
                String teacherName = (String) JOptionPane.showInputDialog(frame,
                        "Выберите преподавателя для курса:",
                        "Выбор преподавателя",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        teacherNames,
                        teacherNames[0]);
                Teacher selectedTeacher = teachers.stream()
                        .filter(t -> t.getFullName().equals(teacherName))
                        .findFirst()
                        .orElse(null);
                if (selectedTeacher != null) {
                    controller.addCourseForTeacher(selectedTeacher, title, hours);
                    JOptionPane.showMessageDialog(frame, "Курс успешно добавлен!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Преподаватель не выбран.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Неверный формат количества часов. Введите целое число.");
            }
        });

        removeButton.addActionListener(e -> {
            List<Teacher> teachers = controller.getAllTeachers();
            String[] teacherNames = teachers.stream().map(Teacher::getFullName).toArray(String[]::new);
            String teacherName = (String) JOptionPane.showInputDialog(frame,
                    "Выберите преподавателя для удаления:",
                    "Выбор преподавателя",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    teacherNames,
                    teacherNames[0]);
            Teacher selectedTeacher = teachers.stream()
                    .filter(t -> t.getFullName().equals(teacherName))
                    .findFirst()
                    .orElse(null);
            if (selectedTeacher != null) {
                controller.removeTeacher(selectedTeacher);
                JOptionPane.showMessageDialog(frame, "Преподаватель успешно удален!");
            } else {
                JOptionPane.showMessageDialog(frame, "Преподаватель не выбран.");
            }
        });

        removeCourseButton.addActionListener(e -> {
            List<Course> courses = controller.getAllCourses();
            if (courses.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Нет доступных курсов для удаления.");
                return;
            }

            String[] courseTitles = courses.stream().map(Course::getTitle).toArray(String[]::new);
            String courseTitle = (String) JOptionPane.showInputDialog(frame,
                    "Выберите курс для удаления:",
                    "Выбор курса",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    courseTitles,
                    courseTitles[0]);

            if (courseTitle != null && !courseTitle.isEmpty()) {
                Course selectedCourse = courses.stream()
                        .filter(c -> c.getTitle().equals(courseTitle))
                        .findFirst()
                        .orElse(null);

                if (selectedCourse != null) {
                    controller.removeCourse(selectedCourse);
                    JOptionPane.showMessageDialog(frame, "Курс успешно удален!");
                    updateCoursesList();
                } else {
                    JOptionPane.showMessageDialog(frame, "Курс не найден.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Курс не выбран.");
            }
        });

        editButton.addActionListener(e -> {
            List<Teacher> teachers = controller.getAllTeachers();
            String[] teacherNames = teachers.stream().map(Teacher::getFullName).toArray(String[]::new);
            String teacherName = (String) JOptionPane.showInputDialog(frame,
                    "Выберите преподавателя для редактирования:",
                    "Выбор преподавателя",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    teacherNames,
                    teacherNames[0]);
            Teacher selectedTeacher = teachers.stream()
                    .filter(t -> t.getFullName().equals(teacherName))
                    .findFirst()
                    .orElse(null);
            if (selectedTeacher != null) {
                String newFullName = JOptionPane.showInputDialog("Введите новое полное имя преподавателя:");
                String newAgeStr = JOptionPane.showInputDialog("Введите новый возраст преподавателя:");
                try {
                    int newAge = Integer.parseInt(newAgeStr);
                    controller.editTeacher(selectedTeacher, newFullName, newAge);
                    JOptionPane.showMessageDialog(frame, "Преподаватель успешно отредактирован!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Неверный формат нового возраста. Введите целое число.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Преподаватель не выбран.");
            }
        });

        editCourseButton.addActionListener(e -> {
            List<Course> courses = controller.getAllCourses();
            if (courses.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Нет доступных курсов для редактирования.");
                return;
            }

            String[] courseTitles = courses.stream().map(Course::getTitle).toArray(String[]::new);
            String courseTitle = (String) JOptionPane.showInputDialog(frame,
                    "Выберите курс для редактирования:",
                    "Выбор курса",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    courseTitles,
                    courseTitles[0]);

            if (courseTitle != null && !courseTitle.isEmpty()) {
                Course selectedCourse = courses.stream()
                        .filter(c -> c.getTitle().equals(courseTitle))
                        .findFirst()
                        .orElse(null);

                if (selectedCourse != null) {
                    String newTitle = JOptionPane.showInputDialog("Введите новое название курса:");
                    String newHoursStr = JOptionPane.showInputDialog("Введите новое количество часов:");

                    if (newTitle != null && !newTitle.isEmpty() && newHoursStr != null && !newHoursStr.isEmpty()) {
                        try {
                            int newHours = Integer.parseInt(newHoursStr);
                            controller.editCourse(selectedCourse, newTitle, newHours);
                            JOptionPane.showMessageDialog(frame, "Курс успешно отредактирован!");
                            updateCoursesList();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Неверный формат нового количества часов. Введите целое число.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Введите корректные данные для редактирования курса.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Курс не найден.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Курс не выбран.");
            }
        });

        showAllButton.addActionListener(e -> {
            List<Course> courses = controller.getAllCourses();
            StringBuilder coursesInfo = new StringBuilder("Список всех курсов:\n");
            for (Course course : courses) {
                coursesInfo.append(course.getTitle()).append(" (").append(course.getHours()).append(" часов)\n");
            }
            JOptionPane.showMessageDialog(frame, coursesInfo.toString());
        });

        showTeachersButton.addActionListener(e -> {
            List<Teacher> teachers = controller.getAllTeachers();
            StringBuilder teachersInfo = new StringBuilder("Список всех преподавателей:\n");
            for (Teacher teacher : teachers) {
                teachersInfo.append(teacher.getFullName()).append(" (").append(teacher.getAge()).append(" лет)\n");
            }

            JTextArea textArea = new JTextArea(teachersInfo.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(frame, scrollPane);
        });

        showTeachersAndCoursesButton.addActionListener(e -> {
            List<Teacher> teachers = controller.getAllTeachers();
            StringBuilder teachersAndCoursesInfo = new StringBuilder("Преподаватели и их курсы:\n");
            for (Teacher teacher : teachers) {
                teachersAndCoursesInfo.append("Преподаватель: ").append(teacher.getFullName()).append("\nКурсы:\n");

                List<Course> courses = controller.getCoursesForTeacher(teacher);
                if (courses.isEmpty()) {
                    teachersAndCoursesInfo.append("  Нет курсов\n");
                } else {
                    for (Course course : courses) {
                        teachersAndCoursesInfo.append("  ").append(course.getTitle()).append(" (").append(course.getHours()).append(" часов)\n");
                    }
                }
                teachersAndCoursesInfo.append("\n");
            }

            JTextArea textArea = new JTextArea(teachersAndCoursesInfo.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(frame, scrollPane);
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(addButton);
        panel.add(addCourseButton);
        panel.add(removeButton);
        panel.add(removeCourseButton);
        panel.add(editButton);
        panel.add(editCourseButton);
        panel.add(showAllButton);
        panel.add(showTeachersButton);
        panel.add(showTeachersAndCoursesButton);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    public void updateCoursesList() {
        List<Course> courses = controller.getAllCourses();
        StringBuilder coursesInfo = new StringBuilder("Список всех курсов:\n");
        for (Course course : courses) {
            coursesInfo.append(course.getTitle()).append(" (").append(course.getHours()).append(" часов)\n");
        }
        JOptionPane.showMessageDialog(frame, coursesInfo.toString());
    }
}