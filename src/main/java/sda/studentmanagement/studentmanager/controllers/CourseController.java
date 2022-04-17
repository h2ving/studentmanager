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
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.Role;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.services.CourseService;
import sda.studentmanagement.studentmanager.services.CourseServiceImplementation;
import sda.studentmanagement.studentmanager.services.UserService;
import sda.studentmanagement.studentmanager.services.UserServiceImplementation;
import sda.studentmanagement.studentmanager.utils.RandomThings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//!!!!! Use this instead -> /api. @RequiredArgsConstruction so we don't have to @Autowire, just private final CourseService courseService.
// @Slf4j for logging?. Cors is already disabled in WebSecurityyConfig -> http.csrf().disable();
/*@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j*/

/*@RestController
@RequestMapping("/courses/")
@CrossOrigin("http://localhost:4200/")*/

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CourseController {
    private final CourseServiceImplementation courseService;
    private final UserService userService;

    //!!!!! We dont need to use UserCache since the route is already authenticated with JWT by adding it into WebSecurityConfig
    //!!!!! -> http.authorizeRequests().antMatchers(POST, "/api/course/save/**").hasAuthority("Admin"); -> Something like this
    // so we just return ResponseEntity.ok().body(userService.getCourses()); something like this?
    //
    // */

    // Save new Course
    @PostMapping("/course/spawn")
    public ResponseEntity<Course> spawnCourse() {
        Course randomCourse = RandomThings.getRandomCourse();
        return ResponseEntity.ok(courseService.saveCourse(randomCourse));
        //return ResponseEntity.ok(course);
    }


    // Save new Course
    @PostMapping("/course/save")
    public ResponseEntity<Course> saveCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.saveCourse(course));
    }


    // Get all Courses
    @GetMapping("/courses/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getCourses());
    }

    //GET SPECIFIC COURSE BY COURSE NAME
    @GetMapping("/course/{courseName}")
    public ResponseEntity<Course> getCourseByCourseName(@PathVariable("courseName") String courseName) {
        return ResponseEntity.ok(courseService.getCourse(courseName));
    }

    // Get All courses by one User? using UserEmail
    @GetMapping("/courses/{userEmail}")
    public ResponseEntity<List<Course>> getAllCoursesByUserEmail(@PathVariable("userEmail") String email) {
        return ResponseEntity.ok(courseService.getCoursesByUser(email));
    }

}
