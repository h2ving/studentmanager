package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.Session;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.repositories.CourseRepository;
import sda.studentmanagement.studentmanager.repositories.SessionRepository;
import sda.studentmanagement.studentmanager.repositories.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SessionServiceImplementation implements SessionService {
    private final SessionRepository sessionRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public List<Session> getSessions() {
        return sessionRepository.findAll();
    }

    @Override
    public List<Session> getUpComingSessions() {
        List<Session> upComingSessions = getSessions();
        upComingSessions = upComingSessions.stream()
                .filter(session -> session.getStartDateTime().isAfter(LocalDateTime.now())).toList();

        return upComingSessions;
    }

    @Override
    public List<Session> getUserSessions(long userId) {
        List<Session> userSessions = new ArrayList<>();
        User user = userRepository.findById(userId);

        if (user != null) {
            for(Session session : getUpComingSessions()) {
                if (session.getUser().contains(user)) {
                    userSessions.add(session);
                }
            }

            userSessions.sort(Comparator.comparing(Session::getStartDateTime));

            return userSessions;
        } else {
            throw new EntityNotFoundException("Invalid User ID");
        }
    }

    @Override
    public List<Session> getCourseSessions(long courseId) {
        Course course = courseRepository.findById(courseId);
        List<Session> courseSessions = new ArrayList<>();

        if(course != null) {
            for (Session session : getSessions()) {
                if(session.getCourse().equals(course)) {
                    courseSessions.add(session);
                }
            }
            return courseSessions;
        } else {
            throw new EntityNotFoundException("Invalid Course ID");
        }
    }

    @Override
    public void addUserToSession(long courseId, long userId) {
        List<Session> courseSessions = getCourseSessions(courseId);
        User user = userRepository.findById(userId);

        if (!courseSessions.isEmpty()) {
            for(Session session : courseSessions) {
                session.getUser().add(user);
            }
        }
    }

    @Override
    public void removeUserFromSession(long courseId, long userId) {
        List<Session> courseSessions = getCourseSessions(courseId);
        User user = userRepository.findById(userId);

        if (!courseSessions.isEmpty()) {
            for(Session session : courseSessions) {
                session.getUser().remove(user);
            }
        }
    }

    @Override
    public Session getSession(long sessionId) {
        Session session = sessionRepository.findById(sessionId);

        if (session == null) {
            throw new EntityExistsException("Session not found");
        }

        return session;
    }

    @Override
    public Session saveSession(Session session) {
        Session findSession = getSession(session.getId());

        if(findSession != null) {
            throw new EntityExistsException("Session already exists!");
        } else {
            return sessionRepository.save(session);
        }
    }

    @Override
    public Session editSession(Session session) {
        // Todo

        return null;
    }

    @Override
    public void deleteSession(long sessionId) {
        Session session = getSession(sessionId);

        sessionRepository.delete(session);
    }
}
