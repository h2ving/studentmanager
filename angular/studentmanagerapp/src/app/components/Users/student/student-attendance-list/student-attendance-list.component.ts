import { Component, OnInit, Input } from '@angular/core';
import { Attendance } from 'src/app/models/attendance.module';
import { User } from 'src/app/models/user.model';
import { AttendanceService } from 'src/app/services/attendance.service';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-student-attendance-list',
  templateUrl: './student-attendance-list.component.html',
  styleUrls: ['./student-attendance-list.component.scss']
})
export class StudentAttendanceListComponent implements OnInit {
  @Input() currentUser: User;
  attendanceList: Array<Attendance>;

  constructor(private attendanceService: AttendanceService, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.attendanceService.getUserAttendance(this.currentUser.email)
      .subscribe({
        next: (res) => {
          this.attendanceList = res;
        },
        error: (err) => {
          this.notificationService.showError('Please try again later.', 'Failed to receive Attendance list.')
        }
      })
  }

}
