package sda.studentmanagement.studentmanager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.studentmanagement.studentmanager.domain.Announcement;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.repositories.AnnouncementRepository;
import sda.studentmanagement.studentmanager.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AnnouncementServiceImplementation implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;

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
    public void saveAnnouncement(Announcement announcement) {
        // Todo
    }
}
