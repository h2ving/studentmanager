import { Injectable } from '@angular/core';
import * as moment from 'moment';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, map, Observable, throwError } from 'rxjs';
import { User } from '../models/user.model';
import { NotificationService } from './notification.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl: string = 'http://localhost:8080/api';
  headers = new HttpHeaders().set('Content-Type', 'application/json');
  currentUser: User = <User>{};

  constructor(private http: HttpClient, public router: Router, public notificationService: NotificationService) { }

  // Log in
  logIn(email: string, password: string, rememberMe: boolean) {
    const body = `email=${email}&password=${password}`;
    const url = `${this.apiUrl}/login`;

    return this.http
      .post<any>(url, body, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      })
      .subscribe({
        next: (res) => {
          this.setSession(res);

          this.getUserProfileByEmail(email).subscribe((res) => {
            this.currentUser = res;
            this.currentUser.role = res.roles[0].name;

            sessionStorage.setItem('RedirectURI', this.currentUser.role.toLowerCase() + '/' + res.id);
            sessionStorage.setItem('Role', this.currentUser.role);

            // Save email to Local Storage if rememberMe true
            if (rememberMe) {
              localStorage.setItem('rememberMeEmail', email);
              localStorage.setItem('rememberMe', JSON.stringify(true));
            } else {
              localStorage.removeItem('rememberMeEmail');
              localStorage.removeItem('rememberMe');
            }

            this.router.navigate([this.currentUser.role.toLowerCase() + '/' + res.id]);
          });
        },
        error: (err) => {
          this.notificationService.showError('Invalid Credentials', 'Error');
        }
      });
  }

  // Get User profile by Email
  getUserProfileByEmail(email: string): Observable<any> {
    const url = `${this.apiUrl}/user/${email}`;

    return this.http.get(url, { headers: this.headers }).pipe(
      map((res) => {
        return res || {};
      }),

      catchError(this.handleError)
    );
  }

  // Get User profile by ID
  getUserProfileById(id: number): Observable<any> {
    const url = `${this.apiUrl}/user/id/${id}`;

    return this.http.get(url, { headers: this.headers }).pipe(
      map((res: any) => {
        this.currentUser = res;
        this.currentUser.role = res.roles[0].name;

        return res || {};
      }),

      catchError(this.handleError)
    );
  }

  // Return the current logged in User
  getCurrentUser(): User {
    return this.currentUser;
  }

  // Return if User is logged in
  get isLoggedIn(): boolean {
    let authAccessToken = sessionStorage.getItem('access_token');
    return authAccessToken !== null ? true : false;
  }

  // Log User out
  logOut() {
    let removeAccessToken = sessionStorage.removeItem('access_token');
    let removeRefreshToken = sessionStorage.removeItem('refresh_token');
    sessionStorage.removeItem('RedirectURI');
    sessionStorage.removeItem('Role');

    //! Experimental
    sessionStorage.removeItem('expiry_time');

    if (removeAccessToken == null && removeRefreshToken == null) {
      this.router.navigate(['login']);
    }
  }

  // Get the JWT Token from Session Storage
  getAccessToken() {
    return sessionStorage.getItem('access_token');
  }
  getRefreshToken() {
    return sessionStorage.getItem('refresh_token');
  }

  // Set the JWT Token to Session Storage
  setSession(authResult: any) {
    sessionStorage.setItem('access_token', authResult.access_token);
    sessionStorage.setItem('refresh_token', authResult.refresh_token);

    //! Experimental
    const currentTime = new Date();
    const expiryTime = moment(currentTime).add(10, 'm').toDate();
    sessionStorage.setItem('expiry_time', expiryTime.toString());
  }

  // Handle and return Error messages
  handleError(error: HttpErrorResponse) {
    let msg = '';
    if (error.error instanceof ErrorEvent) {
      msg = error.error.message
    } else {
      msg = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }

    return throwError(() => new Error(msg));
  }
}
