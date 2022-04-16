package sda.studentmanagement.studentmanager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.domain.Session;
import sda.studentmanagement.studentmanager.services.SessionService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class SessionController {
    private final SessionService sessionService;

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

    @GetMapping("/sessions/{courseName}")
    public ResponseEntity<List<Session>> getCourseSessions(@PathVariable("courseName") String courseName) {
        return ResponseEntity.ok(sessionService.getSessionsByCourseName(courseName));
    }

    @DeleteMapping("/session/delete/{id}")
    public void hardDeleteSession(@PathVariable("id") Long id) {
        sessionService.deleteSession(id);
    }

}
