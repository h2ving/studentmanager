import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { User } from '../model/user.model';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://db4free.net:3306/sdastudentapp';

  constructor(private http: HttpClient) { }

  // Log in
  logIn(email: string, password: string): Observable<User> {
    const body = { email, password };
    const url = `${this.apiUrl}/user/login`;

    return this.http.post<any>(url, body);
  }

  // Log out
  logOut(user: User): Observable<User> {
    const url = `${this.apiUrl}/user/logout/${user.id}`;

    return this.http.get<User>(url);
  }

  // Register a new User
  register(user: User): Observable<User> {
    const url = `${this.apiUrl}/user/registration`;

    return this.http.post<User>(url, user, httpOptions);
  }

  // Edit User
  editUser(user: User): Observable<User> {
    const url = `${this.apiUrl}/user/edit/${user.id}`;

    return this.http.patch<User>(url, user, httpOptions);
  }

  // Delete User
  deleteUser(user: User) {
    const url = `${this.apiUrl}/user/delete/${user.id}`;

    return this.http.delete<User>(url);
  }
}
