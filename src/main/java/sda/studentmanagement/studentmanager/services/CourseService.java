package sda.studentmanagement.studentmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.request.CourseRequest;
import sda.studentmanagement.studentmanager.domain.response.CourseResponse;
import sda.studentmanagement.studentmanager.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<CourseResponse> createNewCourse(int userId, CourseRequest request) {
        Course course = new Course(request.getName(), request.getDescription(),
                request.getStartDate(), request.getEndDate(), request.getAcademicHours(),
                request.getRemote(), userId);
        courseRepository.save(course);
        return fromVOs(courseRepository.findAllActive(userId));
    }

    public List<CourseResponse> getAll(int userId) {
        return fromVOs(courseRepository.findAllActive(userId));
    }

    private List<CourseResponse> fromVOs(List<Course> courses) {
        List<CourseResponse> response = new ArrayList<>();
        for (Course course : courses) {
            response.add(fromVO(course));
        }

        return response;
    }
    private CourseResponse fromVO(Course course) {
        return new CourseResponse(course.getId(), course.getName(), course.getDescription(), course.getStartDate(),course.getEndDate(),course.getAcademicHours(), course.getRemote(), course.getUserId());
    }
}
