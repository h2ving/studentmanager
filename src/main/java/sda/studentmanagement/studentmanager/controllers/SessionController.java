package sda.studentmanagement.studentmanager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.Session;
import sda.studentmanagement.studentmanager.services.CourseServiceImplementation;
import sda.studentmanagement.studentmanager.services.SessionService;
import sda.studentmanagement.studentmanager.services.SessionServiceImplementation;
import sda.studentmanagement.studentmanager.utils.RandomThings;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class SessionController {
    private final SessionServiceImplementation sessionService;
    private final CourseServiceImplementation courseService;

    //Get all Sessions
    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> getAllSessions() {
        return ResponseEntity.ok(sessionService.getSessions());
    }

    //Save new Session
    @PostMapping("/session/save")
    public ResponseEntity<Session> saveNewSession(@RequestBody Session session) {
        return ResponseEntity.ok(sessionService.saveSession(session));
    }

    @PostMapping("/session/spawn/{courseId}")
    public ResponseEntity<?> populatecourse(@PathVariable("courseId") long courseId) {
        List<Session> sessionList = RandomThings.generateRandomSessions(courseService.getCourseById(courseId));
        for (Session session : sessionList
        ) {
            sessionService.saveSession(session);
        }
        return ResponseEntity.ok(sessionList);
    }

    @PostMapping("/session/spawnforall/")
    public ResponseEntity<?> populatecourse() {
        List<Course> courses = courseService.getCourses();

        for (Course course : courses
        ) {
            log.info("Adding sessions to " + course.getName());
            List<Session> sessionList = RandomThings.generateRandomSessions(courseService.getCourseById(course.getId()));
            for (Session session : sessionList
            ) {
                sessionService.saveSession(session);
                log.info("Session " + session.getDescription() + " added");
            }
            log.info("Course " + course.getName() + " populated!");
        }
        return ResponseEntity.ok(sessionService.getSessions());
    }

    @GetMapping("/sessions/sessionsByUser/{id}")
    public ResponseEntity<List<Session>> getSessionsByUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(sessionService.getSessionsByUser(id));
    }

    @GetMapping("/sessions/{courseName}")
    public ResponseEntity<List<Session>> getCourseSessions(@PathVariable("courseName") String courseName) {
        return ResponseEntity.ok(sessionService.getSessionsByCourseName(courseName));
    }

    @DeleteMapping("/session/delete/{id}")
    public void hardDeleteSession(@PathVariable("id") Long id) {
        sessionService.deleteSession(id);
    }

}
