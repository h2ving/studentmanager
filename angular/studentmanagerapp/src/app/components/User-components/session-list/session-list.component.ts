import { Component, OnInit, Input } from '@angular/core';
import { Session } from 'src/app/models/session.module';
import { SessionService } from 'src/app/services/session.service';
import { NotificationService } from 'src/app/services/notification.service';
import { UserDataInterface } from 'src/app/interfaces/user-data-interface';

import { Subscription } from 'rxjs';

@Component({
  selector: 'app-session-list',
  templateUrl: './session-list.component.html',
  styleUrls: ['./session-list.component.scss']
})
export class SessionListComponent implements OnInit {
  @Input() currentUser: UserDataInterface;
  updateSessionsEventSubscriptions: Subscription;
  userSessions: Array<Session>;

  constructor(private sessionService: SessionService, public notificationService: NotificationService) {
    this.updateSessionsEventSubscriptions = this.sessionService.getUpdateSessionsEvent()
      .subscribe(() => {
        this.updateSessions();
      });
  }

  ngOnInit() {
    this.updateSessions();
  }

  updateSessions(): void {
    this.sessionService.getUserSessions(this.currentUser.id)
      .subscribe({
        next: (response) => {
          this.userSessions = response;
        },
        error: () => {
          this.notificationService.showError(`Failed to receive Sessions. Please refresh the page or try again later`);
        }
      });
  }
}
