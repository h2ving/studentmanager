import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup, UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Course } from 'src/app/models/course.module';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';
import { CourseService } from 'src/app/services/course.service';
import { NotificationService } from 'src/app/services/notification.service';

import { faLightbulb } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-edit-course',
  templateUrl: './edit-course.component.html',
  styleUrls: ['./edit-course.component.scss']
})
export class EditCourseComponent implements OnInit {
  courseId: number;
  userId: number;

  course: Course;
  courseLoaded: boolean = true;

  currentUser: User = this.authService.getCurrentUser;
  currentUserLoaded: boolean = true;

  faLightbulb = faLightbulb;

  editCourseForm: UntypedFormGroup;

  constructor(private authService: AuthService, private courseService: CourseService, private activatedRoute: ActivatedRoute, private formBuilder: UntypedFormBuilder, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params: any) => {
      this.courseId = params.courseId;
      this.userId = params.id;
    });

    if (!this.currentUser.user) {
      this.getUserData();
    }

    if (!this.course) {
      this.getCourseData();
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

  getCourseData(): void {
    this.courseLoaded = false;

    this.courseService.getCourse(this.courseId).subscribe({
      next: (response) => {
        this.course = response;
        this.createEditCourseForm();
      },
      error: (error) => {
        console.log(error)
        this.notificationService.showError('Error Editing Course. Please refresh the page or try again later.');
      }
    });
  }

  createEditCourseForm() {
    const {
      name, description, startDate, endDate, academicHours, remote,
    } = this.course;

    this.editCourseForm = this.formBuilder.group({
      name,
      description,
      startDate,
      endDate,
      academicHours,
      remote,
    });
  }

  submitEditCourse(courseId: number): void {
    this.editCourseForm.value.id = courseId;

    this.courseService.editCourse(courseId, this.editCourseForm.value)
      .subscribe({
        next: (response: string) => {
          this.notificationService.showSuccess(response);
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        }
      })
  }
}
