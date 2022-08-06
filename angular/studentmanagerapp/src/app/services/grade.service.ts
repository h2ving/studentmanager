import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Grade } from '../models/grade.model';
import { ChartsInterface } from '../interfaces/charts-interface';

@Injectable({
  providedIn: 'root'
})
export class GradeService {
  headers = new HttpHeaders().set('Content-Type', 'application/json');
  private apiUrl: string = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getUserGrades(userId: number): Observable<Grade[]> {
    const url: string = `${this.apiUrl}/grades/user/${userId}`;

    return this.http.get<Grade[]>(url, { headers: this.headers });
  }

  getUserGradesPaginated(userId: number, pageNumber: number, pageSize: number): Observable<Grade[]> {
    const url: string = `${this.apiUrl}/grades/user/${userId}/paginated`;

    return this.http.get<Grade[]>(url, {
      headers: this.headers, params: {
        pageNumber,
        pageSize,
      }
    });
  }

  getCourseAverageGradesChart(): Observable<ChartsInterface> {
    const url: string = `${this.apiUrl}/grades/averages/chart`;

    return this.http.get<ChartsInterface>(url, { headers: this.headers });
  }

  getCourseGrades(courseId: number, pageNumber: number, pageSize: number): Observable<Grade[]> {
    const url: string = `${this.apiUrl}/course/${courseId}/grades`;

    return this.http.get<Grade[]>(url, {
      headers: this.headers, params: {
        pageNumber,
        pageSize,
      }
    });
  }

  getUserCourseGrades(userId: number, courseId: number): Observable<Grade[]> {
    const url: string = `${this.apiUrl}/grades/user/${userId}/course/${courseId}`;

    return this.http.get<Grade[]>(url, { headers: this.headers });
  }

  addGrades(body: any): Observable<string> {
    const url: string = `${this.apiUrl}/grades`;

    return this.http.post(url, body, { responseType: 'text' });
  }

  editGrade(gradeId: number, body: { grade: number }): Observable<Grade> {
    const url: string = `${this.apiUrl}/grade/${gradeId}`;

    return this.http.patch<Grade>(url, body, { headers: this.headers });
  }

  deleteGrade(gradeId: number): Observable<string> {
    const url: string = `${this.apiUrl}/grade/${gradeId}`;

    return this.http.delete(url, { responseType: 'text' });
  }
}
