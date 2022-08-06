import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NotificationService } from './notification.service';
import { Course } from '../models/course.module';
import { ChartsInterface } from '../interfaces/charts-interface';

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

  getCourseUsersCharts(): Observable<ChartsInterface[]> {
    const url: string = `${this.apiUrl}/courses/data/charts`;

    return this.http.get<ChartsInterface[]>(url, { headers: this.headers });
  }

  getPaginatedCourses(pageNumber: number, pageSize: number): Observable<Course[]> {
    const url: string = `${this.apiUrl}/courses/paginated`;

    return this.http.get<Course[]>(url, {
      headers: this.headers, params: {
        pageNumber,
        pageSize,
      }
    });
  }

  getCourseCountCharts(): Observable<ChartsInterface> {
    const url: string = `${this.apiUrl}/courses/count/charts`;

    return this.http.get<ChartsInterface>(url, { headers: this.headers });
  }

  getCourse(courseId: number): Observable<Course> {
    const url: string = `${this.apiUrl}/course/${courseId}`;

    return this.http.get<Course>(url, { headers: this.headers });
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

  addCourse(body: any): Observable<string> {
    const url: string = `${this.apiUrl}/course`;

    return this.http.post(url, body, { responseType: 'text' });
  }

  editCourse(courseId: number, body: any): Observable<string> {
    const url: string = `${this.apiUrl}/course/${courseId}`;

    return this.http.put(url, body, { responseType: 'text' });
  }

  leaveCourse(userId: number, courseId: number) {
    const url: string = `${this.apiUrl}/course/remove/user`;
    const body = { userId, courseId };

    return this.http.post(url, body, { responseType: 'text' });
  }

  deleteCourse(courseId: number): Observable<string> {
    const url: string = `${this.apiUrl}/course/${courseId}`;

    return this.http.delete(url, { responseType: 'text' });
  }
}
