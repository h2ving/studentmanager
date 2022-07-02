package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.studentmanagement.studentmanager.domain.Attendance;
import sda.studentmanagement.studentmanager.domain.Session;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.repositories.AttendanceRepository;
import sda.studentmanagement.studentmanager.repositories.SessionRepository;
import sda.studentmanagement.studentmanager.repositories.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AttendanceServiceImplementation implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Override
    public List<Attendance> getAttendances() {
        return attendanceRepository.findAll();
    }

    @Override
    public Page<Attendance> getPaginatedAttendances(int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);

        return attendanceRepository.findAll(paging);
    }

    @Override
    public Attendance getAttendance(long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId);

        if(attendance == null) {
            throw new EntityNotFoundException("Attendance doesn't exists");
        }

        return attendance;
    }

    @Override
    public List<Attendance> getUserAttendances(long userId) {
        User user = userRepository.findById(userId);
        List<Attendance> userAttendances = new ArrayList<>();

        if(user != null) {
            for(Attendance attendance : getAttendances()) {
                if(attendance.getUser().equals(user)) {
                    userAttendances.add(attendance);
                }
            }

            return userAttendances;
        } else {
            throw new EntityNotFoundException("Invalid User ID");
        }
    }

    @Override
    public List<Attendance> getSessionAttendances(long sessionId) {
        Session session = sessionRepository.findById(sessionId);
        List<Attendance> sessionAttendances = new ArrayList<>();

        if(session != null) {
            for(Attendance attendance : getAttendances()) {
                if(attendance.getSession().equals(session)) {
                    sessionAttendances.add(attendance);
                }
            }

            return sessionAttendances;
        } else {
            throw new EntityNotFoundException("Cannot find Session's Attendances");
        }
    }

    @Override
    public Attendance saveAttendance(Attendance attendance) {
        Attendance findAttendance = attendanceRepository.findById(attendance.getId());

        if(findAttendance != null) {
            throw new EntityExistsException("Attendance already exists!");
        }

        return attendanceRepository.save(attendance);

    }

    @Override
    public Attendance editAttendance(Attendance attendance) {
        // Todo

        return null;
    }

    @Override
    public void deleteAttendance(long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId);

        attendanceRepository.delete(attendance);
    }
}
