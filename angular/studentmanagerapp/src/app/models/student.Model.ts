export class Student {
  id: number;
  firstName: String;
  lastName: String;
  dob: Date;
  mobile: String;
  joinedDate: Date;
  userId: number;

  constructor(id: number, firstName: String, lastName: String, dob: Date, mobile: String, joinedDate: Date, userId: number) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dob = dob;
    this.mobile = mobile;
    this.joinedDate = joinedDate;
    this.userId = userId;
  }
}
