import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup, UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';
import { CourseService } from 'src/app/services/course.service';
import { NotificationService } from 'src/app/services/notification.service';

import { faLightbulb } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.scss']
})
export class CreateUserComponent implements OnInit {
  userId: number;
  currentUser: User = this.authService.getCurrentUser;
  currentUserLoaded: boolean = true;

  faLightbulb = faLightbulb;

  createUserForm: UntypedFormGroup;

  constructor(private authService: AuthService, private courseService: CourseService, private activatedRoute: ActivatedRoute, private formBuilder: UntypedFormBuilder, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params: any) => this.userId = params.id);

    if (!this.currentUser.user) {
      this.getUserData();
    }

    this.createCreateUserForm();
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

  createCreateUserForm() {
    this.createUserForm = this.formBuilder.group({
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      role: '',
      gender: '',
      dob: null,
      mobile: null,
    });
  }

  submitCreateUser(): void {
    this.authService.saveUser(this.createUserForm.value)
      .subscribe({
        next: (response) => {
          this.notificationService.showSuccess(response);
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        }
      })
  }
}
