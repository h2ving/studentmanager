import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { NotificationService } from './notification.service';
import { Attendance } from '../models/attendance.module';

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {
  private apiUrl: string = 'http://localhost:8080/api';
  headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient) { }

  getUserAttendance(email: string): Observable<Attendance[]> {
    const url = `${this.apiUrl}/attendances/get/${email}`;

    return this.http.get<Attendance[]>(url, { headers: this.headers });
  }
}
