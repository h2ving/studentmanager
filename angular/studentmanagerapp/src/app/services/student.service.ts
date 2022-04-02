import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from '../models/student.Model';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  })
};

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private apiUrl = 'http://db4free.net:3306/sdastudentapp';

  constructor(private http: HttpClient) { }

  // Get all students
  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.apiUrl);
  }

  // Get student by ID
  getStudentById(studentId: number): Observable<Student> {
    const url = `${this.apiUrl}/${studentId}`;

    return this.http.get<Student>(url);
  }

  // Add a new Student
  addStudent(student: Student): Observable<Student> {
    return this.http.post<Student>(this.apiUrl, student, httpOptions);
  }

  // Edit Student
  editStudent(studentId: number): Observable<Student> {
    const url = `${this.apiUrl}/${studentId}`;

    return this.http.put<Student>(url, httpOptions);
  }

  // Delete Student
  deleteStudent(studentId: number): Observable<Student> {
    const url = `${this.apiUrl}/${studentId}`;

    return this.http.delete<Student>(url, httpOptions);
  }

}
