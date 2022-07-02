import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { Session } from '../models/session.module';
import { NotificationService } from './notification.service';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  headers = new HttpHeaders().set('Content-Type', 'application/json');
  private apiUrl: string = 'http://localhost:8080/api';
  private subject = new Subject<void>();
  userSessions: Array<Session>;

  constructor(private http: HttpClient, public notificationService: NotificationService) { }

  getUserSessions(userId: number): Observable<Session[]> {
    const url: string = `${this.apiUrl}/sessions/user/${userId}`;

    return this.http.get<Session[]>(url, { headers: this.headers });
  }

  sendUpdateSessionsEvent(): void {
    this.subject.next();
  }

  getUpdateSessionsEvent(): Observable<any> {
    return this.subject.asObservable();
  }
}
