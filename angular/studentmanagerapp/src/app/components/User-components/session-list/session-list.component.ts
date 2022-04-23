import { Component, OnInit } from '@angular/core';
import { Course } from 'src/app/models/course.module';
import { Session } from 'src/app/models/session.module';

@Component({
  selector: 'app-session-list',
  templateUrl: './session-list.component.html',
  styleUrls: ['./session-list.component.scss']
})
export class SessionListComponent implements OnInit {
  userSessions: any[] = [
    new Session(1, 'First meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(2, 'Second meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(3, 'Third meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(4, 'Fourth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(5, 'Fifth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(6, 'Sixth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(7, 'Seventh meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(8, 'Eigth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(8, 'Eigth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(8, 'Eigth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(8, 'Eigth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(8, 'Eigth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(8, 'Eigth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(8, 'Eigth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(8, 'Eigth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(8, 'Eigth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(8, 'Eigth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(8, 'Eigth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
    new Session(8, 'Eigth meeting of Java', new Date(), 4, new Course(1, 'JavaRemoteEE11', 'Java from scratch', new Date(), new Date(), 388, true)),
  ]
  constructor() { }

  ngOnInit(): void {
  }

}
