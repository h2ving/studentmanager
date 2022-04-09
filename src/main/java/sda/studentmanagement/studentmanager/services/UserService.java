package sda.studentmanagement.studentmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.domain.request.UserDto;
import sda.studentmanagement.studentmanager.repositories.UserRepository;

@Service
public class UserService {

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
    }
}
