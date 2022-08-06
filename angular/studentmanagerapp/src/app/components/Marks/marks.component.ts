import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup, UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { AttendanceService } from 'src/app/services/attendance.service';
import { GradeService } from 'src/app/services/grade.service';
import { CourseService } from 'src/app/services/course.service';
import { NotificationService } from 'src/app/services/notification.service';
import { Grade } from 'src/app/models/grade.model';
import { Attendance } from 'src/app/models/attendance.module';
import { User } from 'src/app/models/user.model';
import { faLightbulb, faEdit, faTrash, faCheck, faChartLine, faPercentage, faArrowRight } from '@fortawesome/free-solid-svg-icons';
import { Course } from 'src/app/models/course.module';

@Component({
  selector: 'app-marks',
  templateUrl: './marks.component.html',
  styleUrls: ['./marks.component.scss']
})
export class MarksComponent implements OnInit {
  faLightbulb = faLightbulb;
  faEdit = faEdit;
  faTrash = faTrash;
  faCheck = faCheck;
  faChartLine = faChartLine;
  faPercentage = faPercentage;
  faArrowRight = faArrowRight;

  currentUser: User = this.authService.getCurrentUser;
  currentUserId: number;
  currentUserLoaded: boolean = true;

  selectedCourseId: string;
  course: Course;

  userGradesUserId: number | null;
  userAttendancesUserId: number | null;

  userGrades: Array<Grade>;
  userAttendances: Array<Attendance>;

  editGradeId: number | null;
  editAttendanceId: number | null;

  editGradeForm: UntypedFormGroup;
  editAttendanceForm: UntypedFormGroup;

