package sda.studentmanagement.studentmanager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.services.CourseService;
import sda.studentmanagement.studentmanager.services.CourseServiceImplementation;

import java.util.List;

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

    //!!!!! We dont need to use UserCache since the route is already authenticated with JWT by adding it into WebSecurityConfig
    //!!!!! -> http.authorizeRequests().antMatchers(POST, "/api/course/save/**").hasAuthority("Admin"); -> Something like this
    // so we just return ResponseEntity.ok().body(userService.getCourses()); something like this?
    //
    // */

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
    @GetMapping("/course/courseName/{courseName}")
    public ResponseEntity<Course> getCourseByCourseName(@PathVariable("courseName") String courseName) {
        return ResponseEntity.ok(courseService.getCourse(courseName));
    }

    // Get All courses by one User? using UserEmail
    @GetMapping("/courses/email/{userEmail}")
    public ResponseEntity<List<Course>> getAllCoursesByUserEmail(@PathVariable("userEmail") String email) {
        return ResponseEntity.ok(courseService.getCoursesByUser(email));
    }
}
