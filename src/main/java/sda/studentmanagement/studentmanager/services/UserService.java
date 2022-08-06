package sda.studentmanagement.studentmanager.services;

import org.springframework.data.domain.Page;
import sda.studentmanagement.studentmanager.dto.AddUserFormDto;
import sda.studentmanagement.studentmanager.dto.EditUserFormDto;
import sda.studentmanagement.studentmanager.dto.ResetUserPasswordFormDto;
import sda.studentmanagement.studentmanager.projections.UserDataProjection;
import sda.studentmanagement.studentmanager.domain.Role;
import sda.studentmanagement.studentmanager.domain.User;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;

public interface UserService {
    List<User> getUsers();

    Page<User> getPaginatedUsers(int pageNumber, int pageSize);

    List<HashMap<Object, Object>> getUsersCountCharts();

    List<UserDataProjection> getUsersData();

    User getUser(long userId);

    User getUser(String userEmail);

    HashMap<Object, Object> getUserData(long id);

    void addRoleToUser(String userEmail, String roleName);

    void resetUserPassword(long userId, ResetUserPasswordFormDto userForm);

    void saveUser(AddUserFormDto addUserForm);

    User savePopulateUser(User user);

    UserDataProjection editUser(EditUserFormDto userForm) throws ConstraintViolationException;

    Role saveRole(Role role);
}
