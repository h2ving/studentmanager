package sda.studentmanagement.studentmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sda.studentmanagement.studentmanager.domain.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query(value = "SELECT p FROM Course p JOIN User u ON p.userId = u.id WHERE u.id = :userId")
    List<Course> findAllActive(@Param("userId") Integer userId);
}
