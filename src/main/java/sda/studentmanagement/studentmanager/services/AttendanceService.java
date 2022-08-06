package sda.studentmanagement.studentmanager.services;

import org.springframework.data.domain.Page;
import sda.studentmanagement.studentmanager.domain.Attendance;
import sda.studentmanagement.studentmanager.dto.AddAttendancesFormDto;
import sda.studentmanagement.studentmanager.dto.EditAttendanceFormDto;

import java.util.HashMap;
import java.util.List;

public interface AttendanceService {
    List<Attendance> getAttendances();

    List<HashMap<Object, Object>> getCourseAttendancesChart();

    Page<Attendance> getPaginatedAttendances(int pageNumber, int pageSize);

    List<Attendance> getUserAttendances(long userId);

    Page<Attendance> getUserAttendances(long userId, int pageNumber, int pageSize);

    List<Attendance> getSessionAttendances(long sessionId);

    Page<Attendance> getCourseAttendances(long courseId, int pageNumber, int pageSize);

    List<Attendance> getUserCourseAttendances(long userId, long courseId);

    Attendance getAttendance(long attendanceId);

    Attendance saveAttendance(Attendance attendance);

    void saveAttendances(AddAttendancesFormDto addAttendancesForm);

    Attendance editAttendance(long attendanceId, EditAttendanceFormDto attendanceForm);

    void deleteAttendance(long attendanceId);
}
