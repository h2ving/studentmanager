import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/notification.service';
import { ChartsInterface } from 'src/app/interfaces/charts-interface';


@Component({
  selector: 'app-users-chart',
  templateUrl: './users-chart.component.html',
  styleUrls: ['./users-chart.component.scss']
})
export class UsersChartComponent implements OnInit {
  single: ChartsInterface;
  view: [number, number] = [600, 400];

  gradient: boolean = true;
  showLabels: boolean = true;
  isDoughnut: boolean = false;

  constructor(private authService: AuthService, public notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.authService.getUsersCharts()
      .subscribe({
        next: (response) => {
          this.single = response;
        },
        error: () => {
          this.notificationService.showError("Error Receiving Users Charts")
        }
      });
  }
}
