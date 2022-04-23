import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentCourseItemComponent } from './student-course-item.component';

describe('StudentCourseItemComponent', () => {
  let component: StudentCourseItemComponent;
  let fixture: ComponentFixture<StudentCourseItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StudentCourseItemComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentCourseItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
