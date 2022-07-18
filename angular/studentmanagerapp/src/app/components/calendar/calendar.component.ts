import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { CalendarEvent, CalendarView } from 'angular-calendar';
import * as moment from 'moment';

import { SessionService } from 'src/app/services/session.service';
import { NotificationService } from 'src/app/services/notification.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CalendarComponent implements OnInit {
  view: CalendarView = CalendarView.Month;
  viewDate: Date = new Date();
  events: CalendarEvent[] = [];
  updateSessionsDataEventSubscriptions: Subscription;

  constructor(private sessionService: SessionService, public notificationService: NotificationService) {
    this.updateSessionsDataEventSubscriptions = this.sessionService.getUpdateSessionsDataEvent()
      .subscribe(() => {
        this.getSessionsData();
      });
  }

  ngOnInit(): void {
    this.getSessionsData();
  }

  getSessionsData(): void {
    this.events = [];

    this.sessionService.getSessionsData()
      .subscribe({
        next: (response) => {
          let courses = [...new Set(response.map(session => session.course))];
          let colors: Array<any> = [];

          for (let course in courses) {
            const randomColor: string = this.getRandomColor();

            colors.push({
              primary: randomColor,
              secondary: randomColor
            })
          }

          response.map((event) => {
            const colorIndex = courses.findIndex((course) => course === event.course);

            const calendarEvent = {
              title: event.description,
              start: moment(event.startDateTime).toDate(),
              end: moment(event.startDateTime).add(event.academicHours * 45, 'm').toDate(),
              color: colors[colorIndex]
            }

            this.events.push(calendarEvent);
          })
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        }
      })
  }

  getRandomColor(): string {
    const transparency = '0.5';
    let color = 'rgba(';

    for (var i = 0; i < 3; i++) {
      color += Math.floor(Math.random() * 255) + ',';
    }

    color += transparency + ')';

    return color;
  }
}
