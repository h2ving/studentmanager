import { Component, OnInit, Input } from '@angular/core';
import { Attendance } from 'src/app/models/attendance.module';

@Component({
  selector: 'app-student-attendance-item',
  templateUrl: './student-attendance-item.component.html',
  styleUrls: ['./student-attendance-item.component.scss']
})
export class StudentAttendanceItemComponent implements OnInit {
  @Input() attendance: Attendance;

  constructor() { }

  ngOnInit(): void {
  }

}
