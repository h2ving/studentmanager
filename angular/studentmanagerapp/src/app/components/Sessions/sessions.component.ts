import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup, UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { SessionService } from 'src/app/services/session.service';
import { User } from 'src/app/models/user.model';
import { Session } from 'src/app/models/session.module';
import { faLightbulb, faEdit, faTrash, faCheck } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-sessions',
  templateUrl: './sessions.component.html',
  styleUrls: ['./sessions.component.scss'],
})
export class SessionsComponent implements OnInit {
  faLightbulb = faLightbulb;
  faEdit = faEdit;
  faTrash = faTrash;
  faCheck = faCheck;

  currentUser: User = this.authService.getCurrentUser;
  currentUserId: number;
  currentUserLoaded: boolean = true;

  selectedCourseId: string;
  courseSessions: Array<Session>;
  editSessionId: number | null;

  editSessionForm: UntypedFormGroup;
  addSessionForm: UntypedFormGroup;

  constructor(private authService: AuthService, private sessionService: SessionService, private formBuilder: UntypedFormBuilder, private activatedRoute: ActivatedRoute, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => this.currentUserId = params['id']);

    if (!this.currentUser.user) {
      this.getUserData();
    }

    this.createEditSessionForm();
    this.createAddSessionForm();
  }

  getUserData(): void {
    this.currentUserLoaded = false;

    this.authService.getUserDatatById(this.currentUserId).subscribe({
      next: () => {
        this.currentUser = this.authService.currentUser;
        this.currentUserLoaded = true;
      },
      error: () => {
        this.notificationService.showError('Error getting User data, please refresh the page');
      }
    });
  }

  createEditSessionForm() {
    this.editSessionForm = this.formBuilder.group({
      description: '',
      startDateTime: '',
      academicHours: '',
    });
  }

  createAddSessionForm() {
    this.addSessionForm = this.formBuilder.group({
      description: '',
      startDateTime: '',
      academicHours: '',
    });
  }

  getCourseSessions(event: Event): void {
    const { value } = event.target as HTMLInputElement;

    this.selectedCourseId = value;

    if (value) {
      this.sessionService.getCourseSessions(+value)
        .subscribe({
          next: (response) => {
            this.courseSessions = response;
          },
          error: () => {
            this.notificationService.showError('Error Getting Sessions. Please refresh the page and try again');
          }
        })

      this.editSessionForm.reset({ description: '', startDateTime: '', academicHours: '' });
      this.editSessionId = null;
    }
  }

  toggleEditSession(sessionId: number): void {
    if (this.editSessionId === sessionId) {
      this.editSessionId = null;
    } else {
      this.editSessionId = sessionId;
    }

    this.editSessionForm.reset({ description: '', startDateTime: '', academicHours: '' });
  }

  submitEditSession(): void {
    let editedSessionIndex = this.courseSessions.findIndex((session) => session.id === this.editSessionId);

    this.sessionService.editSession(this.editSessionId!, this.editSessionForm.value)
      .subscribe({
        next: (response: Session) => {
          this.notificationService.showSuccess('Session Edited');

          this.courseSessions[editedSessionIndex] = response;

          this.sessionService.sendUpdateSessionsDataEvent();

          this.editSessionId = null;
          this.editSessionForm.reset({ description: '', startDateTime: '', academicHours: '' });
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        }
      });
  }

  submitAddSession(): void {
    this.addSessionForm.value.courseId = this.selectedCourseId;

    this.sessionService.saveSession(this.addSessionForm.value)
      .subscribe({
        next: (response) => {
          this.courseSessions.push(response);
          this.notificationService.showSuccess("New Session created");
          this.sessionService.sendUpdateSessionsDataEvent();

          this.addSessionForm.reset({ description: '', startDateTime: '', academicHours: '' });
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        }
      });
  }

  deleteSession(sessionId: number): void {
    // TODO Modal -> https://zoaibkhan.com/blog/create-reusable-confirmation-dialogs-with-angular-material/

    if (confirm("Confirmation. Are you sure you want to delete it?")) {
      this.sessionService.deleteSession(sessionId)
        .subscribe({
          next: (response) => {
            this.notificationService.showSuccess(response);

            const deleteArrayElementIndex = this.courseSessions.findIndex((session) => session.id === sessionId);

            this.courseSessions.splice(deleteArrayElementIndex, 1);

            this.sessionService.sendUpdateSessionsDataEvent();
          },
          error: () => {
            this.notificationService.showError("Error Deleting Session. Please refresh the page or try again later");
          }
        });
    }
  }
}
