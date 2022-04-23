package sda.studentmanagement.studentmanager.services;

import sda.studentmanagement.studentmanager.domain.Attendance;

import java.util.List;

public interface AttendanceService {
    Attendance saveAttendance(Attendance att);
    void deleteAttendance(long id);
    Attendance editAttendance(Attendance att);
    Attendance getAttendance(long id);
    List<Attendance> getAttendanceList();
    List<Attendance> getAttendanceListByUser(String email);
    List<Attendance> getAttendanceListBySession(long id);
}
