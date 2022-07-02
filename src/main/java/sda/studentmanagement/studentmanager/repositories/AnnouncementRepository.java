package sda.studentmanagement.studentmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sda.studentmanagement.studentmanager.domain.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    Announcement findById(long announcementId);
}
