package sda.studentmanagement.studentmanager.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.domain.Role;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.dto.AddUserFormDto;
import sda.studentmanagement.studentmanager.dto.EditUserFormDto;
import sda.studentmanagement.studentmanager.dto.ResetUserPasswordFormDto;
import sda.studentmanagement.studentmanager.dto.RoleToUserFormDto;
import sda.studentmanagement.studentmanager.projections.UserDataProjection;
import sda.studentmanagement.studentmanager.services.UserService;
import sda.studentmanagement.studentmanager.utils.*;
import sda.studentmanagement.studentmanager.utils.generationStrategy.UserRole;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    /**
     * @Route GET /api/users
     * @Desc Get all Users
     * @Access
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    /**
     * @Route GET /api/users/paginated
     * @Desc Get all Users, Paginated
     * @Access
     */
    @GetMapping("/users/paginated")
    public ResponseEntity<Page<User>> getPaginatedUsers(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
        return ResponseEntity.ok(userService.getPaginatedUsers(pageNumber, pageSize));
    }

    /**
     * @Route GET /api/users/data/charts
     * @Desc Get all Users count for charts
     * @Access
     */
    @GetMapping("/users/data/charts")
    public ResponseEntity<List<HashMap<Object, Object>>> getUsersCountCharts() {
        return ResponseEntity.ok(userService.getUsersCountCharts());
    }

    /**
     * @Route GET /api/users/data
     * @Desc Get all Users data
     * @Access
     */
    @GetMapping("/users/data")
    public ResponseEntity<List<UserDataProjection>> getUsersData() {
        // Todo

        return null;
    }

    /**
     * @Route GET /api/user/{userId}
     * @Desc Get User by ID
     * @Access
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") long userId) {
        return ResponseEntity.ok().body(userService.getUser(userId));
    }

    /**
     * @Route GET /api/user/data/{userId}
     * @Desc Get User data by ID
     * @Access
     */
    @GetMapping("/user/data/{userId}")
    public ResponseEntity<HashMap<Object, Object>> getUserData(@PathVariable("userId") long userId) {
        return ResponseEntity.ok().body(userService.getUserData(userId));
    }

    /**
     * @Route POST /api/user/role
     * @Desc Add a new Role to User
     * @Access
     */
    @PostMapping("/user/role")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserFormDto form) {
        userService.addRoleToUser(form.getUserEmail(), form.getRoleName());

        return ResponseEntity.ok().build();
    }

    /**
     * @Route PUT /api/user/{userId}/reset_password
     * @Desc Reset User Password
     * @Access
     */
    @PutMapping("/user/{userId}/reset_password")
    public ResponseEntity<String> resetUserPassword(
            @PathVariable("userId") long userId, @RequestBody ResetUserPasswordFormDto userForm
    ) {
        try {
            userService.resetUserPassword(userId, userForm);

            return new ResponseEntity<>(
                    "Password Changed", new HttpHeaders(), HttpStatus.OK
            );
        } catch (EmptyStackException e) {
            return new ResponseEntity<>(
              "Please fill in all fields", new HttpHeaders(), HttpStatus.BAD_REQUEST
            );
        } catch (InputMismatchException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * @Route POST /api/user
     * @Desc Save a new User
     * @Access
     */
    @PostMapping("/user")
    public ResponseEntity<?> saveUser(@RequestBody AddUserFormDto addUserForm) {
        try {
            userService.saveUser(addUserForm);

            return new ResponseEntity<>(
                    "User created Successfully.", new HttpHeaders(), HttpStatus.CREATED
            );
        } catch(EntityNotFoundException | IllegalArgumentException e) {
            return new ResponseEntity<>(
              e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST
            );
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append("- ").append(violation.getMessage()).append("</br>"));

            String errorMessage = builder.toString();

            return new ResponseEntity<>(
                    errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * @Route PATCH /api/user
     * @Desc Edit User
     * @Access Student, Professor, Admin
     */
    @PatchMapping("/user")
    public ResponseEntity<?> editUser(@RequestBody EditUserFormDto userForm) {
        try {
            UserDataProjection user = userService.editUser(userForm);

            return new ResponseEntity<>(
                    user, new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append("- ").append(violation.getMessage()).append("</br>"));

            String errorMessage = builder.toString();

            return new ResponseEntity<>(
                    errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST
            );
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * @Route POST /api/user/spawn
     * @Desc Save a new random User
     * @Access
     */
    @PostMapping("/user/spawn")
    public ResponseEntity<?> spawnUser() {
        boolean created = false;
        User user;

        // Works in loop in case if random generated User have same email as an existing User
        do {
            UserRole role;
            Random random = new Random();

            if (random.nextInt(100) > 90) {
                role = UserRole.PROFESSOR;
            } else {
                role = UserRole.STUDENT;
            }

            user = RandomThings.generateRandomUser(role);
            log.info("User {} generated", user.getEmail());

            if (userService.getUser(user.getEmail()) == null) {
                userService.savePopulateUser(user);
                userService.addRoleToUser(user.getEmail(), role.getRoleName());

                created = true;
            } else {
                log.info("User with the same email exists. Redo!");
            }
        }
        while(!created);

        return ResponseEntity.ok().body("User created Successfully");
    }

    /**
     * @Route POST /api/users/spawn/{amount}
     * @Desc Save X amount of random Users
     * @Access Admin
     */
    @PostMapping("/users/spawn/{amount}")
    public ResponseEntity<?> spawnManyUsers(@PathVariable("amount") int amount) {
        if (amount > 0) {
            for (int i = 0; i < amount; i++) {
                spawnUser();
            }
        }

        return ResponseEntity.ok().body(amount + " users generated.");
    }



    /**
     * @Route GET /api/token/refresh
     * @Desc Refresh and get new Access Token after it expires(15min)
     * @Access
     */
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Todo: Util class

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("Secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                String userEmail = decodedJWT.getSubject();
                User user = userService.getUser(userEmail);
                String role = user.getRoles().iterator().next().getName().toLowerCase(Locale.ROOT);
                Long id = user.getId();
                String redirectURI = "/" + role + "/" + id;

                String access_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .withClaim("redirectURI", redirectURI)
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                log.error("Error refreshing Token: {}", e.getMessage());
                response.setHeader("Error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
