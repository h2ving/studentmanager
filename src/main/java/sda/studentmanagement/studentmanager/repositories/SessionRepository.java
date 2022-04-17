package sda.studentmanagement.studentmanager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sda.studentmanagement.studentmanager.domain.Session;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findOneById(Long id);
}
