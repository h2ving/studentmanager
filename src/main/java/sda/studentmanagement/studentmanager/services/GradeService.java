package sda.studentmanagement.studentmanager.services;

import sda.studentmanagement.studentmanager.domain.Grade;

import java.util.List;

public interface GradeService {
    Grade saveGrade(Grade grade);
    void deleteGrade(Long id);
    Grade editGrade(Long id);
    Grade getGrade(Long id);
    List<Grade> getGrades();
    List<Grade> getGradesByStudent(String email);
    //List<Grade> getGradesBySession(Long sessionId);
}
