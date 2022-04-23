import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentGradeItemComponent } from './student-grade-item.component';

describe('StudentGradeItemComponent', () => {
  let component: StudentGradeItemComponent;
  let fixture: ComponentFixture<StudentGradeItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StudentGradeItemComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentGradeItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
