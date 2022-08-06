package sda.studentmanagement.studentmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import sda.studentmanagement.studentmanager.dto.UserToCourseDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Course Name can't be empty")
    @Size(max = 100, message = "Course Name is restricted with 100 characters")
    private String name;

    @NotBlank(message = "Description can't be empty")
    @Lob
    private String description;

    @NotNull(message = "Start Date must be selected")
    private LocalDate startDate;

    @NotNull(message = "End Date must be selected")
    private LocalDate endDate;

    @NotNull(message = "Duration can't be empty")
    private Integer academicHours;

    private Boolean remote;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "course_user",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "user_id")
    @ToString.Exclude
    private List<User> users;

    @Transient
    public int getStudentsCount(){
        return this.users.size();
    }

    @Transient
    public List<UserToCourseDTO> getUsersAssigned()
    {
        List<UserToCourseDTO> usersAssigned = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for (User user : this.getUsers()) {
            usersAssigned.add(modelMapper.map(user, UserToCourseDTO.class));
        }
        return usersAssigned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Course course = (Course) o;
        return id != null && Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
