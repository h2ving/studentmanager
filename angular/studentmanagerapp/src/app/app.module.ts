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
import { StudentGradeListComponent } from './components/Users/student/student-grade-list/student-grade-list.component';
import { StudentGradeItemComponent } from './components/Users/student/student-grade-item/student-grade-item.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { AdminComponent } from './components/Users/admin/admin.component';
import { ProfessorComponent } from './components/Users/professor/professor.component';
import { LoaderComponent } from './components/loader/loader.component';
import { LoaderInterceptor } from './interceptors/loader.interceptor';
import { ProfileInformationComponent } from './components/User-components/profile-information/profile-information.component';
import { StudentCourseListComponent } from './components/Users/student/student-course-list/student-course-list.component';
import { StudentCourseItemComponent } from './components/Users/student/student-course-item/student-course-item.component';
import { StudentAttendanceListComponent } from './components/Users/student/student-attendance-list/student-attendance-list.component';
import { StudentAttendanceItemComponent } from './components/Users/student/student-attendance-item/student-attendance-item.component';
import { HeaderComponent } from './components/User-components/header/header.component';
import { SessionListComponent } from './components/User-components/session-list/session-list.component';
import { SessionItemComponent } from './components/User-components/session-item/session-item.component';
import { ClockComponent } from './components/User-components/clock/clock.component';

@NgModule({
  declarations: [
    AppComponent,
    StudentComponent,
    LoginComponent,
    FooterComponent,
    StudentGradeListComponent,
    StudentGradeItemComponent,
    PageNotFoundComponent,
    AdminComponent,
    ProfessorComponent,
    LoaderComponent,
    ProfileInformationComponent,
    StudentCourseListComponent,
    StudentCourseItemComponent,
    StudentAttendanceListComponent,
    StudentAttendanceItemComponent,
    HeaderComponent,
    SessionListComponent,
    SessionItemComponent,
    ClockComponent,
  ],
  imports: [
    BrowserModule,
    FontAwesomeModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({ timeOut: 3000, positionClass: 'toast-top-right', preventDuplicates: true })
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
