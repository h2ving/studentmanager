import { Course } from "./course.module";

export class Announcement {
  id: number;
  announcement: string;
  course: Course;
  date: Date;
  markAsRead: boolean;

  constructor(id: number, announcement: string, course: Course, markAsRead: boolean, date: Date) {
    this.id = id;
    this.announcement = announcement;
    this.course = course;
    this.date = date;
    this.markAsRead = markAsRead;
  }
}
