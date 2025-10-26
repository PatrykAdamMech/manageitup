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
import { WorkflowService } from './services/workflow-service.service';
import { ProjectStatusService } from  './services/project-status-service.service';
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
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatOptionModule } from '@angular/material/core';
import { AdminPanelComponent } from './sites/admin-panel/admin-panel.component';
import { HomeComponent } from './sites/home/home.component';
import { TopRibbonComponent } from './lists/top-ribbon/top-ribbon.component';
import { ProjectListComponent } from './lists/project-list/project-list.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { ParticipantsListDialogComponent } from './lists/participants-list-dialog/participants-list-dialog.component';
import { UserListDialogComponent } from './lists/user-list-dialog/user-list-dialog.component';
import { UserDisplayComponent } from './model/user-display/user-display.component';
import { WorlflowDialogComponent } from './lists/worlflow-dialog/worlflow-dialog.component';
import { StatusDisplayComponent } from './model/status-display/status-display.component';
import { ProjectFormComponent } from './forms/project-form/project-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { WorkflowFormComponent } from './forms/workflow-form/workflow-form.component';
import { ProjectStatusFormComponent } from './forms/project-status-form/project-status-form.component';
import { ProjectParticipantFormComponent } from './forms/project-participant-form/project-participant-form.component';
import { API_BASE_URL } from './tokens';
import { ProjectParticipantListComponent } from './lists/project-participant-list/project-participant-list.component';
import { ProjectStatusListComponent } from './lists/project-status-list/project-status-list.component';
import { CustomDateAdapter } from './custom-date-adapter';
import { DateAdapter } from '@angular/material/core';
import { DeleteConfirmDialogComponent } from './forms/delete-confirm-dialog/delete-confirm-dialog.component';

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
    StatusDisplayComponent,
    ProjectFormComponent,
    WorkflowFormComponent,
    ProjectStatusFormComponent,
    ProjectParticipantFormComponent,
    ProjectParticipantListComponent,
    ProjectStatusListComponent,
    DeleteConfirmDialogComponent
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
    MatDividerModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatProgressSpinnerModule,
    MatOptionModule,
    MatDatepickerModule,
    MatNativeDateModule
    ],
  providers: [UserService, ProjectService, WorkflowService, ProjectStatusService,
    { provide: API_BASE_URL, useValue: 'http://localhost:8081/' },
    { provide: DateAdapter, useClass: CustomDateAdapter }
  ],
  bootstrap: [AppComponent,TopRibbonComponent]
})
export class AppModule { }
