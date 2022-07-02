package sda.studentmanagement.studentmanager.services;

import sda.studentmanagement.studentmanager.domain.Session;

import java.util.List;

public interface SessionService {
    List<Session> getSessions();

    List<Session> getUpComingSessions();

    List<Session> getUserSessions(long userId);

    List<Session> getCourseSessions(long courseId);

    void addUserToSession(long courseId, long userId);

    void removeUserFromSession(long courseId, long userId);

    Session getSession(long sessionId);

    Session saveSession(Session session);

    Session editSession(Session session);

    void deleteSession(long sessionId);
}
