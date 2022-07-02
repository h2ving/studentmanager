import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentAnnouncementListComponent } from './student-announcement-list.component';

describe('StudentAnnouncementListComponent', () => {
  let component: StudentAnnouncementListComponent;
  let fixture: ComponentFixture<StudentAnnouncementListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudentAnnouncementListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentAnnouncementListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
