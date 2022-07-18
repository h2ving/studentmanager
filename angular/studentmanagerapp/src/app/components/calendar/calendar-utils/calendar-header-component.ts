import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CalendarView } from 'angular-calendar';

@Component({
  selector: 'calendar-utils-calendar-header',
  styleUrls: ['./calendar-header-component.scss'],
  template: `
    <div class="calendar-header-container">
      <div class="calendar-time">
        <div
          class="btn"
          mwlCalendarPreviousView
          [view]="view"
          [(viewDate)]="viewDate"
          (viewDateChange)="viewDateChange.next(viewDate)"
        >
          Previous
        </div>

        <div
          class="btn"
          mwlCalendarToday
          [(viewDate)]="viewDate"
          (viewDateChange)="viewDateChange.next(viewDate)"
        >
          Today
        </div>

        <div
          class="btn"
          mwlCalendarNextView
          [view]="view"
          [(viewDate)]="viewDate"
          (viewDateChange)="viewDateChange.next(viewDate)"
        >
          Next
        </div>
      </div>

      <div class="calendar-date">
        <h3>
          {{ viewDate | calendarDate: view + 'ViewTitle':locale }}
        </h3>
      </div>

      <div class="calendar-view">
        <div
          class="btn"
          (click)="viewChange.emit(CalendarView.Month)"
          [class.active]="view === CalendarView.Month"
        >
          Month
        </div>

        <div
          class="btn"
          (click)="viewChange.emit(CalendarView.Week)"
          [class.active]="view === CalendarView.Week"
        >
          Week
        </div>

        <div
          class="btn"
          (click)="viewChange.emit(CalendarView.Day)"
          [class.active]="view === CalendarView.Day"
        >
          Day
        </div>
      </div>
    </div>
  `
})
export class CalendarHeaderComponent {
  @Input() view: CalendarView;

  @Input() viewDate: Date;

  @Input() locale: string = 'en';

  @Output() viewChange = new EventEmitter<CalendarView>();

  @Output() viewDateChange = new EventEmitter<Date>();

  CalendarView = CalendarView;
}
