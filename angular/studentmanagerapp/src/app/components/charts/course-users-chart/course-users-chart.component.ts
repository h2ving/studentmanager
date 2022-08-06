import { Component, OnInit } from '@angular/core';
import { CourseService } from 'src/app/services/course.service';
import { NotificationService } from 'src/app/services/notification.service';
import { ChartsInterface } from 'src/app/interfaces/charts-interface';

@Component({
  selector: 'app-course-users-chart',
  templateUrl: './course-users-chart.component.html',
  styleUrls: ['./course-users-chart.component.scss']
})
export class CourseUsersChartComponent implements OnInit {
  single: ChartsInterface[];
  view: [number, number] = [600, 400];

  gradient: boolean = true;
  showLabels: boolean = true;
  isDoughnut: boolean = false;

  constructor(private courseService: CourseService, public notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.courseService.getCourseUsersCharts()
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
