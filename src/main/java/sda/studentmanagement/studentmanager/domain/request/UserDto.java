package sda.studentmanagement.studentmanager.domain.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import sda.studentmanagement.studentmanager.services.validations.PasswordMatches;
import sda.studentmanagement.studentmanager.services.validations.ValidEmail;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@PasswordMatches
public class UserDto {

    //!!!!! I'm not sure how to use UserDto, UserLoginDto with JWT, or do we even need them? because login method already works and no more configuration is needed

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

    @NotNull
    @NotEmpty
    private LocalDate DOB;

    @NotNull
    @NotEmpty
    private String mobile;

    @NotNull
    @NotEmpty
    private String role;

    @NotNull
    @NotEmpty
    private String gender;
}
