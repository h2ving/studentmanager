package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.studentmanagement.studentmanager.dto.AddUserFormDto;
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
    public Page<User> getPaginatedUsers(int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);

        return userRepository.findAll(paging);
    }

    @Override
    public List<HashMap<Object, Object>> getUsersCountCharts() {
        int students = 0;
        int professors = 0;
        int admins = 0;

        for (User user : getUsers()) {
            students += user.getRoles().stream().filter(role -> Objects.equals(role.getName(), "Student")).count();
            professors += user.getRoles().stream().filter(role -> Objects.equals(role.getName(), "Professor")).count();
            admins += user.getRoles().stream().filter(role -> Objects.equals(role.getName(), "Admin")).count();
        }

        List<HashMap<Object, Object>> listOfUsers = new ArrayList<>();

        HashMap<Object, Object> studentMap = new HashMap<>();
        studentMap.put("name", "Students");
        studentMap.put("value", students);

        HashMap<Object, Object> professorMap = new HashMap<>();
        professorMap.put("name", "Professors");
        professorMap.put("value", professors);

        HashMap<Object, Object> adminMap = new HashMap<>();
        adminMap.put("name", "Admins");
        adminMap.put("value", admins);

        listOfUsers.add(studentMap);
        listOfUsers.add(professorMap);
        listOfUsers.add(adminMap);

        return listOfUsers;
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
    public void saveUser(AddUserFormDto addUserForm) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();

        if (addUserForm.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }

        user.setFirstName(addUserForm.getFirstName());
        user.setLastName(addUserForm.getLastName());
        user.setEmail(addUserForm.getEmail());
        user.setCourses(new ArrayList<>());
        user.setGender(addUserForm.getGender());
        user.setDOB(addUserForm.getDob());
        user.setMobile(addUserForm.getMobile());

        String encodedPassword = encoder.encode(addUserForm.getPassword());
        user.setPassword(encodedPassword);

        Role userRole = roleRepository.findByName(addUserForm.getRole());

        if (userRole == null) {
            throw new EntityNotFoundException("User role not found. Please make sure it's not empty.");
        }

        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);

        userRepository.save(user);
    }

    @Override
    public User savePopulateUser(User user) {
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

            if (!userForm.getRole().isBlank()) {
                Role newRole = new Role();
                newRole.setName(userForm.getRole());

                roleRepository.save(newRole);

                List<Role> roles = new ArrayList<>();
                roles.add(newRole);

                user.getRoles().clear();

                user.setRoles(roles);
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
