package sda.studentmanagement.studentmanager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.domain.Grade;
import sda.studentmanagement.studentmanager.services.CourseServiceImplementation;
import sda.studentmanagement.studentmanager.services.GradeServiceImplementation;

import javax.persistence.EntityExistsException;
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
     * @Route GET /api/grades/session/{sessionId}
     * @Desc Get All Grades from Session
     * @Access
     */
    @GetMapping("/grades/session/{sessionId}")
    public ResponseEntity<List<Grade>> getSessionGrades(@PathVariable("sessionId") long sessionId) {
        return ResponseEntity.ok(gradeService.getSessionGrades(sessionId));
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
     * @Route PUT /api/grade/{gradeId}
     * @Desc Save a new Grade
     * @Access
     */
    @PutMapping("/grade/{gradeId}")
    public ResponseEntity<?> editGrade(@PathVariable("gradeId") long gradeId) {
        // Todo

        return null;
    }

    /**
     * @Route DELETE /api/grade/{gradeId}
     * @Desc Delete Grade
     * @Access
     */
    @DeleteMapping("/grade/{gradeId}")
    public ResponseEntity<?> deleteGrade(@PathVariable("gradeId") long gradeId) {
        // Todo

        return null;
    }
}
