package sda.studentmanagement.studentmanager.domain.requestDto;

import lombok.Data;

@Data
public class RoleToUserFormDto {
    private String userEmail;
    private String roleName;

}
