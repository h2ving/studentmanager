package sda.studentmanagement.studentmanager.services;

import sda.studentmanagement.studentmanager.domain.Grade;
import sda.studentmanagement.studentmanager.dto.AddGradesFormDto;
import sda.studentmanagement.studentmanager.dto.EditGradeFormDto;

import java.util.List;

public interface GradeService {
    List<Grade> getGrades();

    List<Grade> getUserGrades(long userId);

    List<Grade> getSessionGrades(long sessionId);

    List<Grade> getUserSessionGrades(long userId, long sessionId);

    List<Grade> getUserCourseGrades(long userId, long courseId);

    Grade getGrade(long gradeId);

    Grade saveGrade(Grade grade);

    void saveGrades(AddGradesFormDto addGradesForm); //

    Grade editGrade(long gradeId, EditGradeFormDto gradeForm);

    void deleteGrade(long gradeId);
}
