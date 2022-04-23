import { Component, OnInit, Input } from '@angular/core';
import { Grade } from 'src/app/models/grade.model';

@Component({
  selector: 'app-student-grade-item',
  templateUrl: './student-grade-item.component.html',
  styleUrls: ['./student-grade-item.component.scss']
})
export class StudentGradeItemComponent implements OnInit {
  @Input() grade: Grade;

  constructor() { }

  ngOnInit(): void {
  }

}
