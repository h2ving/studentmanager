import { Component, OnInit, Input } from '@angular/core';
import { Grade } from 'src/app/models/grade.model';
import { NotificationService } from 'src/app/services/notification.service';
import { GradeService } from 'src/app/services/grade.service';
import { UserDataInterface } from 'src/app/interfaces/user-data-interface';

@Component({
  selector: 'app-student-grade-table',
  templateUrl: './student-grade-table.component.html',
  styleUrls: ['./student-grade-table.component.scss']
})
export class StudentGradeTableComponent implements OnInit {
  @Input() currentUser: UserDataInterface;
  grades: Array<Grade>;
  gradesCopy: Array<Grade>;
  courses: Array<string>;
  selectedCourseGrades: string = 'All Grades';

  constructor(private gradeService: GradeService, public notificationService: NotificationService) { }
  gradeDate: string;

  ngOnInit(): void {
    this.getGrades();
  }

  getGrades(): void {
    this.gradeService.getUserGrades(this.currentUser.id).subscribe({
      next: (response: any) => {
        const { userGrades, userCourses } = response;

        this.grades = userGrades;
        this.gradesCopy = userGrades;
        this.courses = userCourses;
      },
      error: () => {
        this.notificationService.showError('Failed to receive Grades.');
      }
    });
  }

  filterGrades(event: Event): void {
    const { value } = event.target as HTMLInputElement;

    this.selectedCourseGrades = value;

    if (value !== 'All Grades') {
      this.grades = this.gradesCopy.filter((grade) => grade.session.course.name === value);
    } else {
      this.getGrades();
    }
  }
}
