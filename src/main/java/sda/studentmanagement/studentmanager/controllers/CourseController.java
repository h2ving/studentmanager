package sda.studentmanagement.studentmanager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.dto.UserToCourseFormDto;
import sda.studentmanagement.studentmanager.projections.CourseDataProjection;
import sda.studentmanagement.studentmanager.services.*;
import sda.studentmanagement.studentmanager.utils.RandomThings;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CourseController {
    private final CourseServiceImplementation courseService;
    private final SessionServiceImplementation sessionService;

    /**
     * @Route GET /api/courses
     * @Desc Get all Courses
     * @Access
     */
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getCourses() {
        return ResponseEntity.ok(courseService.getCourses());
    }

    /**
     * @Route GET /api/courses/user/{userId}
     * @Desc Get All courses where User is included or User is not included based on checkCourseContainsUser
     * @Access
     */
    @GetMapping("/courses/user/{userId}")
    public ResponseEntity<?> getUserCourses(
            @PathVariable("userId") long userId, @RequestParam("userCourses") boolean checkCourseContainsUser
    ) {
        try {
            List<Course> userCourses = courseService.getUserCourses(userId, checkCourseContainsUser);

            return new ResponseEntity<>(
                    userCourses, new HttpHeaders(), HttpStatus.OK
            );
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(
                    "User not Found", new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * @Route GET /api/courses/data
     * @Desc Get all Courses data using CourseDataProjection
     * @Access
     */
    @GetMapping("/courses/data")
    public ResponseEntity<List<CourseDataProjection>> getCoursesData() {
        return ResponseEntity.ok().body(courseService.getCoursesData());
    }

    /**
     * @Route GET /api/course/{courseId}
     * @Desc Get Course by ID
     * @Access
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getCourse(@PathVariable("courseId") long courseId) {
        try {
            Course course = courseService.getCourse(courseId);

            return new ResponseEntity<>(
                    course, new HttpHeaders(), HttpStatus.OK
            );
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(
                    "Course not found", new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * @Route POST /api/course/add/user
     * @Desc Add User to Course
     * @Access
     */
    @PostMapping("/course/add/user")
    public ResponseEntity<?> addUserToCourse(@RequestBody UserToCourseFormDto form) {
        try {
            courseService.addUserToCourse(form.getUserId(), form.getCourseId());
            sessionService.addUserToSession(form.getCourseId(), form.getUserId());

           return new ResponseEntity<>(
            "Course joined", new HttpHeaders(), HttpStatus.OK
           );
        } catch (EntityExistsException e) {
            System.out.println(e.getMessage());

            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * @Route POST /api/course/remove/user
     * @Desc Remove User from Course
     * @Access
     */
    @PostMapping("/course/remove/user")
    public ResponseEntity<?> removeUserFromCourse(@RequestBody UserToCourseFormDto form) {
        courseService.removeUserFromCourse(form.getUserId(), form.getCourseId());
        sessionService.removeUserFromSession(form.getCourseId(), form.getUserId());

        return new ResponseEntity<>(
                "Course left", new HttpHeaders(), HttpStatus.OK
        );
    }

    /**
     * @Route POST /api/course
     * @Desc Save a new Course
     * @Access
     */
    @PostMapping("/course")
    public ResponseEntity<Course> saveCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.saveCourse(course));
    }

    /**
     * @Route PUT /api/course/{courseId}
     * @Desc Edit Course
     * @Access
     */
    @PutMapping("/course/{courseId}")
    public ResponseEntity<?> editCourse(@PathVariable("courseId") long courseId, @RequestBody Course course) {
        // Todo

        return null;
    }

    /**
     * @Route DELETE /api/course/{courseId}
     * @Desc Delete Course
     * @Access
     */
    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable("courseId") long courseId) {
        // Todo

        return null;
    }

    /**
     * @Route POST /api/course/spawn
     * @Desc Create a new random Course
     * @Access
     */
    @PostMapping("/course/spawn")
    public ResponseEntity<Course> spawnCourse() {
        Course randomCourse = RandomThings.getRandomCourse();

        return ResponseEntity.ok(courseService.saveCourse(randomCourse));
    }
}
