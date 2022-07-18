package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.studentmanagement.studentmanager.domain.Announcement;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.dto.AddAnnouncementFormDto;
import sda.studentmanagement.studentmanager.dto.EditAnnouncementFormDto;
import sda.studentmanagement.studentmanager.repositories.AnnouncementRepository;
import sda.studentmanagement.studentmanager.repositories.CourseRepository;
import sda.studentmanagement.studentmanager.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AnnouncementServiceImplementation implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public Announcement getAnnouncement(long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId);

        if (announcement == null) {
            throw new EntityNotFoundException("Announcement not found");
        }

        return announcement;
    }

    @Override
    public List<Announcement> getUserAnnouncements(long userId) {
        User user = userRepository.findById(userId);
        List<Announcement> announcements = announcementRepository.findAll();

        announcements = announcements.stream()
                .filter(announcement ->
                        announcement.getCourse().getUsers().contains(user) &&
                        announcement.getCourse().getEndDate().isAfter(LocalDate.now().plusDays(7))
                )
                .collect(Collectors.toList());

        return announcements.stream()
                .filter(announcement -> announcement.getCourse().getUsers().contains(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<Announcement> getCourseAnnouncements(long courseId) {
        List<Announcement> announcements = announcementRepository.findAll();
        List<Announcement> courseAnnouncements = new ArrayList<>();
        Course course = courseRepository.findById(courseId);

        if (course != null) {
            for (Announcement announcement : announcements) {
                if (announcement.getCourse().equals(course)) {
                    courseAnnouncements.add(announcement);
                }
            }
        }  else {
            throw new EntityNotFoundException("Course not found");
        }

        return courseAnnouncements;
    }

    @Override
    public Announcement saveAnnouncement(AddAnnouncementFormDto announcementForm) throws ConstraintViolationException {
        Announcement newAnnouncement = new Announcement();
        Course course = courseRepository.findById(announcementForm.getCourseId());

        if (course != null) {
            newAnnouncement.setAnnouncement(announcementForm.getAnnouncement());
            newAnnouncement.setCourse(course);

            announcementRepository.save(newAnnouncement);

            return newAnnouncement;
        } else {
            throw new EntityNotFoundException("Course not found");
        }
    }

    @Override
    public Announcement editAnnouncement(long announcementId, EditAnnouncementFormDto announcementForm) {
        Announcement announcement = getAnnouncement(announcementId);

        if (announcement != null) {
            if (announcementForm.getAnnouncement().isBlank()) {
                throw new IllegalArgumentException("Announcement can not be blank");
            }
            if (announcementForm.getAnnouncement().length() > 255) {
                throw new IllegalArgumentException("Announcement is limited to 255 characters");
            }

            announcement.setAnnouncement(announcementForm.getAnnouncement());

            return announcement;
        } else {
        throw new EntityNotFoundException("Announcement not found");
    }
    }

    @Override
    public void deleteAnnouncement(long announcementId) {
        Announcement announcement = getAnnouncement(announcementId);

        if (announcement == null) {
            throw new EntityNotFoundException("Announcement not found");
        } else {
            announcementRepository.delete(announcement);
        }
    }
}
