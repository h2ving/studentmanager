import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './components/Users/admin/admin.component';
import { LoginComponent } from './components/login/login.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { ProfessorComponent } from './components/Users/professor/professor.component';
import { StudentComponent } from './components/Users/student/student.component';
import { AnnouncementsComponent } from './components/Announcements/announcements.component';
import { MarksComponent } from './components/Marks/marks.component';
import { AttendancesComponent } from './components/Marks/Attendances/attendances.component';
import { GradesComponent } from './components/Marks/Grades/grades.component';
import { SessionsComponent } from './components/Sessions/sessions.component';
import { CoursesComponent } from './components/Users/admin/Courses/courses.component';
import { CreateCourseComponent } from './components/Users/admin/Create-course/create-course.component';
import { UserListComponent } from './components/Users/admin/User-List/user-list.component';
import { CreateUserComponent } from './components/Users/admin/Create-User/create-user.component';
import { EditCourseComponent } from './components/Users/admin/Edit-course/edit-course.component';
import { Roles } from './models/roles.model';
import { AuthGuard } from './shared/auth.guard';
import { EditUserComponent } from './components/Users/admin/Edit-user/edit-user.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'student/:id', component: StudentComponent, canActivate: [AuthGuard], data: { roles: [Roles.Student] } },
  { path: 'admin/:id', component: AdminComponent, canActivate: [AuthGuard], data: { roles: [Roles.Admin] } },
  { path: 'professor/:id', component: ProfessorComponent, canActivate: [AuthGuard], data: { roles: [Roles.Professor] } },
  { path: 'announcements/professor/:id', component: AnnouncementsComponent, canActivate: [AuthGuard], data: { roles: [Roles.Professor, Roles.Admin] } },
  { path: 'announcements/admin/:id', component: AnnouncementsComponent, canActivate: [AuthGuard], data: { roles: [Roles.Professor, Roles.Admin] } },
  { path: 'courses/admin/:id', component: CoursesComponent, canActivate: [AuthGuard], data: { roles: [Roles.Admin] } },
  { path: 'courses/create/admin/:id', component: CreateCourseComponent, canActivate: [AuthGuard], data: { roles: [Roles.Admin] } },
  { path: 'courses/edit/:courseId/admin/:id', component: EditCourseComponent, canActivate: [AuthGuard], data: { roles: [Roles.Admin] } },
  { path: 'users/admin/:id', component: UserListComponent, canActivate: [AuthGuard], data: { roles: [Roles.Admin] } },
  { path: 'users/create/admin/:id', component: CreateUserComponent, canActivate: [AuthGuard], data: { roles: [Roles.Admin] } },
  { path: 'users/edit/:userId/admin/:id', component: EditUserComponent, canActivate: [AuthGuard], data: { roles: [Roles.Admin] } },
  { path: 'sessions/professor/:id', component: SessionsComponent, canActivate: [AuthGuard], data: { roles: [Roles.Professor, Roles.Admin] } },
  { path: 'sessions/admin/:id', component: SessionsComponent, canActivate: [AuthGuard], data: { roles: [Roles.Professor, Roles.Admin] } },
  { path: 'marks/professor/:id', component: MarksComponent, canActivate: [AuthGuard], data: { roles: [Roles.Professor, Roles.Admin] } },
  { path: 'marks/admin/:id', component: MarksComponent, canActivate: [AuthGuard], data: { roles: [Roles.Professor, Roles.Admin] } },
  { path: 'marks/grades/professor/:id', component: GradesComponent, canActivate: [AuthGuard], data: { roles: [Roles.Professor, Roles.Admin] } },
  { path: 'marks/grades/admin/:id', component: GradesComponent, canActivate: [AuthGuard], data: { roles: [Roles.Professor, Roles.Admin] } },
  { path: 'marks/attendances/professor/:id', component: AttendancesComponent, canActivate: [AuthGuard], data: { roles: [Roles.Professor, Roles.Admin] } },
  { path: 'marks/attendances/admin/:id', component: AttendancesComponent, canActivate: [AuthGuard], data: { roles: [Roles.Professor, Roles.Admin] } },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
