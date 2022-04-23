package sda.studentmanagement.studentmanager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.projections.UserView;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);
    List<UserView> findAllProjectedBy();
    User findById(long id);
    User findOneById(Long id);
}
