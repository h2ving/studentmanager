package sda.studentmanagement.studentmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 45)
    private String firstName;

    @NotBlank
    @Size(max = 45)
    private String lastName;

    @NotBlank
    @Size(min = 6, max = 45)
    private String email;

    @NotBlank
    @Size(min = 6, max = 255)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @NotBlank
    @Size(max = 45)
    private String gender;

    // @Past
    private LocalDate DOB;

    @Size(max = 45)
    private String mobile;

    @Transient
    public int getAge()
    {
        Period age = this.DOB.until(LocalDate.now());
        return age.getYears();
    };

    @CreatedDate
    private final LocalDate createdAt = LocalDate.now();
}
