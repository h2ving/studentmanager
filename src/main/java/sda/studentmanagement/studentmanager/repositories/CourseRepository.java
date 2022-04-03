package sda.studentmanagement.studentmanager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.User;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
