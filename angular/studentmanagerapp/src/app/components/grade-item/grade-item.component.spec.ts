import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GradeItemComponent } from './grade-item.component';

describe('GradeItemComponent', () => {
  let component: GradeItemComponent;
  let fixture: ComponentFixture<GradeItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GradeItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GradeItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
