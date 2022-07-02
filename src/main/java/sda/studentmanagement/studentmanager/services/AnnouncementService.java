package sda.studentmanagement.studentmanager.services;

import sda.studentmanagement.studentmanager.domain.Announcement;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> getUserAnnouncements(long userId);

    void saveAnnouncement(Announcement announcement);
}
