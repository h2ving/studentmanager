package sda.studentmanagement.studentmanager.services;

import sda.studentmanagement.studentmanager.domain.Session;

import java.util.List;

public interface SessionService {
    Session saveSession(Session session);
    void deleteSession(Long id);
    Session editSession(Session session);
    Session getSession(Long id);
    List<Session> getSessions();
    List<Session> getSessionsByCourseName(String courseName);
}
