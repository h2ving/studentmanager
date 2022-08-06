import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { ToastrModule } from 'ngx-toastr';
import { NgxChartsModule } from '@swimlane/ngx-charts';

import { AppComponent } from './app.component';
import { StudentComponent } from './components/Users/student/student.component';
import { LoginComponent } from './components/login/login.component';
import { FooterComponent } from './components/User-components/footer/footer.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { AdminComponent } from './components/Users/admin/admin.component';
import { ProfessorComponent } from './components/Users/professor/professor.component';
import { LoaderComponent } from './components/loader/loader.component';
import { LoaderInterceptor } from './interceptors/loader.interceptor';
import { ProfileInformationComponent } from './components/User-components/profile-information/profile-information.component';
import { CourseTableComponent } from './components/User-components/course-table/course-table.component';
import { StudentGradeTableComponent } from './components/Users/student/student-grade-table/student-grade-table.component';
import { StudentAnnouncementListComponent } from './components/Users/student/student-announcement-list/student-announcement-list.component';
import { StudentAttendanceTableComponent } from './components/Users/student/student-attendance-table/student-attendance-table.component';
import { HeaderComponent } from './components/User-components/header/header.component';
import { SessionListComponent } from './components/User-components/session-list/session-list.component';
import { ClockComponent } from './components/User-components/clock/clock.component';
import { AnnouncementsComponent } from './components/Announcements/announcements.component';
import { MarksComponent } from './components/Marks/marks.component';
import { SessionsComponent } from './components/Sessions/sessions.component';
import { ConfirmationDialogModalComponent } from './components/confirmation-dialog-modal/confirmation-dialog-modal.component';
import { CalendarsModule } from './components/calendar/module';
import { GradesComponent } from './components/Marks/Grades/grades.component';
import { AttendancesComponent } from './components/Marks/Attendances/attendances.component';
import { CoursesComponent } from './components/Users/admin/Courses/courses.component';
import { ChartsComponent } from './components/charts/charts.component';
import { CourseUsersChartComponent } from './components/charts/course-users-chart/course-users-chart.component';
import { UsersChartComponent } from './components/charts/users-chart/users-chart.component';
import { CourseChartComponent } from './components/charts/course-chart/course-chart.component';
import { AttendanceChartComponent } from './components/charts/attendance-chart/attendance-chart.component';
import { CourseGradesChartComponent } from './components/charts/course-grades-chart/course-grades-chart.component';
import { CreateCourseComponent } from './components/Users/admin/Create-course/create-course.component';
import { UserListComponent } from './components/Users/admin/User-List/user-list.component';
import { CreateUserComponent } from './components/Users/admin/Create-User/create-user.component';
import { EditCourseComponent } from './components/Users/admin/Edit-course/edit-course.component';
import { EditUserComponent } from './components/Users/admin/Edit-user/edit-user.component';

@NgModule({
  declarations: [
    AppComponent,
    StudentComponent,
    LoginComponent,
    FooterComponent,
    StudentGradeTableComponent,
    PageNotFoundComponent,
    AdminComponent,
    ProfessorComponent,
    LoaderComponent,
    ProfileInformationComponent,
    CourseTableComponent,
    StudentAttendanceTableComponent,
    HeaderComponent,
    SessionListComponent,
    ClockComponent,
    StudentAnnouncementListComponent,
    AnnouncementsComponent,
    MarksComponent,
    SessionsComponent,
    ConfirmationDialogModalComponent,
    GradesComponent,
    AttendancesComponent,
    CoursesComponent,
    ChartsComponent,
    CourseUsersChartComponent,
    UsersChartComponent,
    CourseChartComponent,
    AttendanceChartComponent,
    CourseGradesChartComponent,
    CreateCourseComponent,
    UserListComponent,
    CreateUserComponent,
    EditCourseComponent,
    EditUserComponent,
  ],
  imports: [
    BrowserModule,
    FontAwesomeModule,
    AppRoutingModule,
    HttpClientModule,
    NgxChartsModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({ timeOut: 3000, positionClass: 'toast-top-right', preventDuplicates: true, enableHtml: true }),
    CalendarsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
