package sda.studentmanagement.studentmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserToCourseDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String gender;

    private LocalDate DOB;

    private String mobile;

    public int age;
}
