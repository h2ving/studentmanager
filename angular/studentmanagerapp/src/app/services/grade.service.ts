import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Grade } from '../models/grade.model';

@Injectable({
  providedIn: 'root'
})
export class GradeService {
  headers = new HttpHeaders().set('Content-Type', 'application/json');
  private apiUrl: string = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getUserGrades(userId: number): Observable<Grade[]> {
    const url: string = `${this.apiUrl}/grades/user/${userId}`;

    return this.http.get<Grade[]>(url, { headers: this.headers })
  }
}
