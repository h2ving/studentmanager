package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.studentmanagement.studentmanager.domain.Grade;
import sda.studentmanagement.studentmanager.domain.Session;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.repositories.GradeRepository;
import sda.studentmanagement.studentmanager.repositories.SessionRepository;
import sda.studentmanagement.studentmanager.repositories.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GradeServiceImplementation implements GradeService {
    private final GradeRepository gradeRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Override
    public List<Grade> getGrades() {
        return gradeRepository.findAll();
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
    public Grade editGrade(long gradeId) {
        // Todo

        return null;
    }

    @Override
    public void deleteGrade(long gradeId) {
        Grade grade = gradeRepository.findById(gradeId);

        gradeRepository.delete(grade);
    }
}
