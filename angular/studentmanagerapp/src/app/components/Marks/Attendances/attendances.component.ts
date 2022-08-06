import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup, UntypedFormBuilder, UntypedFormArray } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { AttendanceService } from 'src/app/services/attendance.service';
import { SessionService } from 'src/app/services/session.service';
import { NotificationService } from 'src/app/services/notification.service';
import { User } from 'src/app/models/user.model';
import { Session } from 'src/app/models/session.module';
import { faLightbulb } from '@fortawesome/free-solid-svg-icons';
import { UserDataInterface } from 'src/app/interfaces/user-data-interface';

@Component({
  selector: 'app-attendances',
  templateUrl: './attendances.component.html',
  styleUrls: ['../gradesAttendances.component.scss']
})
export class AttendancesComponent implements OnInit {
  faLightbulb = faLightbulb;

  currentUser: User = this.authService.getCurrentUser;
  currentUserId: number;
  currentUserLoaded: boolean = true;

  courseSessions: Array<Session> | null;
  sessionUsers: Array<UserDataInterface> | null;
  sessionId: number;

  addAttendancesForm: UntypedFormGroup;

  constructor(private authService: AuthService, private attendanceService: AttendanceService, private sessionsService: SessionService, private formBuilder: UntypedFormBuilder, private activatedRoute: ActivatedRoute, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => this.currentUserId = params['id']);

    if (!this.currentUser.user) {
      this.getUserData();
    }

    this.createAddAttendancesForm();
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

  createAddAttendancesForm() {
    this.addAttendancesForm = this.formBuilder.group({
      sessionId: '',
      attendances: this.formBuilder.array([]),
    });
  }

  get attendances(): UntypedFormArray {
    return this.addAttendancesForm.get('attendances') as UntypedFormArray;
  }

  newAttendace(userId: number, attendance: boolean): UntypedFormGroup {
    return this.formBuilder.group({
      userId,
      attendance,
    });
  }

  addAttendance(userId: number, attendance: boolean) {
    this.attendances.push(this.newAttendace(userId, attendance));
  }

  removeAttendance(userId: number) {
    const index: number = this.attendances.value.findIndex((e: any) => e.userId === userId);

    if (index !== -1) {
      this.attendances.removeAt(index);
    }
  }


  getCourseSessions(event: Event): void {
    const { value } = event.target as HTMLInputElement;

    this.courseSessions = null;
    this.sessionUsers = null;

    if (value) {
      this.sessionsService.getCourseSessions(+value)
        .subscribe({
          next: (response) => {
            this.courseSessions = response;
          },
          error: (error) => {
            this.notificationService.showError(error.error)
          }
        });
    }
  }

  getSessionUsers(event: Event): void {
    const { value } = event.target as HTMLInputElement;

    this.sessionUsers = null;

    if (value !== '') {
      this.courseSessions!.find((session) => {
        if (session.id === +value) {
          this.sessionUsers = session.user!;
          this.sessionId = session.id;
        }
      });
    }
  }

  onInputChange(event: Event, userId: number) {
    const { value } = event.target as HTMLInputElement;
    const booleanvalue = value === 'present' ? true : false;
    const userAttendanceIndex = this.attendances.value.findIndex((attendance: any) => attendance.userId === userId);

    if (value === '') {
      this.removeAttendance(userId);
    } else {
      if (userAttendanceIndex !== -1)
        this.attendances.at(userAttendanceIndex).get('attendance')?.patchValue(booleanvalue);

      else this.addAttendance(userId, booleanvalue);
    }
  }

  submitAddAttendances() {
    this.addAttendancesForm.value.sessionId = this.sessionId;

    this.attendanceService.addAttendances(this.addAttendancesForm.value)
      .subscribe({
        next: (response) => {
          this.notificationService.showSuccess(response);

          this.sessionUsers = null;
        },
        error: (error) => {
          console.log(error)

          this.notificationService.showError(error.error);
        }
      });
  }
}
