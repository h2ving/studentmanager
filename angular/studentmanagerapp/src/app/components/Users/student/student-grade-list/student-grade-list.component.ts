import { Component, OnInit, Input } from '@angular/core';
import { Grade } from 'src/app/models/grade.model';
import { NotificationService } from 'src/app/services/notification.service';
import { GradeService } from 'src/app/services/grade.service';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-student-grade-list',
  templateUrl: './student-grade-list.component.html',
  styleUrls: ['./student-grade-list.component.scss']
})
export class StudentGradeListComponent implements OnInit {
  @Input() currentUser: User;
  grades: Array<Grade>;

  constructor(private gradeService: GradeService, public notificationService: NotificationService) { }

  ngOnInit(): void {
    console.log(this.currentUser)
    this.gradeService.getUserGrades(this.currentUser.email).subscribe({
      next: (res) => {
        console.log(res);
        this.grades = res;
      },
      error: (err) => {
        this.notificationService.showError('Please try again later.', 'Failed to receive grades.')
      }
    })
  }

}
