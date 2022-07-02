package sda.studentmanagement.studentmanager.services;

import sda.studentmanagement.studentmanager.domain.Grade;

import java.util.List;

public interface GradeService {
    List<Grade> getGrades();

    List<Grade> getUserGrades(long userId);

    List<Grade> getSessionGrades(long sessionId);

    List<Grade> getUserSessionGrades(long userId, long sessionId);

    Grade getGrade(long gradeId);

    Grade saveGrade(Grade grade);

    Grade editGrade(long gradeId);

    void deleteGrade(long gradeId);
}
