import { Component, OnInit, Input } from '@angular/core';
import { faArrowAltCircleRight } from '@fortawesome/free-solid-svg-icons';
import { CourseService } from 'src/app/services/course.service';
import { SessionService } from 'src/app/services/session.service';
import { NotificationService } from 'src/app/services/notification.service';

import { UserDataInterface } from 'src/app/interfaces/user-data-interface';
import { Course } from 'src/app/models/course.module';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-course-table',
  templateUrl: './course-table.component.html',
  styleUrls: ['./course-table.component.scss']
})
export class CourseTableComponent implements OnInit {
  @Input() currentUser: UserDataInterface;
  courses: Array<Course>;
  selectedCourseType: string;

  faArrowAltCircleRight = faArrowAltCircleRight;

  constructor(
    private courseService: CourseService,
    private sessionService: SessionService,
    private authService: AuthService,
    public notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    this.switchCourses('Ongoing');
  }

  switchCourses(type: string): void {
    enum CourseTypes {
      all = 'All',
      ongoing = 'Ongoing',
      past = 'Past',
    };

    const checkCourseContainsUser: boolean = (type !== CourseTypes.all);

    this.selectedCourseType = type;

    this.courseService.getUserCourses(this.currentUser.id, checkCourseContainsUser)
      .subscribe({
        next: (response) => {
          this.courses = response;
          console.log(response)
        },
        error: () => {
          this.notificationService.showError('Failed to receive courses.');
        },
        complete: () => {
          const today = new Date();

          if (type !== CourseTypes.all) {
            this.courses = this.courses.filter((course) => {
              const endDate = new Date(course.endDate);

              if (type === CourseTypes.past) return endDate < today;

              return endDate >= today;
            })
          }
        }
      });
  }

  joinCourse(course: Course): void {
    this.courseService.joinCourse(this.currentUser.id, course.id)
      .subscribe({
        next: (response) => {
          this.notificationService.showSuccess(response);
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        },
        complete: () => {
          this.switchCourses(this.selectedCourseType);

          this.sessionService.sendUpdateSessionsEvent();
          this.authService.sendUpdateUserDataEvent();
        }
      });
  }

  leaveCourse(course: Course): void {
    this.courseService.leaveCourse(this.currentUser.id, course.id)
      .subscribe({
        next: (response) => {
          this.notificationService.showSuccess(response);
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        },
        complete: () => {
          this.switchCourses(this.selectedCourseType);

          this.sessionService.sendUpdateSessionsEvent();
          this.authService.sendUpdateUserDataEvent()
        }
      });
  }
}
