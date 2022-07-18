package sda.studentmanagement.studentmanager.services;

import sda.studentmanagement.studentmanager.domain.Announcement;
import sda.studentmanagement.studentmanager.dto.AddAnnouncementFormDto;
import sda.studentmanagement.studentmanager.dto.EditAnnouncementFormDto;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface AnnouncementService {
    Announcement getAnnouncement(long announcementId);

    List<Announcement> getUserAnnouncements(long userId);

    List<Announcement> getCourseAnnouncements(long courseId);

    Announcement saveAnnouncement(AddAnnouncementFormDto announcement) throws ConstraintViolationException;

    Announcement editAnnouncement(long announcementId, EditAnnouncementFormDto announcementForm);

    void deleteAnnouncement(long announcementId);
}
