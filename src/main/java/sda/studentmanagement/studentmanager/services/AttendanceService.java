package sda.studentmanagement.studentmanager.services;

import org.springframework.data.domain.Page;
import sda.studentmanagement.studentmanager.domain.Attendance;

import java.util.List;

public interface AttendanceService {
    List<Attendance> getAttendances();

    Page<Attendance> getPaginatedAttendances(int pageNumber, int pageSize);

    List<Attendance> getUserAttendances(long userId);

    List<Attendance> getSessionAttendances(long sessionId);

    Attendance getAttendance(long attendanceId);

    Attendance saveAttendance(Attendance attendance);

    Attendance editAttendance(Attendance attendance);

    void deleteAttendance(long attendanceId);
}
