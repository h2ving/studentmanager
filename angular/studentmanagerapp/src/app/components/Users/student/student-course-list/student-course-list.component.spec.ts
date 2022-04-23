import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentCourseListComponent } from './student-course-list.component';

describe('StudentCourseListComponent', () => {
  let component: StudentCourseListComponent;
  let fixture: ComponentFixture<StudentCourseListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StudentCourseListComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentCourseListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
