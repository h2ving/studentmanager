package sda.studentmanagement.studentmanager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.studentmanagement.studentmanager.domain.Announcement;
import sda.studentmanagement.studentmanager.dto.AddAnnouncementFormDto;
import sda.studentmanagement.studentmanager.dto.EditAnnouncementFormDto;
import sda.studentmanagement.studentmanager.services.AnnouncementServiceImplementation;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AnnouncementController {
    private final AnnouncementServiceImplementation announcementService;

    /**
     * @Route GET /api/announcements/user/{userId}
     * @Desc Get all User Announcements
     * @Access
     */
    @GetMapping("/announcements/user/{userId}")
    public ResponseEntity<List<Announcement>> getUserAnnouncements(@PathVariable("userId") long userId) {
        return ResponseEntity.ok(announcementService.getUserAnnouncements(userId));
    }

    /**
     * @Route GET /api/announcements/course/{courseId}
     * @Desc Get all Course Announcements
     * @Access
     */
    @GetMapping("/announcements/course/{courseId}")
    public ResponseEntity<?> geCourseAnnouncements(@PathVariable("courseId") long courseId) {
        try {
            List<Announcement> announcements = announcementService.getCourseAnnouncements(courseId);

            return new ResponseEntity<>(
                announcements, new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * @Route POST /api/announcement
     * @Desc Add a new Announcement
     * @Access Professor, Admin
     */
    @PostMapping("/announcement")
    public ResponseEntity<?> saveAnnouncement(@RequestBody AddAnnouncementFormDto announcementForm) {
        try {
            Announcement announcement = announcementService.saveAnnouncement(announcementForm);

            return new ResponseEntity<>(
                    announcement, new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        } catch(ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append("- ").append(violation.getMessage()).append("</br>"));

            String errorMessage = builder.toString();

            return new ResponseEntity<>(
                    errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * @Route PATCH /api/announcement/{announcementId}
     * @Desc Edit Announcement
     * @Access Professor, Admin
     */
    @PatchMapping("/announcement/{announcementId}")
    public ResponseEntity<?> editAnnouncement(
            @PathVariable("announcementId") long announcementId, @RequestBody EditAnnouncementFormDto announcementForm
    ) {
        try {
            Announcement announcement = announcementService.editAnnouncement(announcementId, announcementForm);

            return new ResponseEntity<>(
                    announcement, new HttpHeaders(), HttpStatus.OK
            );
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * @Route DELETE /api/announcement/{announcementId}
     * @Desc Delete Announcement
     * @Access Professor, Admin
     */
    @DeleteMapping("/announcement/{announcementId}")
    public ResponseEntity<?> deleteAnnouncement(@PathVariable long announcementId) {
        try {
            announcementService.deleteAnnouncement(announcementId);

            return new ResponseEntity<>(
                    "Announcement deleted successfully", new HttpHeaders(), HttpStatus.OK
            );
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND
            );
        }
    }

}
