import { Component, OnInit, inject } from '@angular/core';
import { Project } from '../../model/project/project';
import { Workflow } from '../../model/project/workflow';
import { UserDisplayComponent } from '../../model/user-display/user-display.component';
import { ProjectService } from '../../services/project-service.service';
import { ParticipantsListDialogComponent } from '../participants-list-dialog/participants-list-dialog.component';
import { WorlflowDialogComponent } from '../worlflow-dialog/worlflow-dialog.component';
import { DeleteConfirmDialogComponent } from '../../forms/delete-confirm-dialog/delete-confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {

  projects: Project[] = [];
  columnsToDisplay: string[] = ['id', 'title', 'owner', 'workflow', 'participants', 'startDate', 'endDate'];
  readonly dialog = inject(MatDialog);

  constructor(private projectService: ProjectService, private router: Router) {}

  ngOnInit() {
    this.projectService.findAllProjects().subscribe( data => {
      this.projects = data;
    });
  }

  click() {
  }

  deleteProject(project: Project, e?: MouseEvent) {
    e?.stopPropagation();
    let dialogRef = this.dialog.open(DeleteConfirmDialogComponent, {
          height: '150px',
          width: '350px',
          data: project
        });

    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.projectService.delete(project.id)?.subscribe(deleted => {
          console.log(deleted);
        });
      }
      window.location.reload();
    });
  }

  openParticipants(project: Project, e?: MouseEvent) {
    e?.stopPropagation();
    let dialogRef = this.dialog.open(ParticipantsListDialogComponent, {
      height: '40%',
      width: '65%',
      data: project
    });
  }

  openWorkflows(project: Project, e?: MouseEvent) {
    e?.stopPropagation();
    let dialogRef = this.dialog.open(WorlflowDialogComponent, {
      height: '40%',
      width: '65%',
      data: project
    });
  }

  openOwner(project: Project, e?: MouseEvent) {
    e?.stopPropagation();
    let dialogRef = this.dialog.open(UserDisplayComponent, {
      height: '40%',
      width: '65%',
      data: project?.owner
    });
  }

  addNewProject() {
    let dialogRef = this.dialog.open(ParticipantsListDialogComponent, {
      height: '400px',
      width: '600px',
    });
  }

  editProject(project: Project, e?: MouseEvent  ) {
    e?.stopPropagation();
    this.router.navigate(['/projects/edit/', project.id]);
  }

}
