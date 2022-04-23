import { Component, OnInit, Input } from '@angular/core';
import { Session } from 'src/app/models/session.module';

@Component({
  selector: 'app-session-item',
  templateUrl: './session-item.component.html',
  styleUrls: ['./session-item.component.scss']
})
export class SessionItemComponent implements OnInit {
  @Input() session: Session;

  constructor() { }

  ngOnInit(): void {
  }

}
