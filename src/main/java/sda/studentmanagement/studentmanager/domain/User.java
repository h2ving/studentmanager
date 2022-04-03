package sda.studentmanagement.studentmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min = 6, max = 45)
    private String email;

    @NotBlank
    @Size(min = 6, max = 255)
    private String password;

    @NotBlank
    @Size(max = 45)
    private String role;

    @NotBlank
    @Size(max = 45)
    private String firstName;

    @NotBlank
    @Size(max = 45)
    private String lastName;

    @NotBlank
    private LocalDate DOB;

    @Size(max = 45)
    private String mobile;

    @CreatedDate
    private LocalDate createdAt;
}
