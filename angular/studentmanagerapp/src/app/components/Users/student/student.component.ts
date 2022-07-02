import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons';
import { User } from 'src/app/models/user.model';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss']
})
export class StudentComponent implements OnInit {
  faSignOutAlt = faSignOutAlt;
  currentUser: User = <User>{};
  currentUserLoaded: boolean = false;
  showAttendance: boolean = false;
  showCourses: boolean = false;
  showGrades: boolean = false;

  constructor(private authService: AuthService, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    let id: any = this.activatedRoute.snapshot.paramMap.get('id');

    this.authService.getUserProfileById(id).subscribe((res) => {
      this.currentUser = res;
      this.currentUser.role = res.roles[0].name;

      this.currentUserLoaded = true;
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
