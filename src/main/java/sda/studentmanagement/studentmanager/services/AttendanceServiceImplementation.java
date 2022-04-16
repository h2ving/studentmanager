package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Attendance saveAttendance(Attendance att) {
        Attendance findAttendance = attendanceRepository.findOneById(att.getId());

        if(findAttendance != null) {
            throw new EntityExistsException("Attendance with ID \"" + att.getId() + "\" already exists!");
        } else {
            return attendanceRepository.save(att);
        }
    }

    //HARD DELETE
    @Override
    public void deleteAttendance(long id) {
        Attendance att = attendanceRepository.findOneById(id);

        attendanceRepository.delete(att);
    }

    @Override
    public Attendance editAttendance(Attendance att) {
        return null;
    }

    @Override
    public Attendance getAttendance(long id) {
        Attendance att = attendanceRepository.findOneById(id);

        if(att == null) {
            throw new EntityExistsException("Attendance with ID: \"" + id + "\" not found!");
        }
        return att;
    }

    @Override
    public List<Attendance> getAttendanceList() {
        return attendanceRepository.findAll();
    }

    @Override
    public List<Attendance> getAttendanceListByUser(String email) {
        User user = userRepository.findByEmail(email);
        List<Attendance> attendanceList = new ArrayList<>();
        if(user != null) {
            for(Attendance att : getAttendanceList()) {
                if(att.getUser().equals(user)) {
                    attendanceList.add(att);
                }
            }
            return attendanceList;
        } else {
            throw new EntityNotFoundException("Cannot find attendances by this email: " + email);
        }
    }

    @Override
    public List<Attendance> getAttendanceListBySession(long id) {
        Session session = sessionRepository.findOneById(id);
        List<Attendance> attendanceList = new ArrayList<>();
        if(session != null) {
            for(Attendance att : getAttendanceList()) {
                if(att.getSession().equals(session)) {
                    attendanceList.add(att);
                }
            }
            return attendanceList;
        } else {
            throw new EntityNotFoundException("Cannot find Session with ID: \"" + id + "\"");
        }
    }
}
