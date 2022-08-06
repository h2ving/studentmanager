import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { AttendanceService } from 'src/app/services/attendance.service';
import { GradeService } from 'src/app/services/grade.service';
import { NotificationService } from 'src/app/services/notification.service';
import { User } from 'src/app/models/user.model';

import { faLightbulb, faEdit, faTrash, faUsers, faBook, faGraduationCap } from '@fortawesome/free-solid-svg-icons';
import { UserDataInterface } from 'src/app/interfaces/user-data-interface';
import { Attendance } from 'src/app/models/attendance.module';
import { Grade } from 'src/app/models/grade.model';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  faLightbulb = faLightbulb;
  faEdit = faEdit;
  faTrash = faTrash;
  faUsers = faUsers;
  faBook = faBook;
  faGraduationCap = faGraduationCap;

  currentUser: User = this.authService.getCurrentUser;
  currentUserId: number;
  currentUserLoaded: boolean = true;

  pageNumber: number = 1;
  pageSize: number = 10;
  firstPage: boolean;
  lastPage: boolean;
  users: Array<any>;
  totalUsers: number | null;

  userCoursesAssignedUserId: number | null = null;

  userAttendancesUserId: number | null = null;
  userAttendances: Array<Attendance> | null;
  userAttendancesCount: number | null;
  userAttendancesPageNumber: number = 1;
  userAttendancesPageSize: number = 10;
  userAttendancesFirstPage: boolean;
  userAttendancesLastPage: boolean;

  userGradesUserId: number | null = null;
  userGrades: Array<Grade> | null;
  userGradesCount: number | null;
  userGradesPageNumber: number = 1;
  userGradesPageSize: number = 10;
  userGradesFirstPage: boolean;
  userGradesLastPage: boolean;

  constructor(private authService: AuthService, private attendanceService: AttendanceService, private gradeService: GradeService, private activatedRoute: ActivatedRoute, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => this.currentUserId = params['id']);

    if (!this.currentUser.user) {
      this.getUserData();
    }

    this.getUsers();
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

  getUsers() {
    this.authService.getPaginatedUsers(this.pageNumber - 1, this.pageSize)
      .subscribe({
        next: (response: any) => {
          this.users = response.content;
          this.firstPage = response.first;
          this.lastPage = response.last;
          this.totalUsers = response.totalElements;
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        }
      })
  }

  getUserAttendances() {
    this.attendanceService.getUserAttendancesPaginated(this.userAttendancesUserId!, this.userAttendancesPageNumber - 1, this.userAttendancesPageSize)
      .subscribe({
        next: (response: any) => {
          this.userAttendances = response.content;
          this.userAttendancesFirstPage = response.first;
          this.userAttendancesLastPage = response.last;
          this.userAttendancesCount = response.totalElements;
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        }
      })
  }

  getUserGrades() {
    this.gradeService.getUserGradesPaginated(this.userGradesUserId!, this.userGradesPageNumber - 1, this.userGradesPageSize)
      .subscribe({
        next: (response: any) => {
          this.userGrades = response.content;
          this.userGradesFirstPage = response.first;
          this.userGradesLastPage = response.last;
          this.userGradesCount = response.totalElements;

          console.log(response);
        }, error: (error) => {
          console.log(error);
          this.notificationService.showError(error.error);
        }
      });
  }


  switchPage(pageNumber: number): void {
    this.pageNumber = pageNumber;

    this.getUsers();
  }

  switchUserAttendancesPage(pageNumber: number): void {
    this.userAttendancesPageNumber = pageNumber;

    this.getUserAttendances();
  }

  switchUserGradesPage(pageNumber: number): void {
    this.userGradesPageNumber = pageNumber;

    this.getUserGrades();
  }


  toggleCoursesAssigned(userId: number): void {
    if (userId !== this.userCoursesAssignedUserId) {
      this.userCoursesAssignedUserId = userId;
    } else {
      this.userCoursesAssignedUserId = null;
    }
  }

  toggleUserAttendances(userId: number): void {
    if (userId !== this.userAttendancesUserId) {
      this.userAttendancesUserId = userId;
      this.getUserAttendances();
    } else {
      this.userAttendancesUserId = null;
    }
  }

  toggleUserGrades(userId: number): void {
    if (userId !== this.userGradesUserId) {
      this.userGradesUserId = userId;
      this.getUserGrades();
    } else {
      this.userGradesUserId = null;
    }
  }


  submitDeleteUser(userId: number): void {

  }
}
