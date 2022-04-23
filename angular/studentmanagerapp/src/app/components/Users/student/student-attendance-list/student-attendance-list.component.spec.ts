import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentAttendanceListComponent } from './student-attendance-list.component';

describe('StudentAttendanceListComponent', () => {
  let component: StudentAttendanceListComponent;
  let fixture: ComponentFixture<StudentAttendanceListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StudentAttendanceListComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentAttendanceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
