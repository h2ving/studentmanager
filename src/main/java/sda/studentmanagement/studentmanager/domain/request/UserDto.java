package sda.studentmanagement.studentmanager.domain.request;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import sda.studentmanagement.studentmanager.services.validations.PasswordMatches;
import sda.studentmanagement.studentmanager.services.validations.ValidEmail;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@PasswordMatches
public class UserDto {

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String matchingPassword;
}
