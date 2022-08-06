package sda.studentmanagement.studentmanager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddCourseFormDto {
    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer academicHours;

    private Boolean remote;
}
