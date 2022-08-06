import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup, UntypedFormBuilder, UntypedFormArray } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { GradeService } from 'src/app/services/grade.service';
import { SessionService } from 'src/app/services/session.service';
import { NotificationService } from 'src/app/services/notification.service';
import { User } from 'src/app/models/user.model';
import { Session } from 'src/app/models/session.module';

import { faLightbulb } from '@fortawesome/free-solid-svg-icons';
import { UserDataInterface } from 'src/app/interfaces/user-data-interface';

@Component({
  selector: 'app-grades',
  templateUrl: './grades.component.html',
  styleUrls: ['../gradesAttendances.component.scss']
})
export class GradesComponent implements OnInit {
  faLightbulb = faLightbulb;

  currentUser: User = this.authService.getCurrentUser;
  currentUserId: number;
  currentUserLoaded: boolean = true;

  courseSessions: Array<Session> | null;
  sessionUsers: Array<UserDataInterface> | null;
  sessionId: number;

  addGradesForm: UntypedFormGroup;

  constructor(private authService: AuthService, private gradeService: GradeService, private sessionsService: SessionService, private formBuilder: UntypedFormBuilder, private activatedRoute: ActivatedRoute, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => this.currentUserId = params['id']);

    if (!this.currentUser.user) {
      this.getUserData();
    }

    this.createAddGradesForm();
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

  createAddGradesForm() {
    this.addGradesForm = this.formBuilder.group({
      sessionId: '',
      grades: this.formBuilder.array([]),
    });
  }

  get grades(): UntypedFormArray {
    return this.addGradesForm.get('grades') as UntypedFormArray;
  }

  newGrade(userId: number, grade: number): UntypedFormGroup {
    return this.formBuilder.group({
      userId,
      grade,
    });
  }

  addGrade(userId: number, grade: number) {
    this.grades.push(this.newGrade(userId, grade));
  }

  removeGrade(userId: number) {
    const index: number = this.grades.value.findIndex((e: any) => e.userId === userId);

    if (index !== -1) {
      this.grades.removeAt(index);
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
    const userGradeIndex = this.grades.value.findIndex((grade: any) => grade.userId === userId);

    if (value === '') {
      this.removeGrade(userId);
    } else {
      if (userGradeIndex !== -1)
        this.grades.at(userGradeIndex).get('grade')?.patchValue(+value);

      else this.addGrade(userId, +value);
    }
  }

  submitAddGrades() {
    this.addGradesForm.value.sessionId = this.sessionId;

    this.gradeService.addGrades(this.addGradesForm.value)
      .subscribe({
        next: (response) => {
          this.notificationService.showSuccess(response);

          this.sessionUsers = null;
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        }
      });
  }
}
