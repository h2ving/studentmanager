import { Course } from "./course.module";

export class Announcement {
  id: number;
  announcement: string;
  course: Course;
  createdAt: Date;

  constructor(id: number, announcement: string, course: Course, createdAt: Date) {
    this.id = id;
    this.announcement = announcement;
    this.course = course;
    this.createdAt = createdAt;
  }
}
