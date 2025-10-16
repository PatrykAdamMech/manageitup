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
import { ProjectService } from './services/project-service.service';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatDividerModule } from '@angular/material/divider';
import { AdminPanelComponent } from './sites/admin-panel/admin-panel.component';
import { HomeComponent } from './sites/home/home.component';
import { TopRibbonComponent } from './lists/top-ribbon/top-ribbon.component';
import { ProjectListComponent } from './lists/project-list/project-list.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { ParticipantsListDialogComponent } from './lists/participants-list-dialog/participants-list-dialog.component';
import { UserListDialogComponent } from './lists/user-list-dialog/user-list-dialog.component';
import { UserDisplayComponent } from './model/user-display/user-display.component';
import { WorlflowDialogComponent } from './lists/worlflow-dialog/worlflow-dialog.component';
import { StatusDisplayComponent } from './model/status-display/status-display.component';

@NgModule({
  declarations: [
    AppComponent,
    UserFormComponent,
    UserListComponent,
    LoginFormComponent,
    AdminPanelComponent,
    HomeComponent,
    TopRibbonComponent,
    ProjectListComponent,
    ParticipantsListDialogComponent,
    UserListDialogComponent,
    UserDisplayComponent,
    WorlflowDialogComponent,
    StatusDisplayComponent
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
    MatButtonModule,
    MatTableModule,
    MatDialogModule,
    MatPaginatorModule,
    MatSortModule,
    MatDialogModule,
    MatExpansionModule,
    MatDividerModule
  ],
  providers: [UserService, ProjectService],
  bootstrap: [AppComponent,TopRibbonComponent]
})
export class AppModule { }
