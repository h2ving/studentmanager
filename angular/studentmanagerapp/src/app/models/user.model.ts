import { Roles } from "./roles.model";
export class User {
  id: number;
  email: string;
  password: string;
  role: Roles;
  firstName: string;
  lastName: string;
  gender: string;
  dob: Date;
  mobile: string;
  createdAt: Date;
  age: number;

  constructor(
    id: number,
    email: string,
    password: string,
    role: Roles,
    firstName: string,
    lastName: string,
    gender: string,
    dob: Date,
    mobile: string,
    createdAt: Date,
    age: number,
  ) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.role = role;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.dob = dob;
    this.mobile = mobile;
    this.createdAt = createdAt;
    this.age = age;
  }
}
