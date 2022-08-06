package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.studentmanagement.studentmanager.domain.Attendance;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.Session;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.dto.AddAttendancesFormDto;
import sda.studentmanagement.studentmanager.dto.EditAttendanceFormDto;
import sda.studentmanagement.studentmanager.repositories.AttendanceRepository;
import sda.studentmanagement.studentmanager.repositories.CourseRepository;
import sda.studentmanagement.studentmanager.repositories.SessionRepository;
import sda.studentmanagement.studentmanager.repositories.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AttendanceServiceImplementation implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<Attendance> getAttendances() {
        return attendanceRepository.findAll();
    }

    @Override
    public List<HashMap<Object, Object>> getCourseAttendancesChart() {
        List<Attendance> attendances = getAttendances();
        List<String> courseNames = new ArrayList<>();

        for (Attendance attendance : attendances) {
            String courseName = attendance.getSession().getCourse().getName();

            if (!courseNames.contains(courseName)) {
                courseNames.add(courseName);
            }
        }

        List<HashMap<Object, Object>> courseAttendances = new ArrayList<>();

        for (String courseName : courseNames) {
            List<Attendance> attendanceList = attendances.stream()
                    .filter(attendance -> attendance.getSession().getCourse().getName().equals(courseName))
                    .collect(Collectors.toList());

            final float totalAttendances = attendanceList.size();
            float attendedSessions = 0;

            for (Attendance attendance : attendanceList) {
                if (attendance.isDidAttend()) {
                    attendedSessions++;
                }
            }

            int attendancePercentage = Math.round((attendedSessions / totalAttendances) * 100);

            courseAttendances.add(new HashMap<>(){{
                put("name", courseName);
                put("value", attendancePercentage);
            }});
        }

        return courseAttendances;
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
    public Page<Attendance> getUserAttendances(long userId, int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);

        return attendanceRepository.getUserAttendancesPaginatedByUserId(userId, paging);
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
    public Page<Attendance> getCourseAttendances(long courseId, int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);

        return attendanceRepository.getCourseAttendancesByCourseId(courseId, paging);
    }

    @Override
    public List<Attendance> getUserCourseAttendances(long userId, long courseId) {
        Course course = courseRepository.findById(courseId);
        User user = userRepository.findById(userId);
        List<Attendance> userCourseAttendances = new ArrayList<>();

        if (course != null && user != null) {
            for(Attendance attendance : getAttendances()) {
                if (attendance.getUser().equals(user) && attendance.getSession().getCourse().equals(course)) {
                    userCourseAttendances.add(attendance);
                }
            }

            return userCourseAttendances;
        } else {
            throw new EntityNotFoundException("Invalid Session, User ID");
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
    public void saveAttendances(AddAttendancesFormDto addAttendancesForm) {
        Session session = sessionRepository.findById(addAttendancesForm.getSessionId());
        List<Attendance> attendanceList = new ArrayList<>();

        if (session == null) {
            throw new EntityNotFoundException("Session not found");
        } else if (addAttendancesForm.getAttendances().length == 0) {
            throw new IllegalArgumentException("No Attendances marked");
        } else {
            for (int i = 0; i < addAttendancesForm.getAttendances().length; i++) {
                Attendance attendance = new Attendance();
                User user = userRepository.findById(addAttendancesForm.getAttendances()[i].getUserId());

                if (user == null) {
                    throw new EntityNotFoundException("User with ID: " + addAttendancesForm.getAttendances()[i].getUserId() + " not found");
                }

                Attendance existingAttendance = attendanceRepository.getExistingAttendanceByUserSessionId(user.getId(), session.getId());

                if (existingAttendance != null) {
                    existingAttendance.setDidAttend(addAttendancesForm.getAttendances()[i].isAttendance());

                    attendanceRepository.save(existingAttendance);
                } else {
                    attendance.setSession(session);
                    attendance.setUser(user);
                    attendance.setDidAttend(addAttendancesForm.getAttendances()[i].isAttendance());

                    attendanceList.add(attendance);
                }
            }

            attendanceRepository.saveAll(attendanceList);
        }
    }

    @Override
    public Attendance editAttendance(long attendanceId, EditAttendanceFormDto attendanceForm) {
        Attendance attendance = attendanceRepository.findById(attendanceId);

        if (attendance != null) {
            if (attendanceForm.getAttendance().isBlank()) {
                throw new IllegalArgumentException("Please select Attendance");
            }

            attendance.setDidAttend(Objects.equals(attendanceForm.getAttendance(), "present"));

            attendanceRepository.save(attendance);

            return attendance;
        } else {
            throw new EntityNotFoundException("Session not found");
        }
    }

    @Override
    public void deleteAttendance(long attendanceId) {
        Attendance attendance = getAttendance(attendanceId);

        if (attendance == null) {
            throw new EntityNotFoundException("Attendance not found");
        } else {
            attendanceRepository.delete(attendance);
        }
    }
}
