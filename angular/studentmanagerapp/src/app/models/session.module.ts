import { Course } from "./course.module";
import { User } from "./user.model";

export class Session {
  id: number;
  description: string;
  startDateTime: Date;
  duration: number;
  course: Course;
  users?: User[];

  constructor(id: number, description: string, startDateTime: Date, duration: number, course: Course, users?: User[]) {
    this.id = id;
    this.description = description;
    this.startDateTime = startDateTime;
    this.duration = duration;
    this.course = course;
    this.users = users;
  }
}
