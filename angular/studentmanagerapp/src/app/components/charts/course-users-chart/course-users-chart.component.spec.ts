import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseUsersChartComponent } from './course-users-chart.component';

describe('CourseUsersChartComponent', () => {
  let component: CourseUsersChartComponent;
  let fixture: ComponentFixture<CourseUsersChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CourseUsersChartComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CourseUsersChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
