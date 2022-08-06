package sda.studentmanagement.studentmanager.services;

import org.springframework.data.domain.Page;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.dto.AddCourseFormDto;
import sda.studentmanagement.studentmanager.projections.CourseDataChartsProjection;
import sda.studentmanagement.studentmanager.projections.CourseDataProjection;

import java.util.HashMap;
import java.util.List;

public interface CourseService {
    List<Course> getCourses();

    List<CourseDataChartsProjection> getCoursesCharts();

    List<HashMap<Object, Object>> getCourseCountCharts();

    Page<Course> getPaginatedCourses(int pageNumber, int pageSize);

    List<Course> getUserCourses(long userId, boolean userCourses);

    List<String> getUserCourseNames(long userId);

    List<CourseDataProjection> getCoursesData();

    Course getCourse(long courseId);

    void addUserToCourse(long userId, long courseId);

    void removeUserFromCourse(long userId, long courseId);

    void saveCourse(AddCourseFormDto addCourseForm);

    Course savePopulateCourse(Course course);

    Course editCourse(Course course);

    void deleteCourse(long courseId);
}