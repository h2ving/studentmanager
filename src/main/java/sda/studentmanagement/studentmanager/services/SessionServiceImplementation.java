package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.Session;
import sda.studentmanagement.studentmanager.repositories.CourseRepository;
import sda.studentmanagement.studentmanager.repositories.SessionRepository;

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

    @Override
    public Session saveSession(Session session) {
        Session findSession = sessionRepository.findOneById(session.getId());

        if(findSession != null) {
            throw new EntityExistsException("Session with ID \"" + session.getId() + "\" already exists");
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
    public Session getSession(Long id) {
        Session session = sessionRepository.findOneById(id);

        if(session == null) {
            throw new EntityExistsException("Session with id - \"" + id + "\" not found");
        }
        return session;
    }

    @Override
    public List<Session> getSessionsByCourseName(String courseName) {
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
            throw new EntityExistsException("Cannot find any courses named: \"" + courseName + "\" :(");
        }
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
