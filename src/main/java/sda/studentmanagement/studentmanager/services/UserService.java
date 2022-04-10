package sda.studentmanagement.studentmanager.services;

/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.domain.request.UserDto;
import sda.studentmanagement.studentmanager.repositories.UserRepository;*/

import sda.studentmanagement.studentmanager.domain.Role;
import sda.studentmanagement.studentmanager.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    User getUser(String email);
    List<User> getUsers();

    //!!!!! Made just the methods in here and in Implementation we override them
    // The logic with jwt is little bit different so this is not needed here
    // Look -> public User saveUser(User user) in Implementation
/*
    @Autowired
    UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User createNewUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMobile(userDto.getMobile());
        user.setDOB(userDto.getDOB());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setGender(userDto.getGender());
        user.setRole(userDto.getRole());

        User savedUser = userRepository.save(user);
        //TODO: Implement authorities for student/professor and add saveduser to that repository.
        return savedUser;
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }*/
}
