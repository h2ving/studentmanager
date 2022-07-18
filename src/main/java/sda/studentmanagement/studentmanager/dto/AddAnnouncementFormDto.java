package sda.studentmanagement.studentmanager.dto;

import lombok.Data;

@Data
public class AddAnnouncementFormDto {
    private String announcement;

    private long courseId;
}
