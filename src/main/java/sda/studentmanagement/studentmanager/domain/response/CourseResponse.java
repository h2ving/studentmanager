package sda.studentmanagement.studentmanager.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CourseResponse {
    private int id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private int academicHours;
    private int remote;
    private int userId;

    public CourseResponse(int id, String name, String description,
                          Date startDate, Date endDate, int academicHours,
                          int remote, int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.academicHours = academicHours;
        this.remote = remote;
        this.userId = userId;
    }
}
