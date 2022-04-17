package sda.studentmanagement.studentmanager.projections;

import org.springframework.beans.factory.annotation.Value;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserView {

    Long getId();

    String getFirstName();

    String getLastName();

    String getEmail();

    Role getRoles();

    String getGender();

    LocalDate getDOB();

    String getMobile();

    LocalDate getCreatedAt();

    LocalDateTime getUpdatedAt();

    @Value("#{target.age}")
    int getAge();

    List<CourseDTO> getCoursesAssigned();
}