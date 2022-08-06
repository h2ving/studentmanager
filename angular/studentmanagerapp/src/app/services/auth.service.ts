import { Injectable } from '@angular/core';
import * as moment from 'moment';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, map, Observable, throwError, Subject, Subscription } from 'rxjs';
import { User } from '../models/user.model';
import { NotificationService } from './notification.service';
import { UserDataInterface } from '../interfaces/user-data-interface';
import { ResetUserPasswordInterface } from '../interfaces/reset-user-password-interface';
import { ChartsInterface } from '../interfaces/charts-interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  jsonHeaders = new HttpHeaders().set('Content-Type', 'application/json');
  urlEncodedHeaders = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
  private apiUrl: string = 'http://localhost:8080/api';
  private subject = new Subject<void>();
  currentUser: User = <User>{};
  redirectURI: string;

  constructor(private http: HttpClient, public router: Router, public notificationService: NotificationService) { }

  logIn(userEmail: string, password: string, rememberMe: boolean): Subscription {
    const body: string = `email=${userEmail}&password=${password}`;
    const url: string = `${this.apiUrl}/login`;

    return this.http
      .post<any>(url, body, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      })
      .subscribe({
        next: (response) => {
          const { tokens, redirectURI } = response;

          this.setSession(tokens);
          this.redirectURI = redirectURI;

          // Save Email to Local Storage
          if (rememberMe) {
            localStorage.setItem('rememberMeEmail', userEmail);
            localStorage.setItem('rememberMe', JSON.stringify(true));
          } else {
            localStorage.removeItem('rememberMeEmail');
            localStorage.removeItem('rememberMe');
          }

          this.router.navigate([redirectURI]);
        },
        error: () => {
          this.notificationService.showError('Invalid Credentials');
        }
      });
  }

  getUsersCharts(): Observable<ChartsInterface> {
    const url: string = `${this.apiUrl}/users/data/charts`;

    return this.http.get<ChartsInterface>(url, { headers: this.jsonHeaders });
  }

  getPaginatedUsers(pageNumber: number, pageSize: number): Observable<User> {
    const url: string = `${this.apiUrl}/users/paginated`;

    return this.http.get<User>(url, {
      headers: this.jsonHeaders, params: {
        pageNumber,
        pageSize,
      }
    }
    );
  }

  getUserDatatById(userId: number): Observable<User | Error> {
    const url: string = `${this.apiUrl}/user/data/${userId}`;

    return this.http.get(url, { headers: this.jsonHeaders })
      .pipe(
        map((response: any) => {
          this.currentUser = response;
          this.currentUser.user.role = response.user.roles.name;

          return this.currentUser || {};
        }),

        catchError(this.handleError)
      );
  }

  getUser(userId: number): Observable<UserDataInterface> {
    const url: string = `${this.apiUrl}/user/${userId}`;

    return this.http.get<UserDataInterface>(url, { headers: this.jsonHeaders });
  }

  saveUser(body: Object): Observable<string> {
    const url: string = `${this.apiUrl}/user`;

    return this.http.post(url, body, { responseType: 'text' });
  }

  editUser(user: UserDataInterface): Observable<User> {
    const url: string = `${this.apiUrl}/user`;

    return this.http.patch<any>(url, user, { headers: this.jsonHeaders });
  }

  resetUserPassword(userId: number, body: ResetUserPasswordInterface): Observable<any> {
    const url: string = `${this.apiUrl}/user/${userId}/reset_password`;

    return this.http.put(url, body, { responseType: 'text' });
  }

  logOut(): void {
    let removeAccessToken = sessionStorage.removeItem('access_token');
    let removeRefreshToken = sessionStorage.removeItem('refresh_token');

    sessionStorage.removeItem('expiry_time');

    if (removeAccessToken == null && removeRefreshToken == null) {
      this.router.navigate(['login']);
    }
  }

  setSession(authResult: any) {
    sessionStorage.setItem('access_token', authResult.access_token);
    sessionStorage.setItem('refresh_token', authResult.refresh_token);

    const currentTime: Date = new Date();
    const expiryTime: Date = moment(currentTime).add(10, 'm').toDate();
    sessionStorage.setItem('expiry_time', expiryTime.toString());
  }

  sendUpdateUserDataEvent(): void {
    this.subject.next();
  }

  getUpdateUserDataEvent(): Observable<any> {
    return this.subject.asObservable();
  }

  get isLoggedIn(): boolean {
    let accessToken: string | null = sessionStorage.getItem('access_token');
    const loggedIn: boolean = accessToken !== null ? true : false;

    return loggedIn;
  }

  get getTokens(): { accessToken: string, refreshToken: string } {
    const tokens = {
      accessToken: sessionStorage.getItem('access_token')!,
      refreshToken: sessionStorage.getItem('refresh_token')!,
    };

    return tokens;
  }

  get getCurrentUser(): User {
    return this.currentUser;
  }

  handleError(error: HttpErrorResponse): Observable<Error> {
    let msg = '';

    if (error.error instanceof ErrorEvent) {
      msg = error.error.message
    } else {
      this.notificationService.showError(`${String(error.status)}: ${error.message}`);
      msg = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }

    this.notificationService.showError(`handleError: ${error}`);

    return throwError(() => new Error(msg));
  }
}
