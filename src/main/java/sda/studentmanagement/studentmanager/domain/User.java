package sda.studentmanagement.studentmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import sda.studentmanagement.studentmanager.projections.CourseDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private LocalDate DOB;

    @Size(max = 45)
    private String mobile;

    @Transient
    public int getAge() {
        Period age = this.DOB.until(LocalDate.now());
        return age.getYears();
    }

    @CreatedDate
    private final LocalDate createdAt = LocalDate.now();

    @LastModifiedDate
    private final LocalDateTime updatedAt = LocalDateTime.now();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "course_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "course_id")
    List<Course> courses;

    @Transient
    public List<CourseDTO> getCoursesAssigned() {
        List<CourseDTO> courseAssigned = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for (Course course : this.courses)
        {
            courseAssigned.add(modelMapper.map(course, CourseDTO.class));
        }
        return courseAssigned;
    }
}
