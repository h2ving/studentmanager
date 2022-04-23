import { Session } from "./session.module";
import { User } from "./user.model";

export class Attendance {
  id: number;
  session: Session;
  didAttend: boolean;
  user?: User;

  constructor(id: number, session: Session,
    didAttend: boolean, user?: User) {
    this.id = id;
    this.user = user;
    this.session = session;
    this.didAttend = didAttend;
  }
}
