import { Component, OnInit, Input } from '@angular/core';
import { Grade } from 'src/app/models/grade.model';

@Component({
  selector: 'app-grade-item',
  templateUrl: './grade-item.component.html',
  styleUrls: ['./grade-item.component.scss']
})
export class GradeItemComponent implements OnInit {
  @Input() grade: Grade;

  constructor() { }

  ngOnInit(): void {
  }

}