  constructor(private authService: AuthService, private attendanceService: AttendanceService, private gradeService: GradeService, private courseService: CourseService, private formBuilder: UntypedFormBuilder, private activatedRoute: ActivatedRoute, public notificationService: NotificationService) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => this.currentUserId = params['id']);

    if (!this.currentUser.user) {
      this.getUserData();
    }

    if (this.currentUser.user) {
      this.currentUser.user.coursesAssigned = this.currentUser.user.coursesAssigned.sort((course, locales: any) => course.description.localeCompare(locales.description))
    }


    this.createEditGradeForm();
    this.createEditAttendanceForm();
  }

  getUserData(): void {
    this.currentUserLoaded = false;

    this.authService.getUserDatatById(this.currentUserId).subscribe({
      next: () => {
        this.currentUser = this.authService.currentUser;

        this.currentUser.user.coursesAssigned = this.currentUser.user.coursesAssigned.sort((course, locales: any) => course.description.localeCompare(locales.description))

        this.currentUserLoaded = true;
      },
      error: () => {
        this.notificationService.showError('Error getting User data, please refresh the page');
      }
    });
  }

  createEditGradeForm() {
    this.editGradeForm = this.formBuilder.group({ grade: '' });
  }

  createEditAttendanceForm() {
    this.editAttendanceForm = this.formBuilder.group({ attendance: '' });
  }

  getCourseUsers(event: Event): void {
    const { value } = event.target as HTMLInputElement;

    this.selectedCourseId = value;

    if (value) {
      this.courseService.getCourse(+this.selectedCourseId)
        .subscribe({
          next: (response) => {
            this.course = response;
          },
          error: (error) => {
            console.log(error);
            this.notificationService.showError(error.error);
          }
        });

      this.userGradesUserId = null;
      this.userAttendancesUserId = null;

      this.editAttendanceForm.reset({ attendance: '' });
      this.editGradeForm.reset({ grade: '' });
    }
  }

  getUserGrades(userId: number): void {
    this.gradeService.getUserCourseGrades(userId, this.course.id)
      .subscribe({
        next: (response: any) => {
          this.userGrades = response;
        },
        error: (error) => {
          console.log(error);
          this.notificationService.showError(error.error);
        }
      });

    this.editAttendanceForm.reset({ attendance: '' });
    this.editGradeForm.reset({ grade: '' });
  }

  getUserAttendances(userId: number) {
    this.attendanceService.getUserCourseAttendances(userId, this.course.id)
      .subscribe({
        next: (response) => {
          this.userAttendances = response;
        },
        error: (error) => {
          console.log(error);
          this.notificationService.showError(error.error);
        }
      });

    this.editAttendanceForm.reset({ attendance: '' });
    this.editGradeForm.reset({ grade: '' });
  }

  toggleShowGrades(userId: number) {
    if (this.userGradesUserId === userId) {
      this.userGradesUserId = null;
    } else {
      this.getUserGrades(userId);
      this.userGradesUserId = userId;
    }

    this.editAttendanceForm.reset({ attendance: '' });
    this.editGradeForm.reset({ grade: '' });

    this.userAttendancesUserId = null;
  }

  toggleShowAttendances(userId: number) {
    if (this.userAttendancesUserId === userId) {
      this.userAttendancesUserId = null;
    } else {
      this.getUserAttendances(userId);
      this.userAttendancesUserId = userId;
    }

    this.editAttendanceForm.reset({ attendance: '' });
    this.editGradeForm.reset({ grade: '' });

    this.userGradesUserId = null;
  }

  toggleEditGrade(gradeId: number) {
    if (this.editGradeId === gradeId) {
      this.editGradeId = null;
    } else {
      this.editGradeId = gradeId;
    }

    this.editAttendanceForm.reset({ attendance: '' });
    this.editGradeForm.reset({ grade: '' });
  }


  toggleEditAttendance(attendanceId: number) {
    if (this.editAttendanceId === attendanceId) {
      this.editAttendanceId = null;
    } else {
      this.editAttendanceId = attendanceId;
    }

    this.editAttendanceForm.reset({ attendance: '' });
    this.editGradeForm.reset({ grade: '' });
  }

  submitEditGrade(): void {
    let editedGradeIndex = this.userGrades.findIndex((grade) => grade.id === this.editGradeId);

    this.gradeService.editGrade(this.editGradeId!, this.editGradeForm.value)
      .subscribe({
        next: (response: Grade) => {
          this.notificationService.showSuccess('Grade edited');

          this.userGrades[editedGradeIndex] = response;

          this.editGradeId = null;
          this.editGradeForm.reset({ grade: '' });
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        }
      });
  }

  submitEditAttendance() {
    let editedAttendanceIndex = this.userAttendances.findIndex((attendance) => attendance.id === this.editAttendanceId);

    this.attendanceService.editAttendance(this.editAttendanceId!, this.editAttendanceForm.value)
      .subscribe({
        next: (response) => {
          this.notificationService.showSuccess('Attendance edited');

          this.userAttendances[editedAttendanceIndex] = response;

          this.editAttendanceId = null;
          this.editAttendanceForm.reset({ grade: '' });
        },
        error: (error) => {
          this.notificationService.showError(error.error);
        }
      })
  }

  submitDeleteGrade(gradeId: number) {
    if (confirm("Conformation. Are you sure you want to delete id?")) {
      this.gradeService.deleteGrade(gradeId)
        .subscribe({
          next: (response) => {
            this.notificationService.showSuccess(response);

            const deleteArrayElementIndex = this.userGrades.findIndex((grade) => grade.id == gradeId);

            this.userGrades.splice(deleteArrayElementIndex, 1);
          },
          error: (error) => {
            console.log(error)
            this.notificationService.showError(error.error);
          }
        })
    }
  }

  submitDeleteAttendance(attendanceId: number) {
    if (confirm("Conformation. Are you sure you want to delete it?")) {
      this.attendanceService.deleteAttendance(attendanceId)
        .subscribe({
          next: (response) => {
            this.notificationService.showSuccess(response);

            const deleteArrayElementIndex = this.userAttendances.findIndex((attendance) => attendance.id == attendanceId);

            this.userAttendances.splice(deleteArrayElementIndex, 1);
          },
          error: (error) => {
            this.notificationService.showError(error.error);
          }
        })
    }
  }
}
