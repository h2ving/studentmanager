package sda.studentmanagement.studentmanager.services;

import sda.studentmanagement.studentmanager.projections.UserView;
import sda.studentmanagement.studentmanager.domain.Role;
import sda.studentmanagement.studentmanager.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    User getUser(String email);
    User getUserById(Long id);
    User getUser(Long id);
    List<User> getUsers();
    List<UserView> getUsersView();
}
