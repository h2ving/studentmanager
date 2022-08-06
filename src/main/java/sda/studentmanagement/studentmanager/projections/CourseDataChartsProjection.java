package sda.studentmanagement.studentmanager.projections;

import org.springframework.beans.factory.annotation.Value;

public interface CourseDataChartsProjection {
    String getName();

    @Value("#{target.getStudentsCount()}")
    int getValue();
}
