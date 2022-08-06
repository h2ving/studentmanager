import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseGradesChartComponent } from './course-grades-chart.component';

describe('CourseGradesChartComponent', () => {
  let component: CourseGradesChartComponent;
  let fixture: ComponentFixture<CourseGradesChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CourseGradesChartComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CourseGradesChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
