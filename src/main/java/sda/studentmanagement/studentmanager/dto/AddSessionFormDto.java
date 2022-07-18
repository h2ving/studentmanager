package sda.studentmanagement.studentmanager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddSessionFormDto {
    private long courseId;

    private String description;

    private LocalDateTime startDateTime;

    private Integer academicHours;
}
