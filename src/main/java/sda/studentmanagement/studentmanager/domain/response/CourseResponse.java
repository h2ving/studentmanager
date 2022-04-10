package sda.studentmanagement.studentmanager.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class CourseResponse {
    private int id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int academicHours;
    private boolean remote;
    private int userId;


    //!!!!! Don't think we need this also since we use repository to get all and just send them from database with ResponseEntity
    // for example -> return ResponseEntity.ok().body(userService.getUsers());
    public CourseResponse(int id, String name, String description,
                          LocalDate startDate, LocalDate endDate, int academicHours,
                          boolean remote) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.academicHours = academicHours;
        this.remote = remote;
    }
}
