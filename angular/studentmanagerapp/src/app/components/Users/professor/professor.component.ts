import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons';
import { UserDataInterface } from 'src/app/interfaces/user-data-interface';

@Component({
  selector: 'app-professor',
  templateUrl: './professor.component.html',
  styleUrls: ['./professor.component.scss']
})
export class ProfessorComponent implements OnInit {
  faSignOutAlt = faSignOutAlt;
  currentUser: UserDataInterface = <UserDataInterface>{};
  showAttendance: boolean = false;
  showCourses: boolean = false;

  constructor(private authService: AuthService, private activatedRoute: ActivatedRoute) {
    let id: any = this.activatedRoute.snapshot.paramMap.get('id');

    // this.authService.getUserProfileById(id).subscribe((res) => {
    //   this.currentUser = res;
    //   this.currentUser.role = res.roles[0].name;
    // });
  }

  ngOnInit(): void {
  }

  toggleAttendance(): void {
    if (this.showCourses === true) this.showCourses = false;

    this.showAttendance = !this.showAttendance;
  }

  toggleCourses(): void {
    if (this.showAttendance === true) this.showAttendance = false;

    this.showCourses = !this.showCourses;
  }
}
