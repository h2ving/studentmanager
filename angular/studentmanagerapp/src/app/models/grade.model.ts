import { Session } from "./session.module";
import { User } from "./user.model";

export class Grade {
  id: number;
  grade: number;
  session: Session;
  user: User;

  constructor(id: number, grade: number, session: Session, user: User) {
    this.id = id;
    this.grade = grade;
    this.session = session;
    this.user = user;
  }
}
