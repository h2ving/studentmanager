package sda.studentmanagement.studentmanager.projections;

import org.springframework.beans.factory.annotation.Value;
import sda.studentmanagement.studentmanager.domain.Role;
import sda.studentmanagement.studentmanager.dto.CourseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserDataProjection {
    Long getId();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getGender();

    Role getRoles();

    String getMobile();

    LocalDate getDOB();

    LocalDate getCreatedAt();

    LocalDateTime getUpdatedAt();

    @Value("#{target.age}")
    int getAge();

    List<CourseDTO> getCoursesAssigned();
}
