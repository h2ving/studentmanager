import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { ToastrModule } from 'ngx-toastr';

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
import { StudentCourseTableComponent } from './components/Users/student/student-course-table/student-course-table.component';
import { StudentGradeTableComponent } from './components/Users/student/student-grade-table/student-grade-table.component';
import { StudentAnnouncementListComponent } from './components/Users/student/student-announcement-list/student-announcement-list.component';
import { StudentAttendanceTableComponent } from './components/Users/student/student-attendance-table/student-attendance-table.component';
import { HeaderComponent } from './components/User-components/header/header.component';
import { SessionListComponent } from './components/User-components/session-list/session-list.component';
import { ClockComponent } from './components/User-components/clock/clock.component';

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
    StudentCourseTableComponent,
    StudentAttendanceTableComponent,
    HeaderComponent,
    SessionListComponent,
    ClockComponent,
    StudentAnnouncementListComponent,
  ],
  imports: [
    BrowserModule,
    FontAwesomeModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({ timeOut: 3000, positionClass: 'toast-top-right', preventDuplicates: true, enableHtml: true })
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
