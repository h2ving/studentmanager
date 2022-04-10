package sda.studentmanagement.studentmanager.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    //!!!!! I'm not sure how to use UserDto, UserLoginDto with JWT, or do we even need them? because login method already works and no more configuration is needed
    private String email;
    private String password;

    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserLoginDto() {
    }
}
