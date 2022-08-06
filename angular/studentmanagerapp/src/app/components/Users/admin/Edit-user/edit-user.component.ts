import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup, UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';

import { User } from 'src/app/models/user.model';
import { faLightbulb } from '@fortawesome/free-solid-svg-icons';
import { UserDataInterface } from 'src/app/interfaces/user-data-interface';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit {
  editUserId: number;
  userId: number;

  user: UserDataInterface;
  userLoaded: boolean = true;

  currentUser: User = this.authService.getCurrentUser;
  currentUserLoaded: boolean = true;

  editUserForm: UntypedFormGroup;

  faLightbulb = faLightbulb;

  constructor(private authService: AuthService, private activatedRoute: ActivatedRoute, private formBuilder: UntypedFormBuilder, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params: any) => {
      this.editUserId = params.userId;
      this.userId = params.id;
    });

    if (!this.currentUser.user) {
      this.getUserData();
    }

    if (!this.user) {
      this.getUser();
    }
  }

  getUserData(): void {
    this.currentUserLoaded = false;

    this.authService.getUserDatatById(this.userId).subscribe({
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

  getUser(): void {
    this.userLoaded = false;

    this.authService.getUser(this.editUserId)
      .subscribe({
        next: (response: any) => {
          this.user = response;
          this.user.role = response.roles[0].name;

          this.createEditUserForm();

          console.log(response);
        },
        error: (error) => {
          console.log(error)
          this.notificationService.showError(error.error);
        }
      });
  }

  createEditUserForm() {
    const {
      firstName, lastName, email, role, gender, dob, mobile
    } = this.user;

    this.editUserForm = this.formBuilder.group({
      firstName,
      lastName,
      email,
      role,
      gender,
      dob,
      mobile,
    });
  }

  submitEditUser(userId: number): void {
    this.editUserForm.value.id = userId;

    console.log(this.editUserForm.value)

    this.authService.editUser(this.editUserForm.value)
      .subscribe({
        next: (response) => {
          console.log(response);
          this.notificationService.showSuccess('User edited successfully');
        },
        error: (error) => {
          console.log(error)
          this.notificationService.showError(error.error);
        }
      })

    // AuthService
  }
}
