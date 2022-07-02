import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentAttendanceTableComponent } from './student-attendance-table.component';

describe('StudentAttendanceTableComponent', () => {
  let component: StudentAttendanceTableComponent;
  let fixture: ComponentFixture<StudentAttendanceTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StudentAttendanceTableComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentAttendanceTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
