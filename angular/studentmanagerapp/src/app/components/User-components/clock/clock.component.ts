import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';

@Component({
  selector: 'app-clock',
  templateUrl: './clock.component.html',
  styleUrls: ['./clock.component.scss']
})
export class ClockComponent implements OnInit {
  h: string;
  m: string;
  s: string;
  private timerId: any = null;

  constructor() { }

  ngOnInit() {
    this.setCurrentTime();
    this.timerId = this.updateTime();
  }

  ngOnDestroy() {
    clearInterval(this.timerId);
  }

  private setCurrentTime() {
    const currTime = moment(new Date());

    this.h = this.leadingZeros(moment(currTime).hours());
    this.m = this.leadingZeros(moment(currTime).minutes());
    this.s = this.leadingZeros(moment(currTime).seconds());
  }

  private updateTime() {
    setInterval(() => {
      this.setCurrentTime();
    }, 1000);
  }

  private leadingZeros(value: number) {
    return value < 10 ? `0${value}` : value.toString();
  }
}
