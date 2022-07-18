import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { User } from 'src/app/models/user.model';
import {
  faGraduationCap, faChartLine, faBook, faPercentage, faPlusCircle,
} from '@fortawesome/free-solid-svg-icons';


@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss']
})
export class StudentComponent implements OnInit {
  updateUserDataEventSubscription: Subscription;
  currentUser: User = <User>{};
  currentUserLoaded: boolean = false;
  onGoingCourses: number;
  showAttendance: boolean = false;
  showCourses: boolean = false;
  showGrades: boolean = false;
  faBook = faBook;
  faChartLine = faChartLine;
  faPercentage = faPercentage;
  faGraduationCap = faGraduationCap;
  faPlusCircle = faPlusCircle;

  // Student -> edeitz@sdaacademy.uni -> ID 52

  constructor(private authService: AuthService, private activatedRoute: ActivatedRoute, public notificationService: NotificationService) {
    this.updateUserDataEventSubscription = this.authService.getUpdateUserDataEvent()
      .subscribe(() => {
        this.getUserData();
      });
  }

  ngOnInit(): void {
    this.getUserData();
  }

  getUserData(): void {
    let userId: any = this.activatedRoute.snapshot.paramMap.get('id');
    this.currentUserLoaded = false;

    this.authService.getUserDatatById(userId).subscribe({
      next: () => {
        this.currentUser = this.authService.currentUser;
        this.currentUserLoaded = true;

        this.onGoingCourses = this.currentUser.user
          .coursesAssigned.filter((course) => course.endDate < new Date().toLocaleDateString()).length;
      },
      error: () => {
        this.notificationService.showError('Error getting User data, please refresh the page');
      }
    });
  }

  toggleAttendance(): void {
    if (this.showCourses === true) this.showCourses = false;
    if (this.showGrades === true) this.showGrades = false;

    this.showAttendance = !this.showAttendance;
  }

  toggleCourses(): void {
    if (this.showAttendance === true) this.showAttendance = false;
    if (this.showGrades === true) this.showGrades = false;

    this.showCourses = !this.showCourses;
  }

  toggleGrades(): void {
    if (this.showAttendance === true) this.showAttendance = false;
    if (this.showCourses === true) this.showCourses = false;

    this.showGrades = !this.showGrades;
  }
}
