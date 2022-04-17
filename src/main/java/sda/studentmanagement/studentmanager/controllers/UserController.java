package sda.studentmanagement.studentmanager.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sda.studentmanagement.studentmanager.domain.Role;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.domain.request.RoleToUserFormDto;
import sda.studentmanagement.studentmanager.projections.UserView;
import sda.studentmanagement.studentmanager.services.UserService;
import sda.studentmanagement.studentmanager.utils.*;
import sda.studentmanagement.studentmanager.utils.generationStrategy.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
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

    // Get All Users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    // Get All Users
    @GetMapping("/usersview")
    public ResponseEntity<List<UserView>> getUsersView() {
        return ResponseEntity.ok().body(userService.getUsersView());
    }

    // Get User by Email
    @GetMapping("/user/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String userEmail) {
        log.info("User Email is: {}" + userEmail);
        return ResponseEntity.ok().body(userService.getUser(userEmail));
    }


    // Save a new User to database
    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());

        System.out.println("URI = " + uri);

        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    // Create a new random user
    @PostMapping("/spawn")
    public ResponseEntity<User> spawnUser() {
        boolean created = false;
        User user;

        // Works in loop in case if random generated user have same email as an existing user
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
                userService.saveUser(user);
                userService.addRoleToUser(user.getEmail(), role.getRoleName());
                created = true;
                log.info("User created successfully");
            }
            else
            {
                log.info("User with the same email exists. Redo!");
            }
        }
        while(!created);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/spawnmany/{amount}")
    public ResponseEntity<?> spawnManyUsers(@PathVariable("amount") int amount) {
        if (amount > 0) {
            for (int i = 0; i < amount; i++) {
                spawnUser();
            }
        }
        return ResponseEntity.ok().body(amount + " users generated.");
    }

    // Add a new role to user
    @PostMapping("/role/addroletouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserFormDto form) {
        userService.addRoleToUser(form.getUsername(), form.getRolename());

        return ResponseEntity.ok().build();
    }

    // Refresh and get new Token after it Expires
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("Secret".getBytes()); // Todo: Into Util class
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                String userEmail = decodedJWT.getSubject();
                User user = userService.getUser(userEmail);

                String access_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                log.error("Error logging in THERE: {}", e.getMessage());
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
