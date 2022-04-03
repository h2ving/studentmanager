package sda.studentmanagement.studentmanager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sda.studentmanagement.studentmanager.domain.Grade;

public interface GradeRepository extends JpaRepository<Grade, Integer> {

}
