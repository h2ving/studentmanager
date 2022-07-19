import { Component, Input, OnInit } from '@angular/core';
import { UserDataInterface } from 'src/app/interfaces/user-data-interface';
import { Announcement } from 'src/app/models/announcement.model';
import { AnnouncementService } from 'src/app/services/announcement.service';
import { NotificationService } from 'src/app/services/notification.service';
import { faBullhorn, faCheck } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-student-announcement-list',
  templateUrl: './student-announcement-list.component.html',
  styleUrls: ['./student-announcement-list.component.scss']
})
export class StudentAnnouncementListComponent implements OnInit {
  @Input() currentUser: UserDataInterface;
  announcements: Array<Announcement>;
  toggleAnnouncements: boolean = false;
  faBullhorn = faBullhorn;
  faCheck = faCheck;

  constructor(private announcementService: AnnouncementService, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.getAnnouncements();
  }

  getAnnouncements(): void {
    this.announcementService.getUserAnnouncements(this.currentUser.id)
      .subscribe({
        next: (response) => {
          this.announcements = response;
        },
        error: (error) => {
          console.log(error)
          this.notificationService.showError('Failed to receive Announcements')
        }
      })
  }
}
