package sda.studentmanagement.studentmanager.domain;


import lombok.Data;
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
public class Professor {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Date dob;

    @Column
    private String mobileNo;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Date joinedDate;


    public Professor() {
    }

}
