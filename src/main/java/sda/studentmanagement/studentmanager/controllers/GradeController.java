package sda.studentmanagement.studentmanager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.domain.Grade;
import sda.studentmanagement.studentmanager.dto.AddGradesFormDto;
import sda.studentmanagement.studentmanager.dto.EditGradeFormDto;
import sda.studentmanagement.studentmanager.services.CourseServiceImplementation;
import sda.studentmanagement.studentmanager.services.GradeServiceImplementation;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class GradeController {
    private final GradeServiceImplementation gradeService;
    private final CourseServiceImplementation courseService;

    /**
     * @Route GET /api/grades
     * @Desc Get All Grades
     * @Access
     */
    @GetMapping("/grades")
    public ResponseEntity<List<Grade>> getGrades() {
        return ResponseEntity.ok(gradeService.getGrades());
    }

    /**
     * @Route GET /api/grades/averages/chart
     * @Desc Get Course average grades for chart
     * @Access
     */
    @GetMapping("/grades/averages/chart")
    public ResponseEntity<List<HashMap<Object, Object>>> getCourseAverageGradesChart() {
        return ResponseEntity.ok(gradeService.getCourseAverageGradesChart());
    }

    /**
     * @Route GET /api/grades/user/{userId}
     * @Desc Gets all User Grades and list of Course names User is included
     * @Access
     */
    @GetMapping("/grades/user/{userId}")
    public ResponseEntity<Object> getUserGrades(@PathVariable("userId") long userId) {
        HashMap<Object, Object> response = new HashMap<>();
        response.put("userGrades", gradeService.getUserGrades(userId));
        response.put("userCourses", courseService.getUserCourseNames(userId));

        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * @Route GET /api/grades/user/{userId}/paginated
     * @Desc Get All User Grades, Paginated
     * @Access
     */
    @GetMapping("/grades/user/{userId}/paginated")
    public ResponseEntity<Page<Grade>> getUserGradesPaginated(@PathVariable("userId") long userId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(gradeService.getUserGrades(userId, pageNumber, pageSize));
    }

    /**
     * @Route GET /api/grades/session/{sessionId}
     * @Desc Get All Grades from Session
     * @Access
     */
    @GetMapping("/grades/session/{sessionId}")
    public ResponseEntity<List<Grade>> getSessionGrades(@PathVariable("sessionId") long sessionId) {
        return ResponseEntity.ok(gradeService.getSessionGrades(sessionId));
    }

    /**
     * @Route GET /api/course/{courseId}/grades
     * @Desc Get All Course Grades, Paginated
     * @Access Professor, Admin
     */
    @GetMapping("/course/{courseId}/grades")
    public ResponseEntity<Page<Grade>> getCourseGrades(@PathVariable("courseId") long courseId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(gradeService.getCourseGrades(courseId, pageNumber, pageSize));
    }

    /**
     * @Route GET /api/grades/user/{userId}/session/{sessionId}
     * @Desc Get All User Grades from Session
     * @Access
     */
    @GetMapping("/grades/user/{userId}/session/{sessionId}")
    public ResponseEntity<List<Grade>> getUserSessionGrades(
            @PathVariable("userId") long userId, @PathVariable("sessionId") long sessionId
    ) {
        return ResponseEntity.ok(gradeService.getUserSessionGrades(userId, sessionId));
    }

    /**
     * @Route GET /api/grades/user/{userId}/course/{courseId}
     * @Desc Get All User Grades from Course
     * @Access Professor, Admin
     */
    @GetMapping("/grades/user/{userId}/course/{courseId}")
    public ResponseEntity<List<Grade>> getUserCourseGrades(
            @PathVariable("userId") long userId, @PathVariable("courseId") long courseId
    ) {
        return ResponseEntity.ok(gradeService.getUserCourseGrades(userId, courseId));
    }

    /**
     * @Route GET /api/grade/{gradeId}
     * @Desc Get Grade
     * @Access
     */
    @GetMapping("/grade/{gradeId}")
    public ResponseEntity<Grade> getGrade(@PathVariable("gradeId") long gradeId) {
        return ResponseEntity.ok(gradeService.getGrade(gradeId));
    }

    /**
     * @Route POST /api/grade
     * @Desc Save a new Grade
     * @Access
     */
    @PostMapping("/grade")
    public ResponseEntity<Grade> saveGrade(@RequestBody Grade grade) {
        return ResponseEntity.ok(gradeService.saveGrade(grade));
    }

    /**
     * @Route POST /api/grades
     * @Desc Save multiple Grades
     * @Access Professor, Admin
     */
    @PostMapping("/grades")
    public ResponseEntity<?> saveGrades(@RequestBody AddGradesFormDto addGradesForm) {
        try {
            gradeService.saveGrades(addGradesForm);

            return new ResponseEntity<>(
                    "Grades Added Successfully", new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * @Route PATCH /api/grade/{gradeId}
     * @Desc Edit Grade
     * @Access Professor, Admin
     */
    @PatchMapping("/grade/{gradeId}")
    public ResponseEntity<?> editGrade(@PathVariable("gradeId") long gradeId, @RequestBody EditGradeFormDto gradeForm) {
        try {
            Grade grade = gradeService.editGrade(gradeId, gradeForm);

            return new ResponseEntity<>(
                    grade, new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * @Route DELETE /api/grade/{gradeId}
     * @Desc Delete Grade
     * @Access
     */
    @DeleteMapping("/grade/{gradeId}")
    public ResponseEntity<?> deleteAttendance(@PathVariable long gradeId) {
        try {
            gradeService.deleteGrade(gradeId);

            return new ResponseEntity<>(
                    "Grade deleted successfully", new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        }
    }
}
