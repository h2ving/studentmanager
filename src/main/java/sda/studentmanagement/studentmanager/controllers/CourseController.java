package sda.studentmanagement.studentmanager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.services.CourseService;

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
    private final CourseService courseService;

    //!!!!! We dont need to use UserCache since the route is already authenticated with JWT by adding it into WebSecurityConfig
    //!!!!! -> http.authorizeRequests().antMatchers(POST, "/api/course/save/**").hasAuthority("Admin"); -> Something like this
    // so we just return ResponseEntity.ok().body(userService.getCourses()); something like this?
    //
    // */

    // Save new Course
    /*@PostMapping("/")
    public ResponseEntity<List<CourseResponse>> createCourse(@RequestBody CourseRequest request) {
        User user = userCache.getByUsername(UserUtils.getAuthenticatedUserName());

        return ResponseEntity.ok(courseService.createNewCourse(user.getId(), request));
    }*/


    // Get all Courses
/*    @GetMapping("/")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        User user = userCache.getByUsername(UserUtils.getAuthenticatedUserName());

        return ResponseEntity.ok(courseService.getAll(user.getId()));
    }*/


    // Get All courses by one User? using UserEmail
}
