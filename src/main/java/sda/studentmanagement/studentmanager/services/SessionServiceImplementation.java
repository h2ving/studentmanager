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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SessionServiceImplementation implements SessionService {

    private final SessionRepository sessionRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public Session saveSession(Session session) throws EntityExistsException {
        Session findSession = sessionRepository.findOneById(session.getId());

        if(findSession != null) {
            throw new EntityExistsException("Session with ID \"" + session.getId() + "\" already exists!");
        } else {
            return sessionRepository.save(session);
        }
    }

    @Override
    public void deleteSession(Long id) {
        Session session = sessionRepository.findOneById(id);

        sessionRepository.delete(session);
    }

    @Override
    public Session getSession(Long id) throws EntityExistsException {
        Session session = sessionRepository.findOneById(id);

        if(session != null) {
            return session;
        } else {
            throw new EntityExistsException("Session with id -\"" + id + "\" not found");
        }
    }

    @Override
    public List<Session> getSessionsByCourseName(String courseName) throws EntityNotFoundException {
        Course courseByName = courseRepository.findByName(courseName);
        List<Session> returnSessionsList = new ArrayList<>();
        if(courseByName != null) {
            for (Session session : getSessions()) {
                if(session.getCourse().equals(courseByName)) {
                    returnSessionsList.add(session);
                }
            }
            return returnSessionsList;
        } else {
            throw new EntityNotFoundException("Cannot find any courses named: \"" + courseName + "\" :(");
        }
    }

    @Override
    public List<Session> getSessionsByUser(String email) throws EntityNotFoundException {
        List<Session> sessionsByUser = new ArrayList<>();
        List<Session> allSessions = sessionRepository.findAll();
        User user = userRepository.findByEmail(email);
        if(user != null) {
            for(Session session : allSessions) {
                if(session.getUser().equals(user)) {
                    sessionsByUser.add(session);
                }
            }
        } else {
            throw new EntityNotFoundException("Can't find user with email \"" + email + "\"");
        }
        return sessionsByUser;
    }

    @Override
    public Session editSession(Session session) {
        return null;
    }

    @Override
    public List<Session> getSessions() {
        return sessionRepository.findAll();
    }
}
