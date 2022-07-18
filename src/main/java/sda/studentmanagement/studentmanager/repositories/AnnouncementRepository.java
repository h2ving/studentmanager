package sda.studentmanagement.studentmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sda.studentmanagement.studentmanager.domain.Announcement;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    Announcement findById(long announcementId);

    List<Announcement> findAll();
}
