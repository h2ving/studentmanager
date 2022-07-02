import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NotificationService } from './notification.service';
import { Course } from '../models/course.module';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  headers = new HttpHeaders().set('Content-Type', 'application/json');
  private apiUrl: string = 'http://localhost:8080/api';

  constructor(private http: HttpClient, public notificationService: NotificationService) { }

  getCourses(): Observable<Course[]> {
    const url: string = `${this.apiUrl}/courses`;

    return this.http.get<Course[]>(url, { headers: this.headers });
  }

  getUserCourses(userId: number, checkCourseContainsUser: boolean): Observable<Course[]> {
    const url: string = `${this.apiUrl}/courses/user/${userId}?userCourses=${checkCourseContainsUser}`;

    return this.http.get<Course[]>(url, { headers: this.headers });
  }

  getCoursesData(): Observable<Course[]> {
    const url: string = `${this.apiUrl}/courses/data`;

    return this.http.get<Course[]>(url, { headers: this.headers })
  }

  joinCourse(userId: number, courseId: number) {
    const url: string = `${this.apiUrl}/course/add/user`;
    const body = { userId, courseId };

    return this.http.post(url, body, { responseType: 'text' });
  }

  leaveCourse(userId: number, courseId: number) {
    const url: string = `${this.apiUrl}/course/remove/user`;
    const body = { userId, courseId };

    return this.http.post(url, body, { responseType: 'text' });
  }
}
