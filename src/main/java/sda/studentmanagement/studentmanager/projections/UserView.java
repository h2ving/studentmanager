package sda.studentmanagement.studentmanager.projections;

import org.springframework.beans.factory.annotation.Value;
import sda.studentmanagement.studentmanager.domain.Role;

import java.time.LocalDate;

public interface UserView {

    Long getId();

    String getFirstName();

    String getLastName();

    String getEmail();

    Role getRoles();

    String getGender();

    LocalDate getDOB();

    String getMobile();

    LocalDate getCreatedAt();

    @Value("#{target.age}")
    int getAge();
}