import { UserDataInterface } from "../interfaces/user-data-interface";

export class User {
  user: UserDataInterface;
  redirectURI: string;
  attendancePercentage: number;
  averageGrade: number;
  nextSession: string;

  constructor(
    user: UserDataInterface,
    redirectURI: string,
    attendancePercentage: number,
    averageGrade: number,
    nextSession: string
  ) {
    this.user = user,
      this.redirectURI = redirectURI,
      this.attendancePercentage = attendancePercentage,
      this.averageGrade = averageGrade,
      this.nextSession = nextSession
  }
}
