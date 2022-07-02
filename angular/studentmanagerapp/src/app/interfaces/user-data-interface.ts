import { Course } from "../models/course.module";
import { Roles } from "../models/roles.model";

export interface UserDataInterface {
  id: number;
  email: string;
  role: Roles;
  firstName: string;
  lastName: string;
  gender: string;
  dob: Date;
  mobile: string;
  createdAt: Date;
  updatedAt: Date;
  age: number;
  coursesAssigned: Course[];
}
