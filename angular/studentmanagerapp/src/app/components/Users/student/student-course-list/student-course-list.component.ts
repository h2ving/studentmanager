import { Component, OnInit, Input } from '@angular/core';
import { Course } from 'src/app/models/course.module';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-student-course-list',
  templateUrl: './student-course-list.component.html',
  styleUrls: ['./student-course-list.component.scss']
})
export class StudentCourseListComponent implements OnInit {
  @Input() currentUser: User;
  myCourses: boolean = true;
  allCourses: boolean = false;
  addCourse: boolean = false;
  courses: any[] = [];

  constructor() { }

  ngOnInit(): void {
    this.courses = [
      new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true),
      new Course(2, 'MqSql Workshop 2021', 'Sql queries', new Date(), new Date(), 45, false),
      new Course(3, 'Angular Crash Course', 'Angular 12', new Date(), new Date(), 15, true),
      new Course(4, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(5, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(6, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(7, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(8, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(8, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(8, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(8, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(8, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(8, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(8, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(8, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(8, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(8, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(8, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      new Course(8, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
    ]
  }

  toggleMyCourses(): void {
    this.myCourses = true;
    this.allCourses = false;
    this.addCourse = false;

    this.switchCourses();
  }

  toggleAllCourses(): void {
    this.myCourses = false;
    this.allCourses = true;
    this.addCourse = false;

    this.switchCourses();
  }

  toggleAddCourses(): void {
    this.myCourses = false;
    this.allCourses = false;
    this.addCourse = false;

    this.switchCourses();
  }

  switchCourses(): void {
    if (this.myCourses) {
      this.courses = [
        new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true),
        new Course(2, 'MqSql Workshop 2021', 'Sql queries', new Date(), new Date(), 45, false),
        new Course(3, 'Angular Crash Course', 'Angular 12', new Date(), new Date(), 15, true),
        new Course(4, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
        new Course(5, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
        new Course(6, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
        new Course(7, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
        new Course(8, 'React Hooks', 'All the React hooks in detail', new Date(), new Date(), 8, false),
      ]
    }

    if (this.allCourses) {
      this.courses = [
        new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true),
        new Course(2, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true),
        new Course(3, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true),
        new Course(4, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true),
        new Course(5, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true),
        new Course(6, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true),
        new Course(7, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true),
        new Course(8, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true),
        new Course(9, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true),
      ]
    }

    if (this.addCourse) {
      this.courses = [];
    }
  }
}
