package sda.studentmanagement.studentmanager.services;

import sda.studentmanagement.studentmanager.domain.Session;
import sda.studentmanagement.studentmanager.dto.AddSessionFormDto;
import sda.studentmanagement.studentmanager.dto.EditSessionFormDto;
import sda.studentmanagement.studentmanager.projections.SessionDataProjection;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface SessionService {
    List<Session> getSessions();

    List<Session> getUpComingSessions();

    List<Session> getUserSessions(long userId);

    List<Session> getCourseSessions(long courseId);

    List<SessionDataProjection> getSessionsData();

    void addUserToSession(long courseId, long userId);

    void removeUserFromSession(long courseId, long userId);

    Session getSession(long sessionId);

    Session saveSession(AddSessionFormDto addSessionForm) throws ConstraintViolationException;

    Session savePopulateSession(Session session);

    Session editSession(long sessionId, EditSessionFormDto editSessionForm);

    void deleteSession(long sessionId);
}
