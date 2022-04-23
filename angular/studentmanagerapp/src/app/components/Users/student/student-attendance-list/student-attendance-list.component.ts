import { Component, OnInit } from '@angular/core';
import { Attendance } from 'src/app/models/attendance.module';
import { Course } from 'src/app/models/course.module';
import { Session } from 'src/app/models/session.module';

@Component({
  selector: 'app-student-attendance-list',
  templateUrl: './student-attendance-list.component.html',
  styleUrls: ['./student-attendance-list.component.scss']
})
export class StudentAttendanceListComponent implements OnInit {
  attendanceList: any[] = [
    new Attendance(
      2,
      new Session(
        1, 'First meeting of Java', new Date(), 4,
        new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
      true
    ),
    new Attendance(
      3,
      new Session(2, 'Second meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
      false
    ),
    new Attendance(
      4,
      new Session(3, 'Third meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
      true
    ),
    new Attendance(
      1,
      new Session(4, 'Fourth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
      false
    ),
    new Attendance(
      1,
      new Session(4, 'Fourth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
      false
    ),
    new Attendance(
      1,
      new Session(4, 'Fourth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
      false
    ),
    new Attendance(
      1,
      new Session(4, 'Fourth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
      false
    ),
    new Attendance(
      1,
      new Session(4, 'Fourth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
      false
    ),
    new Attendance(
      1,
      new Session(4, 'Fourth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
      false
    ),
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
