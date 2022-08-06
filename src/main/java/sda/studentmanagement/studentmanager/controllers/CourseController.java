package sda.studentmanagement.studentmanager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.dto.AddCourseFormDto;
import sda.studentmanagement.studentmanager.dto.UserToCourseFormDto;
import sda.studentmanagement.studentmanager.projections.CourseDataChartsProjection;
import sda.studentmanagement.studentmanager.projections.CourseDataProjection;
import sda.studentmanagement.studentmanager.services.*;
import sda.studentmanagement.studentmanager.utils.RandomThings;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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
     * @Route GET /api/courses/data/charts
     * @Desc Get all Courses data using CourseDataChartsProjection
     * @Access
     */
    @GetMapping("/courses/data/charts")
    public ResponseEntity<List<CourseDataChartsProjection>> getCoursesCharts() {
        return ResponseEntity.ok(courseService.getCoursesCharts());
    }

    /**
     * @Route GET /api/courses/paginated
     * @Desc Get all Courses, paginated
     * @Access
     */
    @GetMapping("/courses/paginated")
    public ResponseEntity<Page<Course>> getPaginatedCourses(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(courseService.getPaginatedCourses(pageNumber, pageSize));
    }

    /**
     * @Route GET /api/courses/count/charts
     * @Desc Get all Courses count
     * @Access
     */
    @GetMapping("/courses/count/charts")
    public ResponseEntity<List<HashMap<Object, Object>>> getCourseCountCharts() {
        return ResponseEntity.ok(courseService.getCourseCountCharts());
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
    public ResponseEntity<?> saveCourse(@RequestBody AddCourseFormDto addCourseForm) {
        try {
            courseService.saveCourse(addCourseForm);

            return new ResponseEntity<>(
                    "Course created successfully", new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException | IllegalArgumentException e) {
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
        }
    }

    /**
     * @Route PUT /api/course/{courseId}
     * @Desc Edit Course
     * @Access
     */
    @PutMapping("/course/{courseId}")
    public ResponseEntity<?> editCourse(@PathVariable("courseId") long courseId, @RequestBody Course course) {
        try {
            courseService.editCourse(course);

            return new ResponseEntity<>(
                    "Course edited Successfully", new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * @Route DELETE /api/course/{courseId}
     * @Desc Delete Course
     * @Access
     */
    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable("courseId") long courseId) {
        try {
            courseService.deleteCourse(courseId);

            return new ResponseEntity<>(
                    "Course deleted successfully", new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * @Route POST /api/course/spawn
     * @Desc Create a new random Course
     * @Access
     */
    @PostMapping("/course/spawn")
    public ResponseEntity<Course> spawnCourse() {
        Course randomCourse = RandomThings.getRandomCourse();

        return ResponseEntity.ok(courseService.savePopulateCourse(randomCourse));
    }
}
