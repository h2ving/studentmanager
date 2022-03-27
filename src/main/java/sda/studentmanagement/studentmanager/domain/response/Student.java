package sda.studentmanagement.studentmanager.domain.response;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Student {


    private int id;
    private String firstName;
    private String lastName;
    private Date dob;
    private String mobileNo;
    private String email;
    private String password;
    private Date joinedData;

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getJoinedData() {
        return joinedData;
    }

    public void setJoinedData(Date joinedData) {
        this.joinedData = joinedData;
    }
}
