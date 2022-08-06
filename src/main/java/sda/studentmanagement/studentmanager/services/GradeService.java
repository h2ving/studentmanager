package sda.studentmanagement.studentmanager.services;

import org.springframework.data.domain.Page;
import sda.studentmanagement.studentmanager.domain.Grade;
import sda.studentmanagement.studentmanager.dto.AddGradesFormDto;
import sda.studentmanagement.studentmanager.dto.EditGradeFormDto;

import java.util.HashMap;
import java.util.List;

public interface GradeService {
    List<Grade> getGrades();

    List<HashMap<Object, Object>> getCourseAverageGradesChart();

    List<Grade> getUserGrades(long userId);

    Page<Grade> getUserGrades(long userId, int pageNumber, int pageSize);

    List<Grade> getSessionGrades(long sessionId);

    Page<Grade> getCourseGrades(long courseId, int pageNumber, int pageSize);

    List<Grade> getUserSessionGrades(long userId, long sessionId);

    List<Grade> getUserCourseGrades(long userId, long courseId);

    Grade getGrade(long gradeId);

    Grade saveGrade(Grade grade);

    void saveGrades(AddGradesFormDto addGradesForm);

    Grade editGrade(long gradeId, EditGradeFormDto gradeForm);

    void deleteGrade(long gradeId);
}
