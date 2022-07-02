import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NotificationService } from './notification.service';
import { Session } from '../models/session.module';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private apiUrl: string = 'http://localhost:8080/api';
  headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient) { }

  // // Get all Sessions from 1 User by email
  // getUserSessions(email: string): Observable<Session[]> {
  //   const url = `${this.apiUrl}/attendances/get/${email}`;

  //   return this.http.get<Session[]>(url, { headers: this.headers })
  // }
}
