import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Announcement } from '../models/announcement.model';

@Injectable({
  providedIn: 'root'
})
export class AnnouncementService {
  private apiUrl: string = 'http://localhost:8080/api';
  headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient) { }

  getUserAnnouncements(userId: number): Observable<Announcement[]> {
    const url: string = `${this.apiUrl}/announcements/user/${userId}`;

    return this.http.get<Announcement[]>(url, { headers: this.headers });
  }
}
