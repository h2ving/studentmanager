package sda.studentmanagement.studentmanager.services;

import sda.studentmanagement.studentmanager.domain.Course;

import java.util.List;

public interface CourseService {
    Course saveCourse(Course course);
    void deleteCourse(String name);
    Course editCourse(Course course);
    Course getCourse(String name);
    List<Course> getCourses();
    void addUserToCourse(String email, String courseName);
}