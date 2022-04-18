import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss']
})
export class StudentComponent implements OnInit {
  faSignOutAlt = faSignOutAlt;
  currentUser: User = <User>{};
  defaultImageUrl: string = '../../../assets/person-logo.jpg';
  maleImageUrl: string = '../../../assets/avatar_male.jpg';
  femaleImageUrl: string = '../../../assets/avatar_female.jpg';

  constructor(private authService: AuthService, private activatedRoute: ActivatedRoute) {
    let id: any = this.activatedRoute.snapshot.paramMap.get('id');

    this.authService.getUserProfileById(id).subscribe((res) => {
      this.currentUser = res;
      this.currentUser.role = res.roles[0].name;
    });
  }

  ngOnInit(): void {
  }
}
