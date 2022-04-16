package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.repositories.CourseRepository;
import sda.studentmanagement.studentmanager.repositories.UserRepository;

import javax.persistence.EntityExistsException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourseServiceImplementation implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public Course saveCourse(Course course) {
        Course findCourse = courseRepository.findByName(course.getName());

        if (findCourse != null) {
            throw new EntityExistsException("Course with Name \"" + course.getName() + "\" already exists");
        } else {
            return courseRepository.save(course);
        }
    }

    @Override
    public void deleteCourse(String name) {
        Course course = courseRepository.findByName(name);

        courseRepository.delete(course);
    }

    @Override
    public Course editCourse(Course course) {
        return null;
    }

    @Override
    public Course getCourse(String name) {
        Course course = courseRepository.findByName(name);

        if (course == null) {
            throw new EntityExistsException("Course \"" + name + "\" not found");
        }

        return course;

    }

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public void addUserToCourse(String email, String courseName) {
        User user = userRepository.findByEmail(email);
        Course course = courseRepository.findByName(courseName);

        course.getUser_id().add(user);
    }
}
