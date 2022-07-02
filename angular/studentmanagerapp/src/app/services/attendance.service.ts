import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Attendance } from '../models/attendance.module';

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {
  private apiUrl: string = 'http://localhost:8080/api';
  headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient) { }

  getUserAttendance(userId: number): Observable<Attendance[]> {
    const url: string = `${this.apiUrl}/attendances/user/${userId}`;

    return this.http.get<Attendance[]>(url, { headers: this.headers });
  }
}
