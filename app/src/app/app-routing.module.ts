import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './lists/user-list/user-list.component';
import { ProjectListComponent } from './lists/project-list/project-list.component';
import { UserFormComponent } from './forms/user-form/user-form.component';
import { LoginFormComponent } from './forms/login-form/login-form.component';
import { ProjectFormComponent } from './forms/project-form/project-form.component';
import { AppComponent } from './app.component';
import { HomeComponent } from './sites/home/home.component';
import { AdminPanelComponent } from './sites/admin-panel/admin-panel.component';
import { AuthGuard } from './guards/auth-guard.guard';

const routes: Routes = [
{ path: 'home', component: HomeComponent },
{ path: 'users/list', component: UserListComponent },
{ path: 'users/login', component: LoginFormComponent },
{ path: 'users/add', component: UserFormComponent },
{ path: 'projects/list', component: ProjectListComponent },
{ path: 'projects/edit/:id', component: ProjectFormComponent },
{ path: 'projects/add', component: ProjectFormComponent },
{ path: 'admin-panel', component: AdminPanelComponent, canActivate: [AuthGuard] },
{ path: '', redirectTo: 'home', pathMatch: 'full' },
{ path: '**', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
imports: [RouterModule.forRoot(routes, {
    onSameUrlNavigation: 'reload'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
