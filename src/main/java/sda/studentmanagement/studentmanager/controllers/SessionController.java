package sda.studentmanagement.studentmanager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.Session;
import sda.studentmanagement.studentmanager.dto.AddSessionFormDto;
import sda.studentmanagement.studentmanager.dto.EditSessionFormDto;
import sda.studentmanagement.studentmanager.projections.SessionDataProjection;
import sda.studentmanagement.studentmanager.services.CourseServiceImplementation;
import sda.studentmanagement.studentmanager.services.SessionServiceImplementation;
import sda.studentmanagement.studentmanager.utils.RandomThings;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class SessionController {
    private final SessionServiceImplementation sessionService;
    private final CourseServiceImplementation courseService;

    /**
     * @Route GET /api/sessions
     * @Desc Get all Sessions
     * @Access
     */
    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> getAllSessions() {
        return ResponseEntity.ok(sessionService.getSessions());
    }

    /**
     * @Route GET /api/sessions/user/{userId}
     * @Desc Get all User Sessions
     * @Access
     */
    @GetMapping("/sessions/user/{userId}")
    public ResponseEntity<Object> getUserSessions(@PathVariable("userId") long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(sessionService.getUserSessions(userId));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * @Route GET /api/sessions/course/{courseId}
     * @Desc Get all Course Sessions
     * @Access Student, Professor, Admin
     */
    @GetMapping("/sessions/course/{courseId}")
    public ResponseEntity<?> getCourseSessions(@PathVariable("courseId") long courseId) {
        try {
            List<Session> courseSessions = sessionService.getCourseSessions(courseId);

            return new ResponseEntity<>(
                    courseSessions, new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * @Route GET /api/sessions/data
     * @Desc Get All Sessions data using SessionDataProjection
     * @Access Professor, Admin
     */
    @GetMapping("/sessions/data")
    public ResponseEntity<List<SessionDataProjection>> getSessionsData() {
        return ResponseEntity.ok(sessionService.getSessionsData());
    }

    /**
     * @Route GET /api/session/{sessionId}
     * @Desc Get Session by ID
     * @Access
     */
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<Session> getSession(@PathVariable("sessionId") long sessionId) {
        return ResponseEntity.ok(sessionService.getSession(sessionId));
    }

    /**
     * @Route POST /api/session
     * @Desc Save a new Session
     * @Access Professor, Admin
     */
    @PostMapping("/session")
    public ResponseEntity<?> saveSession(@RequestBody AddSessionFormDto sessionForm) {
        try {
            Session session = sessionService.saveSession(sessionForm);

            return new ResponseEntity<>(
                    session, new HttpHeaders(), HttpStatus.OK
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
        }
    }

    /**
     * @Route PATCH /api/session/{sessionId}
     * @Desc Edit Session
     * @Access Professor, Admin
     */
    @PatchMapping("/session/{sessionId}")
    public ResponseEntity<?> editSession(@PathVariable long sessionId, @RequestBody EditSessionFormDto sessionForm) {
        try {
            Session session = sessionService.editSession(sessionId, sessionForm);

            return new ResponseEntity<>(
                    session, new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * @Route DELETE /api/session/{sessionId}
     * @Desc Delete Session
     * @Access Professor, Admin
     */
    @DeleteMapping("/session/{sessionId}")
    public ResponseEntity<?> deleteSession(@PathVariable("sessionId") long sessionId) {
        try {
            sessionService.deleteSession(sessionId);

            return new ResponseEntity<>(
                    "Session deleted successfully", new HttpHeaders(), HttpStatus.OK
            );
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * @Route /api/session/spawn/{courseId}
     * @Desc Create random Sessions to one Course by ID
     * @Access
     */
    @PostMapping("/session/spawn/{courseId}")
    public ResponseEntity<?> populatecourse(@PathVariable("courseId") long courseId) {
        List<Session> sessionList = RandomThings.generateRandomSessions(courseService.getCourse(courseId));

        for (Session session : sessionList) {
            sessionService.savePopulateSession(session);
        }

        return ResponseEntity.ok(sessionList);
    }

    /**
     * @Route /api/session/spawnforall
     * @Desc Create random Sessions to all Courses
     * @Access
     */
    @PostMapping("/session/spawnforall")
    public ResponseEntity<?> populatecourse() {
        List<Course> courses = courseService.getCourses();

        for (Course course : courses) {
            log.info("Adding sessions to " + course.getName());
            List<Session> sessionList = RandomThings.generateRandomSessions(courseService.getCourse(course.getId()));

            for (Session session : sessionList) {
                sessionService.savePopulateSession(session);
                log.info("Session " + session.getDescription() + " added");
            }
            log.info("Course " + course.getName() + " populated!");
        }

        return ResponseEntity.ok(sessionService.getSessions());
    }
}
