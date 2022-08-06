import { Component, OnInit } from '@angular/core';
import { GradeService } from 'src/app/services/grade.service';
import { NotificationService } from 'src/app/services/notification.service';
import { ChartsInterface } from 'src/app/interfaces/charts-interface';

@Component({
  selector: 'app-course-grades-chart',
  templateUrl: './course-grades-chart.component.html',
  styleUrls: ['./course-grades-chart.component.scss']
})
export class CourseGradesChartComponent implements OnInit {
  single: ChartsInterface;
  view: [number, number] = [600, 400];

  gradient: boolean = true;
  showXAxis: boolean = true;
  showYAxis: boolean = true;
  yAxisLabel: string = 'Courses';
  xAxisLabel: string = 'Amount';

  constructor(private gradeService: GradeService, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.gradeService.getCourseAverageGradesChart()
      .subscribe({
        next: (response) => {
          this.single = response;
        },
        error: () => {
          this.notificationService.showError("Error Receiving Average Grade Chart");
        }
      });
  }
}
