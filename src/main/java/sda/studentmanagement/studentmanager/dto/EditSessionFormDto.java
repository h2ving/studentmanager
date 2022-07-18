package sda.studentmanagement.studentmanager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EditSessionFormDto {
    private String description;

    private LocalDateTime startDateTime;

    private Integer academicHours;
}
