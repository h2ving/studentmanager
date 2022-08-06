import { Component, OnInit } from '@angular/core';
import { CourseService } from 'src/app/services/course.service';
import { NotificationService } from 'src/app/services/notification.service';
import { ChartsInterface } from 'src/app/interfaces/charts-interface';

@Component({
  selector: 'app-course-chart',
  templateUrl: './course-chart.component.html',
  styleUrls: ['./course-chart.component.scss']
})
export class CourseChartComponent implements OnInit {
  single: ChartsInterface;
  view: [number, number] = [600, 400];

  gradient: boolean = true;
  showXAxis: boolean = true;
  showYAxis: boolean = true;
  yAxisLabel: string = 'Courses';
  xAxisLabel: string = 'Amount';

  constructor(private courseService: CourseService, public notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.courseService.getCourseCountCharts()
      .subscribe({
        next: (response) => {
          this.single = response;
        },
        error: () => {
          this.notificationService.showError("Error Receiving Users in Courses Charts")
        }
      });
  }
}
