package sda.studentmanagement.studentmanager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.domain.Attendance;
import sda.studentmanagement.studentmanager.services.AttendanceServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AttendanceController {
    private final AttendanceServiceImplementation attendanceService;

    //GET ALL ATTENDANCES - buggy?
    @GetMapping("/attendances")
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        return ResponseEntity.ok(attendanceService.getAttendanceList());
    }

    //SAVE NEW ATTENDANCE
    @PostMapping("/attendances/save")
    public ResponseEntity<Attendance> saveNewAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.saveAttendance(attendance));
    }

    @GetMapping("/attendances/{id}")
    public ResponseEntity<Attendance> getAttendance(@PathVariable("id") long id) {
        return ResponseEntity.ok(attendanceService.getAttendance(id));
    }

    @GetMapping("/attendances/{email}")
    public ResponseEntity<List<Attendance>> getAttendancesByUserEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(attendanceService.getAttendanceListByUser(email));
    }

    @GetMapping("/attendances/{sessionId}")
    public ResponseEntity<List<Attendance>> getAttendancesBySessionId(@PathVariable("sessionId") long id) {
        return ResponseEntity.ok(attendanceService.getAttendanceListBySession(id));
    }
}
