package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.Grade;
import sda.studentmanagement.studentmanager.domain.Session;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.dto.AddGradesFormDto;
import sda.studentmanagement.studentmanager.dto.EditGradeFormDto;
import sda.studentmanagement.studentmanager.repositories.CourseRepository;
import sda.studentmanagement.studentmanager.repositories.GradeRepository;
import sda.studentmanagement.studentmanager.repositories.SessionRepository;
import sda.studentmanagement.studentmanager.repositories.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GradeServiceImplementation implements GradeService {
    private final GradeRepository gradeRepository;
    private final SessionRepository sessionRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public List<Grade> getGrades() {
        return gradeRepository.findAll();
    }

    @Override
    public List<HashMap<Object, Object>> getCourseAverageGradesChart() {
        List<Grade> grades = getGrades();
        List<String> courseNames = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");

        for (Grade grade : grades) {
            String courseName = grade.getSession().getCourse().getName();

            if (!courseNames.contains(courseName)) {
                courseNames.add(courseName);
            }
        }

        List<HashMap<Object, Object>> courseGrades = new ArrayList<>();

        for (String courseName : courseNames) {
            List<Grade> gradeList = grades.stream()
                    .filter(grade -> grade.getSession().getCourse().getName().equals(courseName))
                    .collect(Collectors.toList());

            Double average = gradeList.stream()
                    .mapToDouble(Grade::getGrade)
                    .average()
                    .orElse(0);

            courseGrades.add(new HashMap<>(){{
                put("name", courseName);
                put("value", df.format(average));
            }});
        }

        return courseGrades;
    }

    @Override
    public List<Grade> getUserGrades(long userId) {
        User user = userRepository.findById(userId);
        List<Grade> userGrades = new ArrayList<>();

        if(user != null) {
            for(Grade grade : getGrades()) {
                if(grade.getUser().equals(user)) {
                    userGrades.add(grade);
                }
            }

            return userGrades;
        } else {
            throw new EntityExistsException("Cannot find any grades");
        }
    }

    @Override
    public Page<Grade> getUserGrades(long userId, int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);

        return gradeRepository.getUserGradesByUserId(userId, paging);
    }

    @Override
    public List<Grade> getSessionGrades(long sessionId) {
        Session session = sessionRepository.findById(sessionId);
        List<Grade> sessionGrades = new ArrayList<>();

        if(session != null) {
            for(Grade grade : getGrades()) {
                if(grade.getSession().equals(session)) {
                    sessionGrades.add(grade);
                }
            }

            return sessionGrades;
        } else {
            throw new EntityNotFoundException("Invalid Session ID");
        }
    }

    @Override
    public Page<Grade> getCourseGrades(long courseId, int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);

        return gradeRepository.getCourseGradesByCourseId(courseId, paging);
    }

    @Override
    public List<Grade> getUserSessionGrades(long userId, long sessionId) {
        Session session = sessionRepository.findById(sessionId);
        User user = userRepository.findById(userId);
        List<Grade> userSessionGrades = new ArrayList<>();

        if (session != null && user != null) {
            for(Grade grade : getGrades()) {
                if(grade.getUser().equals(user) && grade.getSession().equals(session)) {
                    userSessionGrades.add(grade);
                }
            }

            return userSessionGrades;
        } else {
            throw new EntityNotFoundException("Invalid Session, User ID");
        }
    }

    @Override
    public List<Grade> getUserCourseGrades(long userId, long courseId) {
        Course course = courseRepository.findById(courseId);
        User user = userRepository.findById(userId);
        List<Grade> userCourseGrades = new ArrayList<>();

        if (course != null && user != null) {
            for (Grade grade : getGrades()) {
                if (grade.getUser().equals(user) && grade.getSession().getCourse().equals(course)) {
                    userCourseGrades.add(grade);
                }
            }

            return userCourseGrades;
        } else {
            throw new EntityNotFoundException("Invalid Session, User ID");
        }
    }

    @Override
    public Grade getGrade(long gradeId) {
        Grade grade = gradeRepository.findById(gradeId);

        if(grade == null) {
            throw new EntityExistsException("Grade not found");
        }

        return grade;
    }

    @Override
    public Grade saveGrade(Grade grade) {
        Grade findGrade = gradeRepository.findById(grade.getId());

        if(findGrade != null) {
            throw new EntityExistsException("Grade already exists");
        } else {
            return gradeRepository.save(grade);
        }
    }

    @Override
    public void saveGrades(AddGradesFormDto addGradesForm) {
        Session session = sessionRepository.findById(addGradesForm.getSessionId());
        List<Grade> gradeList = new ArrayList<>();

        if (session == null) {
            throw new EntityNotFoundException("Session not found");
        } else if (addGradesForm.getGrades().length == 0) {
            throw new IllegalArgumentException("No Grades marked");
        } else {
            for (int i = 0; i < addGradesForm.getGrades().length; i++) {
                Grade grade = new Grade();
                User user = userRepository.findById(addGradesForm.getGrades()[i].getUserId());

                if (user == null) {
                    throw new EntityNotFoundException("User with ID: " + addGradesForm.getGrades()[i].getUserId() + " not found");
                }

                if (addGradesForm.getGrades()[i].getGrade() <= 0 || addGradesForm.getGrades()[i].getGrade() > 5) {
                    throw new IllegalArgumentException("Marks are in 5 point grade scale");
                }

                grade.setSession(session);
                grade.setUser(user);
                grade.setGrade(addGradesForm.getGrades()[i].getGrade());

                gradeList.add(grade);
            }

            gradeRepository.saveAll(gradeList);
        }
    }

    @Override
    public Grade editGrade(long gradeId, EditGradeFormDto gradeForm) {
        Grade grade = gradeRepository.findById(gradeId);

        if (grade != null) {
            if (gradeForm.getGrade() <= 0 || gradeForm.getGrade() > 5) {
                throw new IllegalArgumentException("Grades are using 5 point scale");
            }

            grade.setGrade(gradeForm.getGrade());

            gradeRepository.save(grade);

            return grade;
        } else {
            throw new EntityNotFoundException("Session not found");
        }
    }

    @Override
    public void deleteGrade(long gradeId) {
        Grade grade = getGrade(gradeId);

        if (grade == null) {
            throw new EntityNotFoundException("Grade not found");
        } else {
            gradeRepository.delete(grade);
        }
    }
}
