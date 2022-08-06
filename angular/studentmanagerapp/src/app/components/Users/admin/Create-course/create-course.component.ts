import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup, UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';
import { CourseService } from 'src/app/services/course.service';
import { NotificationService } from 'src/app/services/notification.service';

import { faLightbulb } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-create-course',
  templateUrl: './create-course.component.html',
  styleUrls: ['./create-course.component.scss']
})
export class CreateCourseComponent implements OnInit {
  userId: number;
  currentUser: User = this.authService.getCurrentUser;
  currentUserLoaded: boolean = true;

  faLightbulb = faLightbulb;

  createCourseForm: UntypedFormGroup;

  constructor(private authService: AuthService, private courseService: CourseService, private activatedRoute: ActivatedRoute, private formBuilder: UntypedFormBuilder, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params: any) => this.userId = params.id);

    if (!this.currentUser.user) {
      this.getUserData();
    }

    this.createCreateCourseForm();
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

  createCreateCourseForm() {
    this.createCourseForm = this.formBuilder.group({
      name: '',
      description: '',
      startDate: null,
      endDate: null,
      academicHours: null,
      remote: false,
    });
  }

  submitCreateCourse(): void {
    this.courseService.addCourse(this.createCourseForm.value)
      .subscribe({
        next: (response) => {
          console.log(response)
          this.notificationService.showSuccess(response);
        },
        error: (error) => {
          this.notificationService.showError(error.error);
          console.log(error)
        }
      })
  }
}
