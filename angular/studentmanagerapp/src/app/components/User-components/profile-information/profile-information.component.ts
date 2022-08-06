import { Component, OnInit, Input } from '@angular/core';
import { UntypedFormGroup, UntypedFormBuilder } from '@angular/forms';
import { UserDataInterface } from 'src/app/interfaces/user-data-interface';
import { faEdit, faArrowCircleLeft, faKey } from '@fortawesome/free-solid-svg-icons';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-profile-information',
  templateUrl: './profile-information.component.html',
  styleUrls: ['./profile-information.component.scss']
})
export class ProfileInformationComponent implements OnInit {
  faEdit = faEdit;
  faKey = faKey;
  faArrowCircleLeft = faArrowCircleLeft;
  userAction: string = 'View';
  @Input() currentUser: UserDataInterface;
  editUserForm: UntypedFormGroup;
  resetUserPasswordForm: UntypedFormGroup;

  constructor(private formBuilder: UntypedFormBuilder, private authService: AuthService, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.createEditUserForm();
    this.createResetUserPasswordForm();
  }

  createEditUserForm() {
    const { id, firstName, lastName, email, gender, mobile, dob } = this.currentUser;

    this.editUserForm = this.formBuilder.group({
      id,
      firstName,
      lastName,
      email,
      gender,
      mobile,
      dob,
    });
  }

  createResetUserPasswordForm() {
    const { id } = this.currentUser;

    this.resetUserPasswordForm = this.formBuilder.group({
      id,
      oldPassword: '',
      newPassword: '',
      repeatNewPassword: '',
    });
  }

  submitEditUser(): void {
    delete this.editUserForm.value.role;
    delete this.editUserForm.value.roles;

    this.authService.editUser(this.editUserForm.value).subscribe({
      next: (response: any) => {
        this.notificationService.showSuccess("User edited");

        this.authService.currentUser.user = response;
        this.authService.currentUser.user.role = response.roles.name;
      },
      error: (error) => {
        this.notificationService.showError(error.error);
      },
      complete: () => {
        this.userAction = 'View';
      }
    });
  }

  submitResetUserPassword(): void {
    this.authService.resetUserPassword(this.currentUser.id, this.resetUserPasswordForm.value).subscribe({
      next: () => {
        this.notificationService.showSuccess("Password changed");
      },
      error: (error) => {
        this.notificationService.showError(error.error);
      },
      complete: () => {
        this.userAction = 'View';
        this.resetUserPasswordForm.reset();
      }
    });
  }
}
