package sda.studentmanagement.studentmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class Student {

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
    private Date joinedData;

    public Student() {
    }

}
