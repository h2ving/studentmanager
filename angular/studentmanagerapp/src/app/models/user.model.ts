export class User {
  id: number;
  email: string;
  password: string;
  role: string;
  firstName: string;
  lastName: string;
  dob: Date;
  mobile: string;
  createdAt: Date;

  constructor(
    id: number,
    email: string,
    password: string,
    role: string,
    firstName: string,
    lastName: string,
    dob: Date,
    mobile: string,
    createdAt: Date,
  ) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.role = role;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dob = dob;
    this.mobile = mobile;
    this.createdAt = createdAt;
  }
}
