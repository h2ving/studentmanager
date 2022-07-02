package sda.studentmanagement.studentmanager.services;

import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.projections.CourseDataProjection;

import java.util.List;

public interface CourseService {
    List<Course> getCourses();

    List<Course> getUserCourses(long userId, boolean userCourses);

    List<String> getUserCourseNames(long userId);

    List<CourseDataProjection> getCoursesData();

    Course getCourse(long courseId);

    void addUserToCourse(long userId, long courseId);

    void removeUserFromCourse(long userId, long courseId);

    Course saveCourse(Course course);

    Course editCourse(Course course);

    void deleteCourse(long courseId);
}