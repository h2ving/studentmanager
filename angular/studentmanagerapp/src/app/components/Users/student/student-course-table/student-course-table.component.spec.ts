import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentCourseTableComponent } from './student-course-table.component';

describe('StudentCourseTableComponent', () => {
  let component: StudentCourseTableComponent;
  let fixture: ComponentFixture<StudentCourseTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StudentCourseTableComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentCourseTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
