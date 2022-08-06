import { Component, OnInit } from '@angular/core';
import { AttendanceService } from 'src/app/services/attendance.service';
import { NotificationService } from 'src/app/services/notification.service';
import { ChartsInterface } from 'src/app/interfaces/charts-interface';

@Component({
  selector: 'app-attendance-chart',
  templateUrl: './attendance-chart.component.html',
  styleUrls: ['./attendance-chart.component.scss']
})
export class AttendanceChartComponent implements OnInit {
  single: ChartsInterface;
  view: [number, number] = [600, 400];

  gradient: boolean = true;
  showXAxis: boolean = true;
  showYAxis: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Attendance %';

  constructor(private attendanceService: AttendanceService, public notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.attendanceService.getCourseAttendancesChart()
      .subscribe({
        next: (response) => {
          this.single = response;
        },
        error: () => {
          this.notificationService.showError("Error Receiving Course Attendances Charts")
        }
      });
  }

}
