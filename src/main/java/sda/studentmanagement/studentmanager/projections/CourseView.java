package sda.studentmanagement.studentmanager.projections;
import java.time.LocalDate;

public interface CourseView {
    Long getId();

    String getName();

    String getDescription();

    LocalDate getStartDate();

    LocalDate getEndDate();

    Integer getAcademicHours();

    Boolean isRemote();
}
