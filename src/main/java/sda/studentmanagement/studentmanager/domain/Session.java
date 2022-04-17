package sda.studentmanagement.studentmanager.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private LocalDateTime startDateTime;

    @NotNull
    private Integer academicHours;

    @NotBlank
    @Size(max = 255)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<User> user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
}
