import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Grade } from '../models/grade.model';

@Injectable({
  providedIn: 'root'
})
export class GradeService {

  private apiUrl: string = 'http://localhost:8080/api';
  headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient) { }

  // Get all Grades from 1 User by email
  getUserGrades(email: string): Observable<Grade[]> {
    const url = `${this.apiUrl}/attendances/get/${email}`;

    return this.http.get<Grade[]>(url, { headers: this.headers })
  }
}
