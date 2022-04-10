package sda.studentmanagement.studentmanager.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CourseRequest {
    //!!!!! Don't think we need this one as well
    private String name;

    private String description;

    private Date startDate;

    private Date endDate;

    private int academicHours;

    private int remote;

    public CourseRequest() {
    }
}
