import { Component, OnInit } from '@angular/core';
import { faUserCircle, faUser, faLock } from '@fortawesome/free-solid-svg-icons';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  faUserCircle = faUserCircle;
  faUser = faUser;
  faLock = faLock;
  email: any = localStorage.getItem('rememberMeEmail');
  password: string;
  rememberMe: any = localStorage.getItem('rememberMe');

  constructor(private authService: AuthService) { }

  ngOnInit(): void { }

  onSubmit() {
    this.authService.logIn(this.email, this.password, this.rememberMe);
  }
}