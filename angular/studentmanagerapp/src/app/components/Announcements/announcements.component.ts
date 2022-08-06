import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup, UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { AnnouncementService } from 'src/app/services/announcement.service';
import { NotificationService } from 'src/app/services/notification.service';
import { User } from 'src/app/models/user.model';
import { Announcement } from 'src/app/models/announcement.model';
import { faLightbulb, faEdit, faTrash, faCheck } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-announcements',
  templateUrl: './announcements.component.html',
  styleUrls: ['./announcements.component.scss']
})
export class AnnouncementsComponent implements OnInit {
  faLightbulb = faLightbulb;
  faEdit = faEdit;
  faTrash = faTrash;
  faCheck = faCheck;

  currentUser: User = this.authService.getCurrentUser;
  currentUserId: number;
  currentUserLoaded: boolean = true;

  selectedCourseId: string;
  courseAnnouncements: Array<Announcement>;
  editAnnouncementId: number | null;

  editAnnouncementForm: UntypedFormGroup;
  addAnnouncementForm: UntypedFormGroup;

  constructor(private authService: AuthService, private announcementService: AnnouncementService, private formBuilder: UntypedFormBuilder, private activatedRoute: ActivatedRoute, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => this.currentUserId = params['id']);

    if (!this.currentUser.user) {
      this.getUserData();
    }

    this.createEditAnnouncementForm();
    this.createAddAnnouncementForm();
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

  createEditAnnouncementForm() {
    this.editAnnouncementForm = this.formBuilder.group({ announcement: '' });
  }

  createAddAnnouncementForm() {
    this.addAnnouncementForm = this.formBuilder.group({
      announcement: '',
      courseId: this.selectedCourseId,
    });
  }

  getCourseAnnouncements(event: Event): void {
    const { value } = event.target as HTMLInputElement;

    this.selectedCourseId = value;

    if (value) {
      this.announcementService.getCourseAnnouncements(+value)
        .subscribe({
          next: (response) => {
            this.courseAnnouncements = response;
          },
          error: () => {
            this.notificationService.showError('Error Getting Announcements. Please refresh the page and try again');
          }
        })
      this.editAnnouncementForm.reset({ announcement: '' });
      this.editAnnouncementId = null;
    }
  }

  toggleEditAnnouncement(announcementId: number): void {
    if (this.editAnnouncementId === announcementId) {
      this.editAnnouncementId = null;
    } else {
      this.editAnnouncementId = announcementId;
    }

    this.editAnnouncementForm.reset({ announcement: '' });
  }

  submitEditAnnouncement(): void {
    let editedAnnouncementIndex = this.courseAnnouncements.findIndex((announcement) => announcement.id === this.editAnnouncementId!);

    this.announcementService.editAnnouncement(this.editAnnouncementId!, this.editAnnouncementForm.value)
      .subscribe({
        next: (response: Announcement) => {
          this.notificationService.showSuccess('Announcment edited');

          this.courseAnnouncements[editedAnnouncementIndex] = response;

          this.editAnnouncementId = null;
          this.editAnnouncementForm.reset({ announcement: '' });
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        }
      });
  }

  submitAddAnnouncement(): void {
    this.addAnnouncementForm.value.courseId = this.selectedCourseId;

    this.announcementService.saveAnnouncement(this.addAnnouncementForm.value)
      .subscribe({
        next: (response) => {
          this.courseAnnouncements.push(response);
          this.notificationService.showSuccess("New Announcement created");

          this.addAnnouncementForm.reset({ announcement: '', courseId: this.selectedCourseId });
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        }
      });
  }

  deleteAnnouncement(announcementId: number): void {
    // TODO Modal -> https://zoaibkhan.com/blog/create-reusable-confirmation-dialogs-with-angular-material/

    if (confirm("Confirmation. Are you sure you want to delete it?")) {
      this.announcementService.deleteAnnouncement(announcementId)
        .subscribe({
          next: (response) => {
            this.notificationService.showSuccess(response);

            const deleteArrayElementIndex = this.courseAnnouncements.findIndex((announcement) => announcement.id === announcementId);

            this.courseAnnouncements.splice(deleteArrayElementIndex, 1);
          },
          error: () => {
            this.notificationService.showError("Error Deleting Announcement. Please refresh the page or try again later");
          }
        });
    }
  }
}
