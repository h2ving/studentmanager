package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.projections.CourseDataProjection;
import sda.studentmanagement.studentmanager.repositories.CourseRepository;
import sda.studentmanagement.studentmanager.repositories.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourseServiceImplementation implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getUserCourses(long userId, boolean checkCourseContainsUser) {
        List<Course> userCourses = new ArrayList<>();
        User user = userRepository.findById(userId);

        if (user == null) {
            throw new EntityNotFoundException("Invalid User ID");
        }

        for (Course course : getCourses()) {
            if (checkCourseContainsUser) {
                if (course.getUsers().contains(user)) {
                    userCourses.add(course);
                }
            } else {
                if (!course.getUsers().contains(user)) {
                    userCourses.add(course);
                }
            }

        }

        return userCourses;
    }

    @Override
    public List<String> getUserCourseNames(long userId) {
        List<String> userCourseNames = new ArrayList<>();
        User user = userRepository.findById(userId);

        if (user != null) {
            for (Course course : getUserCourses(userId, true)) {
                userCourseNames.add(course.getName());
            }
        }

        return userCourseNames;
    }

    @Override
    public List<CourseDataProjection> getCoursesData() {
        return courseRepository.findAllProjectedBy();
    }

    @Override
    public Course getCourse(long courseId) {
        Course course = courseRepository.findById(courseId);

        if (course == null) {
            throw new EntityNotFoundException("Course not found");
        }

        return course;
    }

    @Override
    public void addUserToCourse(long userId, long courseId) {
        User user = userRepository.findById(userId);
        Course course = courseRepository.findById(courseId);

        if (course.getUsers().contains(user)) {
            throw new EntityExistsException("User is already in the course list");
        }

        course.getUsers().add(user);
    }

    @Override
    public void removeUserFromCourse(long userId, long courseId) {
        User user = userRepository.findById(userId);
        Course course = courseRepository.findById(courseId);

        course.getUsers().remove(user);
    }

    @Override
    public Course saveCourse(Course course) {
        Optional<Course> findCourse = courseRepository.findById(course.getId());

        if (findCourse.isPresent()) {
            throw new EntityExistsException("Course \"" + course.getName() + "\" already exists");
        } else {
            return courseRepository.save(course);
        }
    }


    @Override
    public Course editCourse(Course course) {
        // Todo

        return null;
    }

    @Override
    public void deleteCourse(long courseId) {
        Course course = courseRepository.findById(courseId);

        courseRepository.delete(course);
    }
}
