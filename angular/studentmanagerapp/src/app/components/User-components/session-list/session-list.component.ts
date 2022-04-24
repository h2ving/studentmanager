import { Component, OnInit, Input } from '@angular/core';
import { Course } from 'src/app/models/course.module';
import { Session } from 'src/app/models/session.module';
import { SessionService } from 'src/app/services/session.service';
import { NotificationService } from 'src/app/services/notification.service';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-session-list',
  templateUrl: './session-list.component.html',
  styleUrls: ['./session-list.component.scss']
})
export class SessionListComponent implements OnInit {
  @Input() currentUser: User;
  userSessions: Array<Session>;

  constructor(private sessionService: SessionService, private authService: AuthService, public notificationService: NotificationService) {
  }

  ngOnInit(): void {
    console.log(this.currentUser)

    // this.sessionService.getUserSessions(this.currentUser.email)
    //   .subscribe({
    //     next: (response) => {
    //       console.log(response);

    //       this.userSessions = response;
    //     },
    //     error: (error) => {
    //       this.notificationService.showError('Please refresh the page', 'Error loading Sessions');
    //     }
    //   });
  }

}
