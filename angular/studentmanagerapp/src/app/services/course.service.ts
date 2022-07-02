import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { NotificationService } from './notification.service';
import { Course } from '../models/course.module';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private apiUrl: string = 'http://localhost:8080/api';
  headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient, public notificationService: NotificationService) { }

  getAllCourses(): Observable<Course[]> {
    const url = `${this.apiUrl}/courses/all`;

    return this.http.get<Course[]>(url, { headers: this.headers });
  }

  getUserCourses(email: string): Observable<Course[]> {
    const url = `${this.apiUrl}/courses/email/${email}`;

    return this.http.get<Course[]>(url, { headers: this.headers });
  }
}
