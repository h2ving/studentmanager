import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { CourseService } from 'src/app/services/course.service';
import { NotificationService } from 'src/app/services/notification.service';
import { User } from 'src/app/models/user.model';
import { faLightbulb, faEdit, faTrash, faUsers, faBook } from '@fortawesome/free-solid-svg-icons';
import { Course } from 'src/app/models/course.module';
import { Session } from 'src/app/models/session.module';
import { SessionService } from 'src/app/services/session.service';
import { AttendanceService } from 'src/app/services/attendance.service';
import { GradeService } from 'src/app/services/grade.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit {
  faLightbulb = faLightbulb;
  faEdit = faEdit;
  faTrash = faTrash;
  faUsers = faUsers;
  faBook = faBook;

  currentUser: User = this.authService.getCurrentUser;
  currentUserId: number;
  currentUserLoaded: boolean = true;

  pageNumber: number = 1;
  pageSize: number = 10;
  firstPage: boolean;
  lastPage: boolean;
  courses: Array<Course>;

  studentsCourseId: number | null = null;

  courseSessionsCourseId: number | null = null
  courseSessions: Array<Session> | null;

  courseAttendancesCourseId: number | null = null;
  courseAttendances: Array<any> | null;
  courseAttendancesPageNumber: number = 1;
  courseAttendancesPageSize: number = 10;
  courseAttendancesFirstPage: boolean;
  courseAttendancesLastPage: boolean;

  courseGradesCourseId: number | null = null;
  courseGrades: Array<any> | null;
  courseGradesPageNumber: number = 1;
  courseGradesPageSize: number = 10;
  courseGradesFirstPage: boolean;
  courseGradesLastPage: boolean;

  constructor(private authService: AuthService, private courseService: CourseService, private sessionService: SessionService, private attendanceService: AttendanceService, private gradeService: GradeService, private activatedRoute: ActivatedRoute, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => this.currentUserId = params['id']);

    if (!this.currentUser.user) {
      this.getUserData();
    }

    this.getCourses();
  }

  getUserData(): void {
    this.currentUserLoaded = false;

    this.authService.getUserDatatById(this.currentUserId).subscribe({
      next: () => {
        this.currentUser = this.authService.currentUser;

        this.currentUser.user.coursesAssigned = this.currentUser.user.coursesAssigned.sort((course, locales: any) => course.description.localeCompare(locales.description))

        this.currentUserLoaded = true;
      },
      error: () => {
        this.notificationService.showError('Error getting User data, please refresh the page');
      }
    });
  }

  getCourses() {
    this.courseService.getPaginatedCourses(this.pageNumber - 1, this.pageSize)
      .subscribe({
        next: (response: any) => {
          this.courses = response.content;
          this.firstPage = response.first;
          this.lastPage = response.last;
        },
        error: () => {
          this.notificationService.showError("Error Receiving Courses. Please Try again later");
        }
      });
  }

  getCourseSessions() {
    this.sessionService.getCourseSessions(this.courseSessionsCourseId!)
      .subscribe({
        next: (response) => {
          this.courseSessions = response;
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        }
      })
  }

  getCourseAttendances() {
    this.attendanceService.getCourseAttendances(this.courseAttendancesCourseId!, this.courseAttendancesPageNumber - 1, this.courseAttendancesPageSize)
      .subscribe({
        next: (response: any) => {
          this.courseAttendances = response.content;
          this.courseAttendancesFirstPage = response.first;
          this.courseAttendancesLastPage = response.last;
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        }
      })
  }

  getCourseGrades() {
    this.gradeService.getCourseGrades(this.courseGradesCourseId!, this.courseGradesPageNumber - 1, this.courseGradesPageSize)
      .subscribe({
        next: (response: any) => {
          this.courseGrades = response.content;
          this.courseGradesFirstPage = response.first;
          this.courseGradesLastPage = response.last;
          console.log(response);
        },
        error: (error) => {
          console.log(error)
          this.notificationService.showError(error.error);
        }
      })
  }

  switchPage(pageNumber: number): void {
    this.pageNumber = pageNumber;

    this.getCourses();
  }

  switchCourseAttendancesPage(pageNumber: number): void {
    this.courseAttendancesPageNumber = pageNumber;

    this.getCourseAttendances();
  }

  switchCourseGradesPage(pageNumber: number): void {
    this.courseGradesPageNumber = pageNumber;

    this.getCourseGrades();
  }

  toggleStudentsCourseId(courseId: number): void {
    if (courseId !== this.studentsCourseId) {
      this.studentsCourseId = courseId;
    } else {
      this.studentsCourseId = null;
    }
  }

  toggleCourseSessions(courseId: number): void {
    if (courseId !== this.courseSessionsCourseId) {
      this.courseSessionsCourseId = courseId;
      this.getCourseSessions();
    } else {
      this.courseSessionsCourseId = null;
    }
  }

  toggleCourseAttendances(courseId: number): void {
    if (courseId !== this.courseAttendancesCourseId) {
      this.courseAttendancesCourseId = courseId;
      this.getCourseAttendances();
    } else {
      this.courseAttendancesCourseId = null;
    }
  }

  toggleCourseGrades(courseId: number): void {
    if (courseId !== this.courseGradesCourseId) {
      this.courseGradesCourseId = courseId;
      this.getCourseGrades();
    } else {
      this.courseGradesCourseId = null;
    }
  }

  submitDeleteCourse(courseId: number): void {
    if (confirm('Conformation. Are you sure you want to delete it?')) {
      this.courseService.deleteCourse(courseId)
        .subscribe({
          next: (response) => {
            this.notificationService.showSuccess(response);

            const deleteArrayElementIndex = this.courses.findIndex((course) => course.id == courseId);

            this.courses.splice(deleteArrayElementIndex, 1);
          },
          error: (error) => {
            console.log(error);
            this.notificationService.showError(error.error);
          }
        });
    }
  }
}
