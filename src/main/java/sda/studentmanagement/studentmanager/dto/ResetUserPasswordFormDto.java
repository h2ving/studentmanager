package sda.studentmanagement.studentmanager.dto;

import lombok.Data;

@Data
public class ResetUserPasswordFormDto {
    private String oldPassword;

    private String newPassword;

    private String repeatNewPassword;
}
