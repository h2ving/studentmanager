package sda.studentmanagement.studentmanager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.domain.Attendance;
import sda.studentmanagement.studentmanager.dto.AddAttendancesFormDto;
import sda.studentmanagement.studentmanager.dto.EditAttendanceFormDto;
import sda.studentmanagement.studentmanager.services.AttendanceServiceImplementation;
import sda.studentmanagement.studentmanager.services.CourseServiceImplementation;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AttendanceController {
    private final AttendanceServiceImplementation attendanceService;
    private final CourseServiceImplementation courseService;

    /**
     * @Route GET /api/attendances
     * @Desc Get all Attendances
     * @Access
     */
    @GetMapping("/attendances")
    public ResponseEntity<Page<Attendance>> getAttendances(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
        return ResponseEntity.ok(attendanceService.getPaginatedAttendances(pageNumber, pageSize));
    }

    /**
     * @Route GET /api/attendances/user/{userId}
     * @Desc Gets all User Attendances & list of Course names User is included
     * @Access
     */
    @GetMapping("/attendances/user/{userId}")
    public ResponseEntity<Object> getUserAttendances(@PathVariable("userId") long userId) {
        HashMap<Object,Object> response = new HashMap<>();
        response.put("userAttendance", attendanceService.getUserAttendances(userId));
        response.put("userCourses", courseService.getUserCourseNames(userId));

        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * @Route GET /api/attendances/session/{sessionId}
     * @Desc Get All Attendances of one Session
     * @Access
     */
    @GetMapping("/attendances/session/{sessionId}")
    public ResponseEntity<List<Attendance>> getSessionAttendances(@PathVariable("sessionId") long sessionId) {
        return ResponseEntity.ok(attendanceService.getSessionAttendances(sessionId));
    }

    /**
     * @Route GET /api/attendances/user/{userId}/course/{courseId}
     * @Desc Get All User Attendances in a Course
     * @Access Professor, Admin
     */
    @GetMapping("/attendances/user/{userId}/course/{courseId}")
    public ResponseEntity<List<Attendance>> getUserCourseAttendance(@PathVariable("userId") long userId, @PathVariable("courseId") long courseId) {
        return ResponseEntity.ok(attendanceService.getUserCourseAttendances(userId, courseId));
    }

    /**
     * @Route GET /api/attendance/{attendanceId}
     * @Desc Get Attendance
     * @Access
     */
    @GetMapping("/attendance/{attendanceId}")
    public ResponseEntity<Attendance> getAttendance(@PathVariable("attendanceId") long attendanceId) {
        return ResponseEntity.ok(attendanceService.getAttendance(attendanceId));
    }

    /**
     * @Route POST /api/attendance
     * @Desc Save a new Attendance
     * @Access
     */
    @PostMapping("/attendance")
    public ResponseEntity<Attendance> saveAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.saveAttendance(attendance));
    }

    /**
     * @Route POST /api/attendances
     * @Desc Save multiple Attendances
     * @Access Professor, Admin
     */
    @PostMapping("/attendances")
    public ResponseEntity<?> saveAttendances(@RequestBody AddAttendancesFormDto addAttendancesForm) {
        try {
            attendanceService.saveAttendances(addAttendancesForm);

            return new ResponseEntity<>(
                    "Attendances Added Successfully", new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * @Route PATCH /api/attendance/{attendanceId}
     * @Desc Edit Attendance
     * @Access
     */
    @PatchMapping("/attendance/{attendanceId}")
    public ResponseEntity<?> editAttendance(@PathVariable("attendanceId") long attendanceId, @RequestBody EditAttendanceFormDto attendanceForm) {
        try {
            Attendance attendance = attendanceService.editAttendance(attendanceId, attendanceForm);

            return new ResponseEntity<>(
                    attendance, new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * @Route DELETE /api/attendance/{attendanceId}
     * @Desc Delete Attendance
     * @Access
     */
    @DeleteMapping("/attendance/{attendanceId}")
    public ResponseEntity<?> deleteAttendance(@PathVariable long attendanceId) {
        try {
            attendanceService.deleteAttendance(attendanceId);

            return new ResponseEntity<>(
                    "Attendance deleted successfully", new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        }
    }
}
