package sda.studentmanagement.studentmanager.projections;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface CourseDataProjection {
    Long getId();

    String getName();

    String getDescription();

    LocalDate getStartDate();

    LocalDate getEndDate();

    Integer getAcademicHours();

    Boolean getRemote();

    @Value("#{target.getStudentsCount()}")
    int getStudentsCount();
}
