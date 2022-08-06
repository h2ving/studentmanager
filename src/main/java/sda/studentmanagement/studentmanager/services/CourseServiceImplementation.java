package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.dto.AddCourseFormDto;
import sda.studentmanagement.studentmanager.projections.CourseDataChartsProjection;
import sda.studentmanagement.studentmanager.projections.CourseDataProjection;
import sda.studentmanagement.studentmanager.repositories.CourseRepository;
import sda.studentmanagement.studentmanager.repositories.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
    public List<CourseDataChartsProjection> getCoursesCharts() {
        return courseRepository.findAllCourseUsersChartsProjectedBy();
    }

    @Override
    public List<HashMap<Object, Object>> getCourseCountCharts() {
        int finishedCourses = 0;
        int ongoingCourses = 0;
        int allCourses = 0;

        for (Course course : getCourses()) {
            if (course.getEndDate().isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
                finishedCourses ++;
            } else if (
                    course.getEndDate().isAfter(ChronoLocalDate.from(LocalDateTime.now())) &&
                            course.getStartDate().isBefore(ChronoLocalDate.from(LocalDateTime.now()))
            ) {
                ongoingCourses ++;
            }

            allCourses++;
        }

        List<HashMap<Object, Object>> courseCount = new ArrayList<>();

        HashMap<Object, Object> finishedCoursesCount = new HashMap<>();
        finishedCoursesCount.put("name", "Finished");
        finishedCoursesCount.put("value", finishedCourses);

        HashMap<Object, Object> ongoingCoursesCount = new HashMap<>();
        ongoingCoursesCount.put("name", "Ongoing");
        ongoingCoursesCount.put("value", ongoingCourses);

        HashMap<Object, Object> coursesCount = new HashMap<>();
        coursesCount.put("name", "ALl Courses");
        coursesCount.put("value", allCourses);

        courseCount.add(coursesCount);
        courseCount.add(ongoingCoursesCount);
        courseCount.add(finishedCoursesCount);

        return courseCount;
    }

    @Override
    public Page<Course> getPaginatedCourses(int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);

        return courseRepository.findAll(paging);
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
    public void saveCourse(AddCourseFormDto addCourseForm) {
        System.out.println(addCourseForm);

        Course newCourse = new Course();
        List<Course> courses = getCourses();

        for (Course course : courses) {
            if (course.getName().equals(addCourseForm.getName())) {
                throw new EntityNotFoundException("Course with name " + addCourseForm.getName() + " already exists");
            }
        }

        if (addCourseForm.getStartDate() != null || addCourseForm.getEndDate() != null) {
            if (addCourseForm.getEndDate().isBefore(addCourseForm.getStartDate())) {
                throw new IllegalArgumentException("Course end date is before starting date");
            }
        }

        newCourse.setName(addCourseForm.getName());
        newCourse.setDescription(addCourseForm.getDescription());
        newCourse.setStartDate(addCourseForm.getStartDate());
        newCourse.setEndDate(addCourseForm.getEndDate());
        newCourse.setAcademicHours(addCourseForm.getAcademicHours());
        newCourse.setRemote(addCourseForm.getRemote());

        courseRepository.save(newCourse);
    }

    @Override
    public Course savePopulateCourse(Course course) {
        Optional<Course> findCourse = courseRepository.findById(course.getId());

        if (findCourse.isPresent()) {
            throw new EntityExistsException("Course \"" + course.getName() + "\" already exists");
        } else {
            return courseRepository.save(course);
        }
    }

    @Override
    public Course editCourse(Course course) {
        Course editCourse = getCourse(course.getId());

        if (course.getName().isBlank() || course.getName().length() > 100) {
            throw new IllegalArgumentException("Invalid Course Name Format");
        }

        if (course.getDescription().isBlank()) {
            throw new IllegalArgumentException("Description can't be empty");
        }

        if (course.getStartDate() == null || course.getEndDate() == null) {
            throw new IllegalArgumentException("Invalid Course Date");
        }

        if (course.getStartDate().isAfter(course.getEndDate())) {
            throw new IllegalArgumentException("Invalid Course Start Date");
        }

        if (course.getAcademicHours() == null) {
            throw new IllegalArgumentException("Duration can't be empty");
        }


        editCourse.setName(course.getName());
        editCourse.setDescription(course.getDescription());
        editCourse.setAcademicHours(course.getAcademicHours());
        editCourse.setStartDate(course.getStartDate());
        editCourse.setEndDate(course.getEndDate());
        editCourse.setRemote(course.getRemote());

        courseRepository.save(editCourse);

        return editCourse;
    }

    @Override
    public void deleteCourse(long courseId) {
        Course course = courseRepository.findById(courseId);

        if (course == null) {
            throw new EntityNotFoundException("Course not found");
        } else {
            courseRepository.delete(course);
        }
    }
}
