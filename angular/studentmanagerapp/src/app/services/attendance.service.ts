import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Attendance } from '../models/attendance.module';
import { ChartsInterface } from '../interfaces/charts-interface';

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

  getUserAttendancesPaginated(userId: number, pageNumber: number, pageSize: number) {
    const url: string = `${this.apiUrl}/attendances/user/${userId}/paginated`;

    return this.http.get<Attendance[]>(url, {
      headers: this.headers, params: {
        pageNumber,
        pageSize,
      }
    });
  }

  getCourseAttendancesChart(): Observable<ChartsInterface> {
    const url: string = `${this.apiUrl}/attendances/charts`;

    return this.http.get<ChartsInterface>(url, { headers: this.headers });
  }

  getCourseAttendances(courseId: number, pageNumber: number, pageSize: number): Observable<Attendance[]> {
    const url: string = `${this.apiUrl}/course/${courseId}/attendances`;

    return this.http.get<Attendance[]>(url, {
      headers: this.headers, params: {
        pageNumber,
        pageSize,
      }
    });
  }

  getSessionAttendances(sessionId: number)
    : Observable<Attendance[]> {
    const url: string = `${this.apiUrl}/attendances/session/
    ${sessionId}`;

    return this.http.get<Attendance[]>(url, { headers: this.headers });
  }

  getUserCourseAttendances(userId: number, courseId: number): Observable<Attendance[]> {
    const url: string = `${this.apiUrl}/attendances/user/${userId}/course/${courseId}`;

    return this.http.get<Attendance[]>(url, { headers: this.headers });
  }

  addAttendances(body: any): Observable<string> {
    const url: string = `${this.apiUrl}/attendances`;

    return this.http.post(url, body, { responseType: 'text' });
  }

  editAttendance(attendanceId: number, body: { attendance: string }): Observable<Attendance> {
    console.log(body)
    const url: string = `${this.apiUrl}/attendance/${attendanceId}`;

    return this.http.patch<Attendance>(url, body, { headers: this.headers });
  }

  deleteAttendance(attendanceId: number): Observable<string> {
    const url: string = `${this.apiUrl}/attendance/${attendanceId}`;

    return this.http.delete(url, { responseType: 'text' });
  }
}
