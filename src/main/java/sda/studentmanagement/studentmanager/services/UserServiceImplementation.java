package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.studentmanagement.studentmanager.domain.Role;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.repositories.RoleRepository;
import sda.studentmanagement.studentmanager.repositories.UserRepository;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            log.error("User not found");

            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User was found: {}", email);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        // Todo: Our domain should be AppUser? instead of User
        // Return new Spring Security User
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) throws EntityExistsException {
        log.info("saving new user {} to db", user.getEmail());

        User findUser = userRepository.findByEmail(user.getEmail());

        if (findUser != null) {
            throw new EntityExistsException("User with Email " + user.getEmail() + " already exists");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            return userRepository.save(user);
        }
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {} to db", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);

        // Todo: Validate if user already exists
    }

    @Override
    public User getUser(String email) {
        // Todo: Validate different User roles? e.g. student can't access admin user details

        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id);
    }
}
