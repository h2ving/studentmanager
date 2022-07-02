import { Component, Input, OnInit } from '@angular/core';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons';
import { UserDataInterface } from 'src/app/interfaces/user-data-interface';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  @Input() currentUser: UserDataInterface;
  defaultImageUrl: string = '../../../../assets/person-logo.jpg';
  maleImageUrl: string = '../../../../assets/avatar_male.jpg';
  femaleImageUrl: string = '../../../../assets/avatar_female.jpg';
  faSignOutAlt = faSignOutAlt;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  clickLogOut(): void {
    this.authService.logOut();
  }
}
