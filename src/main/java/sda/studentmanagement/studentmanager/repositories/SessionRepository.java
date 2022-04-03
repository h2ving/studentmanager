package sda.studentmanagement.studentmanager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sda.studentmanagement.studentmanager.domain.Session;

public interface SessionRepository extends JpaRepository<Session, Integer> {

}
