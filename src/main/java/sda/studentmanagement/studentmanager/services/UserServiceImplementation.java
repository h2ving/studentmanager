package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.studentmanagement.studentmanager.dto.EditUserFormDto;
import sda.studentmanagement.studentmanager.dto.ResetUserPasswordFormDto;
import sda.studentmanagement.studentmanager.projections.UserDataProjection;
import sda.studentmanagement.studentmanager.domain.Role;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.repositories.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final SessionRepository sessionRepository;
    private final RoleRepository roleRepository;
    private final AttendanceRepository attendanceRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        // Todo: AppUser/User

        User user = userRepository.findByEmail(userEmail);

        if (user == null) {
            log.error("Invalid Log in: {}", userEmail);

            throw new UsernameNotFoundException("User not found");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDataProjection> getUsersData() {
        return userRepository.findAllProjectedBy();
    }

    @Override
    public User getUser(long userId) {
        User user = userRepository.findById(userId);

        if (user == null) {
            log.error("User with Id {} not found", userId);

            throw new EntityNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public User getUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        if (user == null) {
            // Todo Exception
            log.error("User with Email {} not found", userEmail);
        }

        return user;
    }

    @Override
    public HashMap<Object, Object> getUserData(long userId) {
        // Todo: getUserAverageGrade

        UserDataProjection user = userRepository.getUserById(userId);

        Number averageGrade = gradeRepository.getAverageByUser(userId);
        Date nextSession = sessionRepository.getNextSessionByUser(userId);
        Number averageAttendancePercentage = attendanceRepository.getAverageAttendanceByUser(userId);
        String redirectURI = user.getRoles().getName().toLowerCase(Locale.ROOT) + "/" + user.getId();

        HashMap<Object, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("averageGrade", averageGrade);
        response.put("nextSession", nextSession);
        response.put("attendancePercentage", averageAttendancePercentage);
        response.put("redirectURI", redirectURI);

        return response;
    }

    @Override
    public void addRoleToUser(String userEmail, String roleName) {
        // Todo: Validation, variables
        log.info("Adding role {} to user {}", roleName, userEmail);

        User user = getUser(userEmail);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public void resetUserPassword(long userId, ResetUserPasswordFormDto userForm) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = getUser(userId);

        if (userForm.getOldPassword().isEmpty() || userForm.getNewPassword().isEmpty() || userForm.getRepeatNewPassword().isEmpty()) {
            throw new EmptyStackException();
        }

        if (!Objects.equals(userForm.getNewPassword(), userForm.getRepeatNewPassword())) {
            throw new InputMismatchException("Passwords do not match");
        }

        if (!encoder.matches(userForm.getOldPassword(), user.getPassword())) {
            throw new InputMismatchException("Invalid Password");
        }

        if (encoder.matches(userForm.getNewPassword(), user.getPassword())) {
            throw new InputMismatchException("New password cannot be the same as the old");
        }

        String encodedPassword = encoder.encode(userForm.getNewPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public User saveUser(User user) {
        // Todo: Switch to Bcrypt
        // Todo: Validation

        log.info("New User: {}", user.getEmail());

        User appUser = userRepository.findByEmail(user.getEmail());

        if (appUser == null) {
            // user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setPassword(user.getPassword());

            return userRepository.save(user);
        } else {
            throw new EntityExistsException("User with Email " + user.getEmail() + " already exists");
        }
    }

    @Override
    public UserDataProjection editUser(EditUserFormDto userForm) throws ConstraintViolationException {
        User user = getUser(userForm.getId());

        if (user != null) {
            if (userForm.getDOB() == null) {
                throw new IllegalArgumentException("Date of Birth cannot be empty");
            }

            user.setFirstName(userForm.getFirstName());
            user.setLastName(userForm.getLastName());
            user.setEmail(userForm.getEmail());
            user.setGender(userForm.getGender());
            user.setMobile(userForm.getMobile());
            user.setDOB(userForm.getDOB());

            return userRepository.getUserById(userForm.getId());
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    @Override
    public Role saveRole(Role role) {
        // Todo

        log.info("New role: {}", role.getName());
        return roleRepository.save(role);
    }
}
