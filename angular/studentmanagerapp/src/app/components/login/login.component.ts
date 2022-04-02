import { Component, OnInit } from '@angular/core';
import { faUserCircle, faUser, faLock } from '@fortawesome/free-solid-svg-icons';
import { Subscription } from 'rxjs';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  faUserCircle = faUserCircle;
  faUser = faUser;
  faLock = faLock;
  email: string;
  password: string;
  rememberMe: boolean = false;

  subscription: Subscription;

  constructor(private userService: UserService) { }

  ngOnInit(): void { }

  onSubmit() {
    this.userService.logIn(this.email, this.password);
  }
}