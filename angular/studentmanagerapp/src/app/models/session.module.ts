import { UserDataInterface } from "../interfaces/user-data-interface";
import { Course } from "./course.module";
import { User } from "./user.model";

export class Session {
  id: number;
  description: string;
  startDateTime: Date;
  academicHours: number;
  course: Course;
  user?: UserDataInterface[];

  constructor(id: number, description: string, startDateTime: Date, academicHours: number, course: Course, users?: UserDataInterface[]) {
    this.id = id;
    this.description = description;
    this.startDateTime = startDateTime;
    this.academicHours = academicHours;
    this.course = course;
    this.user = users;
  }
}
