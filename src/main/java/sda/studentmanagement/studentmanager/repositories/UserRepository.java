package sda.studentmanagement.studentmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.projections.UserDataProjection;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{
    List<User> findAll();

    User findById(long userId);

    User findByEmail(String userEmail);

    UserDataProjection getUserById(long userId);

    List<UserDataProjection> findAllProjectedBy();
}
