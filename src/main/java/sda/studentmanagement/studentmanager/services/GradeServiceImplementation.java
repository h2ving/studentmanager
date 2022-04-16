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
    public Grade saveGrade(Grade grade) {
        Grade findGrade = gradeRepository.findOneById(grade.getId());
        if(findGrade != null) {
            throw new EntityExistsException("Grade with ID \"" + grade.getId() + "\" already exists");
        } else {
            return gradeRepository.save(grade);
        }
    }

    //HARDDELETE
    @Override
    public void deleteGrade(Long id) {
        Grade grade = gradeRepository.findOneById(id);

        gradeRepository.delete(grade);

    }

    @Override
    public Grade editGrade(Long id) {
        return null;
    }

    @Override
    public Grade getGrade(Long id) {
        Grade grade = gradeRepository.findOneById(id);

        if(grade == null) {
            throw new EntityExistsException("Grade with id - " + id + "not found");
        }
        return grade;
    }

    @Override
    public List<Grade> getGradesByStudent(String email) {
        User user = userRepository.findByEmail(email);
        List<Grade> returnGradeList = new ArrayList<>();
        if(user != null) {
            for(Grade grade : getGrades()) {
                if(grade.getUser().equals(user)) {
                    returnGradeList.add(grade);
                }
            }
            return returnGradeList;
        } else {
            throw new EntityExistsException("Cannot find any grades associated with: \"" + email + "\" :(");
        }
    }
/*
    @Override
    public List<Grade> getGradesBySession(Long sessionId) {
        Session session = sessionRepository.findOneById(sessionId);
        List<Grade> listOfGradesBySession = new ArrayList<>();

    if(session != null) {
        for(Grade grade : session.get)
    } else {
        throw new EntityNotFoundException("Cannot find any grades associated with user: " + studentName + " in ");
    }

    }

 */

    @Override
    public List<Grade> getGrades() {
        return gradeRepository.findAll();
    }
}
