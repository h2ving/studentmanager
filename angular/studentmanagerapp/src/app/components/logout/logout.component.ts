import { Component, OnInit, Input } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {
  faSignOutAlt = faSignOutAlt;
  @Input() currentUser: User;
  defaultImageUrl: string = '../../../assets/person-logo.jpg';
  maleImageUrl: string = '../../../assets/avatar_male.jpg';
  femaleImageUrl: string = '../../../assets/avatar_female.jpg';

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
  }

  clickLogOut() {
    this.authService.logOut();
  }

}
