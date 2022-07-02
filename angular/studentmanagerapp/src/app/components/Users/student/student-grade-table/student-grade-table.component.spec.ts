import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentGradeTableComponent } from './student-grade-table.component';

describe('StudentGradeTableComponent', () => {
  let component: StudentGradeTableComponent;
  let fixture: ComponentFixture<StudentGradeTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StudentGradeTableComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentGradeTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
