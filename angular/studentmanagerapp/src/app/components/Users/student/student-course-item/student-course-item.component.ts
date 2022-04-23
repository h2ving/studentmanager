import { Component, OnInit, Input } from '@angular/core';
import { Course } from 'src/app/models/course.module';

@Component({
  selector: 'app-student-course-item',
  templateUrl: './student-course-item.component.html',
  styleUrls: ['./student-course-item.component.scss']
})
export class StudentCourseItemComponent implements OnInit {
  @Input() course: Course;
  @Input() allCourses: boolean;

  constructor() { }

  ngOnInit(): void {
  }

  addCourseToUser(): void {

  }
}
