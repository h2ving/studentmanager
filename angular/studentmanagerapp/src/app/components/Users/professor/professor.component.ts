import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { User } from 'src/app/models/user.model';
import { Subscription } from 'rxjs';
import {
  faGraduationCap, faBook, faPlusCircle, faBullhorn, faEdit
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-professor',
  templateUrl: './professor.component.html',
  styleUrls: ['./professor.component.scss']
})
export class ProfessorComponent implements OnInit {
  updateUserDataEventSubscription: Subscription;
  currentUser: User = <User>{};
  currentUserLoaded: boolean = false;

  onGoingCourses: number;

  showCourses: boolean = false;

  faBook = faBook;
  faGraduationCap = faGraduationCap;
  faPlusCircle = faPlusCircle;
  faBullhorn = faBullhorn;
  faEdit = faEdit;

  // Professor -> lchambers@sdaacademy.uni -> ID 56
  // ngx-charts -> https://swimlane.github.io/ngx-charts/#/ngx-charts/calendar
  // ng-bootstrap -> Datepicker -> https://ng-bootstrap.github.io/#/components/datepicker/overview
  // http -> https://stackoverflow.com/questions/31089221/what-is-the-difference-between-put-post-and-patch
  // JPA -> https://www.javaguides.net/2021/12/spring-data-save-method.html

  constructor(private authService: AuthService, private activatedRoute: ActivatedRoute, public notificationService: NotificationService) {
    this.updateUserDataEventSubscription = this.authService.getUpdateUserDataEvent()
      .subscribe(() => {
        this.getUserData();
      })
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

  toggleCourses(): void {
    this.showCourses = !this.showCourses;
  }
}
