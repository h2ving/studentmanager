import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { faUserCircle, faUser, faLock } from '@fortawesome/free-solid-svg-icons';

import { Subscription } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  faUserCircle = faUserCircle;
  faUser = faUser;
  faLock = faLock;
  userName: string;
  password: string;
  rememberMe: boolean = false;

  subscription: Subscription;

  constructor() {
  }

  ngOnInit(): void {
  }

  onSubmit() {
  }
}