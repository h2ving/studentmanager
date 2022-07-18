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
  private updateSessionsSubject = new Subject<void>();
  private updateSessionsDataSubject = new Subject<void>();
  userSessions: Array<Session>;

  constructor(private http: HttpClient, public notificationService: NotificationService) { }

  getUserSessions(userId: number): Observable<Session[]> {
    const url: string = `${this.apiUrl}/sessions/user/${userId}`;

    return this.http.get<Session[]>(url, { headers: this.headers });
  }

  sendUpdateSessionsEvent(): void {
    this.updateSessionsSubject.next();
  }

  getUpdateSessionsEvent(): Observable<any> {
    return this.updateSessionsSubject.asObservable();
  }

  sendUpdateSessionsDataEvent(): void {
    this.updateSessionsDataSubject.next();
  }

  getUpdateSessionsDataEvent(): Observable<any> {
    return this.updateSessionsDataSubject.asObservable();
  }

  getCourseSessions(courseId: number): Observable<Session[]> {
    const url: string = `${this.apiUrl}/sessions/course/${courseId}`;

    return this.http.get<Session[]>(url, { headers: this.headers });
  }

  getSessionsData(): Observable<Session[]> {
    const url: string = `${this.apiUrl}/sessions/data`;

    return this.http.get<Session[]>(url, { headers: this.headers });
  }

  editSession(sessionId: number, body: any): Observable<Session> {
    const url: string = `${this.apiUrl}/session/${sessionId}`;

    return this.http.patch<Session>(url, body);
  }

  saveSession(body: any): Observable<Session> {
    const url: string = `${this.apiUrl}/session`;

    return this.http.post<Session>(url, body, { headers: this.headers });
  }

  deleteSession(sessionId: number): Observable<any> {
    const url: string = `${this.apiUrl}/session/${sessionId}`;

    return this.http.delete(url, { responseType: 'text' });
  }
}
