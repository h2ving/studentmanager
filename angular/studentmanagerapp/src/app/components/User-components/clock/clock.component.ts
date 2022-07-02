import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-clock',
  templateUrl: './clock.component.html',
  styleUrls: ['./clock.component.scss']
})
export class ClockComponent implements OnInit {
  currentSec: number = this.getSecondsToday();
  seconds: number = (this.currentSec / 60) % 1;
  minutes: number = (this.currentSec / 3600) % 1;
  hours: number = (this.currentSec / 43200) % 1;

  constructor() { }

  ngOnInit(): void {
  }

  numSequence(n: number): Array<number> {
    return Array(n);
  }

  getSecondsToday(): number {
    let now: Date = new Date();
    let today: Date = new Date(now.getFullYear(), now.getMonth(), now.getDate());
    let diff = Number(now) - Number(today);

    return Math.round(diff / 1000);
  }
}
