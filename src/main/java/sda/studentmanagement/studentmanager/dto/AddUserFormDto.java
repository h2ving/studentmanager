package sda.studentmanagement.studentmanager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddUserFormDto {
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String role;

    private String gender;

    private LocalDate dob;

    private String mobile;
}
