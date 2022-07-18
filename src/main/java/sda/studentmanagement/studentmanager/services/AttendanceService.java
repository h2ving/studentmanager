package sda.studentmanagement.studentmanager.services;

import org.springframework.data.domain.Page;
import sda.studentmanagement.studentmanager.domain.Attendance;
import sda.studentmanagement.studentmanager.dto.AddAttendancesFormDto;
import sda.studentmanagement.studentmanager.dto.EditAttendanceFormDto;

import java.util.List;

public interface AttendanceService {
    List<Attendance> getAttendances();

    Page<Attendance> getPaginatedAttendances(int pageNumber, int pageSize);

    List<Attendance> getUserAttendances(long userId);

    List<Attendance> getSessionAttendances(long sessionId);

    List<Attendance> getUserCourseAttendances(long userId, long courseId);

    Attendance getAttendance(long attendanceId);

    Attendance saveAttendance(Attendance attendance);

    void saveAttendances(AddAttendancesFormDto addAttendancesForm);

    Attendance editAttendance(long attendanceId, EditAttendanceFormDto attendanceForm);

    void deleteAttendance(long attendanceId);
}
