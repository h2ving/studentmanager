import { Component, Input, OnInit } from '@angular/core';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  faSignOutAlt = faSignOutAlt;
  @Input() currentUser: User;
  defaultImageUrl: string = '../../../../assets/person-logo.jpg';
  maleImageUrl: string = '../../../../assets/avatar_male.jpg';
  femaleImageUrl: string = '../../../../assets/avatar_female.jpg';

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  clickLogOut() {
    this.authService.logOut();
  }
}
