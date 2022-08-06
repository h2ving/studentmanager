package sda.studentmanagement.studentmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.projections.CourseDataChartsProjection;
import sda.studentmanagement.studentmanager.projections.CourseDataProjection;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAll();

    List<CourseDataChartsProjection> findAllCourseUsersChartsProjectedBy();

    List<CourseDataProjection> findAllProjectedBy();

    Course findById(long courseId);
}
