import { Component, OnInit } from '@angular/core';
import { Grade } from 'src/app/models/grade.model';

@Component({
  selector: 'app-student-grade-list',
  templateUrl: './student-grade-list.component.html',
  styleUrls: ['./student-grade-list.component.scss']
})
export class StudentGradeListComponent implements OnInit {
  grades: any[] = [
    new Grade(1, 'JavaRemoteEE11', new Date(), 4),
    new Grade(2, 'MySql Workshop 2021', new Date(), 3),
    new Grade(3, 'Angular Crash Course', new Date(), 3),
    new Grade(4, 'React Hooks', new Date(), 3),
    new Grade(5, 'CSS Introduction', new Date(), 3),
    new Grade(6, 'CSS with Sass', new Date(), 3),
    new Grade(7, 'Designing relational databases', new Date(), 3),
    new Grade(8, 'Backend technologies', new Date(), 2),
    new Grade(8, 'Backend technologies', new Date(), 2),
    new Grade(8, 'Backend technologies', new Date(), 2),
    new Grade(8, 'Backend technologies', new Date(), 2),
    new Grade(8, 'Backend technologies', new Date(), 2),
    new Grade(8, 'Backend technologies', new Date(), 2),
    new Grade(8, 'Backend technologies', new Date(), 2),
    new Grade(8, 'Backend technologies', new Date(), 2),
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
