import { Component, OnInit } from '@angular/core';
import { faUserCircle, faUser, faLock } from '@fortawesome/free-solid-svg-icons';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  rememberMe: any = localStorage.getItem('rememberMe');
  email: string = localStorage.getItem('rememberMeEmail') || '{}';
  password: string;
  faUserCircle = faUserCircle;
  faUser = faUser;
  faLock = faLock;

  constructor(private authService: AuthService) { }

  ngOnInit(): void { }

  onSubmit(): void {
    this.authService.logIn(this.email, this.password, this.rememberMe);
  }
}