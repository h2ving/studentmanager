import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './components/Users/admin/admin.component';
import { LoginComponent } from './components/login/login.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { ProfessorComponent } from './components/Users/professor/professor.component';
import { StudentComponent } from './components/Users/student/student.component';
import { Roles } from './models/roles.model';
import { AuthGuard } from './shared/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'student/:id', component: StudentComponent, canActivate: [AuthGuard], data: { roles: [Roles.Student] } },
  { path: 'admin/:id', component: AdminComponent, canActivate: [AuthGuard], data: { roles: [Roles.Admin] } },
  { path: 'professor/:id', component: ProfessorComponent, canActivate: [AuthGuard], data: { roles: [Roles.Professor] } },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
