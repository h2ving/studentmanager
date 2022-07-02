import { Component, OnInit, Input } from '@angular/core';
import { UserDataInterface } from 'src/app/interfaces/user-data-interface';
import { Attendance } from 'src/app/models/attendance.module';
import { AttendanceService } from 'src/app/services/attendance.service';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-student-attendance-table',
  templateUrl: './student-attendance-table.component.html',
  styleUrls: ['./student-attendance-table.component.scss']
})
export class StudentAttendanceTableComponent implements OnInit {
  @Input() currentUser: UserDataInterface;
  attendances: Array<Attendance>;
  attendancesCopy: Array<Attendance>;
  courses: Array<string>;
  selectedCourseAttendance: string = 'All Attendances';

  constructor(private attendanceService: AttendanceService, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.getAttendances();
  }

  getAttendances(): void {
    this.attendanceService.getUserAttendance(this.currentUser.id)
      .subscribe({
        next: (response: any) => {
          const { userAttendance, userCourses } = response;

          this.attendances = userAttendance;
          this.attendancesCopy = userAttendance;
          this.courses = userCourses
        },
        error: () => {
          this.notificationService.showError('Failed to receive Attendance list.');
        }
      })
  }

  filterAttendances(event: Event): void {
    const { value } = event.target as HTMLInputElement;

    this.selectedCourseAttendance = value;

    if (value !== 'All Attendances') {
      this.attendances = this.attendancesCopy
        .filter((attendance) => attendance.session.course.name === value);
    } else {
      this.getAttendances();
    }
  }
}
