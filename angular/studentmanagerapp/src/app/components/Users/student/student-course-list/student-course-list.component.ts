import { Component, OnInit, Input } from '@angular/core';
import { Course } from 'src/app/models/course.module';
import { User } from 'src/app/models/user.model';
import { CourseService } from 'src/app/services/course.service';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-student-course-list',
  templateUrl: './student-course-list.component.html',
  styleUrls: ['./student-course-list.component.scss']
})
export class StudentCourseListComponent implements OnInit {
  @Input() currentUser: User;
  myCourses: boolean = true;
  allCourses: boolean = false;
  courses: Array<Course>;

  constructor(private courseService: CourseService, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.courseService.getUserCourses(this.currentUser.email).subscribe({
      next: (res) => {
        console.log(this.currentUser)
        this.courses = res;
      },
      error: (err) => {
        this.notificationService.showError('Please try again later.', 'Failed to receive courses.')
      }
    });
  }

  toggleMyCourses(): void {
    this.myCourses = true;
    this.allCourses = false;

    this.switchCourses();
  }

  toggleAllCourses(): void {
    this.myCourses = false;
    this.allCourses = true;

    this.switchCourses();
  }

  switchCourses(): void {
    if (this.myCourses) {
      this.courseService.getUserCourses(this.currentUser.email).subscribe({
        next: (res) => {
          this.courses = res;
        },
        error: (err) => {
          this.notificationService.showError('Please try again later.', 'Failed to receive courses.')
        }
      });
    }

    if (this.allCourses) {
      this.courseService.getAllCourses().subscribe({
        next: (res) => {
          this.courses = res;
        },
        error: (err) => {
          this.notificationService.showError('Please try again later.', 'Failed to receive all courses.')
        }
      });
    }
  }
}
