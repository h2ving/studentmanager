package sda.studentmanagement.studentmanager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sda.studentmanagement.studentmanager.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findById(long id);
}
