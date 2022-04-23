import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentAttendanceItemComponent } from './student-attendance-item.component';

describe('StudentStudentAttendanceItemComponent', () => {
  let component: StudentAttendanceItemComponent;
  let fixture: ComponentFixture<StudentAttendanceItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StudentAttendanceItemComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentAttendanceItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
