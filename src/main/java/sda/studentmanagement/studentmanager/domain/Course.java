package sda.studentmanagement.studentmanager.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import sda.studentmanagement.studentmanager.projections.UserToCourseDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Lob
    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private Integer academicHours;

    @NotNull
    private Boolean remote;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "course_user",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "user_id")
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
}
