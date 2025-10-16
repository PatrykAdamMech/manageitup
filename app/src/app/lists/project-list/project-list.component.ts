import { Component, OnInit, inject } from '@angular/core';
import { Project } from '../../model/project/project';
import { Workflow } from '../../model/project/workflow';
import { ProjectService } from '../../services/project-service.service';
import { ParticipantsListDialogComponent } from '../participants-list-dialog/participants-list-dialog.component';
import { WorlflowDialogComponent } from '../worlflow-dialog/worlflow-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {

  projects: Project[] = [];
  columnsToDisplay: string[] = ['id', 'title', 'owner', 'workflow', 'participants'];
  readonly dialog = inject(MatDialog);

  constructor(private projectService: ProjectService) {}

  ngOnInit() {
    this.projectService.findAllProjects().subscribe( data => {
      this.projects = data;
    });
  }

  click() {
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

  addNewProject() {
    let dialogRef = this.dialog.open(ParticipantsListDialogComponent, {
      height: '400px',
      width: '600px',
    });
  }

}
