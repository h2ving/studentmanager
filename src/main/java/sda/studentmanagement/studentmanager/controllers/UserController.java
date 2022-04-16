package sda.studentmanagement.studentmanager.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sda.studentmanagement.studentmanager.domain.Role;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.domain.request.RoleToUserFormDto;
import sda.studentmanagement.studentmanager.services.UserService;
import sda.studentmanagement.studentmanager.utils.RandomThings;
import sda.studentmanagement.studentmanager.utils.UserUtils;

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

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
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
                log.error("Error logging in: {}", e.getMessage());
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


    // Legacy

    /*
    //!!!!! Whats this for?
    @GetMapping(value = "/", produces = "application/json")
    @ResponseBody
    public User showCurrentUser(Model model) throws Exception {

        User user = userRepo.getUserByEmail(UserUtils.getAuthenticatedUserName());
            return user;
    }

    //!!!!! Delete User still needs to be handled somehow, not sure how
    @DeleteMapping(value = "/admin/user/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable("id") int id) {
        if (userRepo.findById(id) != null) {
            userRepo.deleteById(id);
            return "Done";
        } else return "None";
    }

    //!!!!! Whats this use case?
    @RequestMapping(value = "/admin/drop")
    @ResponseBody
    public void dropUserRepo() {
        userRepo.deleteAll();
    }


    //!!!!! Not sure how adding random Users work atm, needs to take a look
    @RequestMapping(value = "/admin/addRandomUser")
    @ResponseBody
    public User addRandomUser() {
        User user = new User();
        user = RandomThings.generateRandomUser();
        userRepo.save(user);
        return user;
    }

    @RequestMapping(value = "/admin/addALotOfRandomUsers")
    @ResponseBody
    public void addALotOfRandomUsers() {
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user = RandomThings.generateRandomUser();
            userList.add(user);
        }
        userRepo.saveAll(userList);
    }
    */
}
