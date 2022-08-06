package sda.studentmanagement.studentmanager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EditUserFormDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String role;

    private String gender;

    private LocalDate DOB;

    private String mobile;
}
