import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AddAnnouncementInterface } from '../interfaces/add-announcement-interface';
import { EditAnnouncementInterface } from '../interfaces/edit-announcement-interface';
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

  getCourseAnnouncements(courseId: number): Observable<Announcement[]> {
    const url: string = `${this.apiUrl}/announcements/course/${courseId}`;

    return this.http.get<Announcement[]>(url, { headers: this.headers });
  }

  editAnnouncement(announcementId: number, body: EditAnnouncementInterface): Observable<Announcement> {
    const url: string = `${this.apiUrl}/announcement/${announcementId}`;

    return this.http.patch<Announcement>(url, body);
  }

  saveAnnouncement(body: AddAnnouncementInterface): Observable<Announcement> {
    const url: string = `${this.apiUrl}/announcement`;

    return this.http.post<Announcement>(url, body, { headers: this.headers });
  }

  deleteAnnouncement(announcementId: number): Observable<any> {
    const url: string = `${this.apiUrl}/announcement/${announcementId}`;

    return this.http.delete(url, { responseType: 'text' });
  }
}
