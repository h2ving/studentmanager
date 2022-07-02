package sda.studentmanagement.studentmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sda.studentmanagement.studentmanager.dto.CourseDTO;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name cannot be empty")
    @Size(min = 2, max = 45, message = "First name is limited from 2 to 45 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Size(min = 2, max = 45, message = "Last name is limited from 2 to 45 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be empty")
    @Size(min = 6, message = "Email must be at least 6 characters")
    @Size(max = 45, message = "Email cannot exceed 45 characters")
    @Email(message = "Invalid Email Address")
    private String email;

    @NotBlank
    @Size(min = 6, max = 255)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @NotBlank(message = "Gender cannot be empty")
    private String gender;

    @Past(message = "Invalid Date of Birth")
    private LocalDate DOB;

    @Size(max = 45, message = "Mobile number cannot exceed 45 digits")
    @Size(min = 5, message = "Mobile number must be at least 5 digits")
    private String mobile;

    @Transient
    public int getAge() {
        Period age = this.DOB.until(LocalDate.now());
        return age.getYears();
    }

    @CreatedDate
    private final LocalDate createdAt = LocalDate.now();

    @LastModifiedDate
    private LocalDateTime updatedAt = LocalDateTime.now();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "course_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "course_id")
    @ToString.Exclude
    List<Course> courses;

    @Transient
    public List<CourseDTO> getCoursesAssigned() {
        List<CourseDTO> courseAssigned = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for (Course course : this.courses) {
            courseAssigned.add(modelMapper.map(course, CourseDTO.class));
        }

        return courseAssigned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
