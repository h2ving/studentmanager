package sda.studentmanagement.studentmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.domain.request.CourseRequest;
import sda.studentmanagement.studentmanager.domain.response.CourseResponse;
import sda.studentmanagement.studentmanager.services.CourseService;
import sda.studentmanagement.studentmanager.services.UserCache;
import sda.studentmanagement.studentmanager.utils.UserUtils;

import java.util.List;

@RestController
@RequestMapping("/courses/")
@CrossOrigin("http://localhost:4200/")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    UserCache userCache;

    @PostMapping("/")
    public ResponseEntity<List<CourseResponse>> createCourse(@RequestBody CourseRequest request) {
        User user = userCache.getByUsername(UserUtils.getAuthenticatedUserName());

        return ResponseEntity.ok(courseService.createNewCourse(user.getId(), request));
    }

    @GetMapping("/")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        User user = userCache.getByUsername(UserUtils.getAuthenticatedUserName());

        return ResponseEntity.ok(courseService.getAll(user.getId()));
    }
}
