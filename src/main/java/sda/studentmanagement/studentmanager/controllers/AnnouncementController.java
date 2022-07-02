package sda.studentmanagement.studentmanager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sda.studentmanagement.studentmanager.domain.Announcement;
import sda.studentmanagement.studentmanager.services.AnnouncementServiceImplementation;

import java.util.List;

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
}
