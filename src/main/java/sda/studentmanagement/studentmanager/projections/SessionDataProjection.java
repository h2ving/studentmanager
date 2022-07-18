package sda.studentmanagement.studentmanager.projections;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface SessionDataProjection {
    Long getId();

    LocalDateTime getStartDateTime();

    Integer getAcademicHours();

    String getDescription();

    @Value("#{target.course.name}")
    String getCourse();
}
