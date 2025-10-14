import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { UserListComponent } from './lists/user-list/user-list.component';
import { UserFormComponent } from './forms/user-form/user-form.component';
import { LoginFormComponent } from './forms/login-form/login-form.component';
import { UserService } from './services/user-service.service';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { AdminPanelComponent } from './sites/admin-panel/admin-panel.component';
import { HomeComponent } from './sites/home/home.component';
import { TopRibbonComponent } from './lists/top-ribbon/top-ribbon.component';
import { ProjectListComponent } from './lists/project-list/project-list.component';

@NgModule({
  declarations: [
    AppComponent,
    UserFormComponent,
    UserListComponent,
    LoginFormComponent,
    AdminPanelComponent,
    HomeComponent,
    TopRibbonComponent,
    ProjectListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatToolbarModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent,TopRibbonComponent]
})
export class AppModule { }
