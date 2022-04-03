package sda.studentmanagement.studentmanager.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private int academicHours;

    @Column
    private int remote;

    @Column
    private int userId;

    public Course(String name, String description, Date startDate, Date endDate, int academicHours, int remote, int userId) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.academicHours = academicHours;
        this.remote = remote;
        this.userId = userId;
    }
}
