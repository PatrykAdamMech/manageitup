import { Component, OnInit, inject, Input, ChangeDetectionStrategy } from '@angular/core';
import { Project } from '../../model/project/project';
import { Task } from '../../model/project/task';
import { ProjectStatus } from '../../model/project/project-status';
import { ProjectUpdateStatus } from '../../model/project/requests/project-update-status-request';
import { Workflow } from '../../model/project/workflow';
import { UserDisplayComponent } from '../../model/user-display/user-display.component';
import { ProjectService } from '../../services/project-service.service';
import { TaskService } from '../../services/task-service.service';
import { ParticipantsListDialogComponent } from '../participants-list-dialog/participants-list-dialog.component';
import { WorlflowDialogComponent } from '../worlflow-dialog/worlflow-dialog.component';
import { DeleteConfirmDialogComponent } from '../../forms/delete-confirm-dialog/delete-confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router, ActivatedRoute } from '@angular/router';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {

  @Input() displayAdmin: boolean = true;
  @Input() userId: string | null = null;

  statusFormCtrl = new FormControl<string | null>(null, Validators.required);

  editable: boolean = false;
  projects: Project[] = [];
  statusesToDisplay: ProjectStatus[] = [];
  columnsToDisplay: string[] = ['id', 'title', 'currentStatus', 'owner', 'workflow', 'participants', 'startDate', 'endDate', 'tasks'];
  columnsToDisplayTasks: string[] = ['id', 'name', 'assignee', 'status', 'description'];
  readonly dialog = inject(MatDialog);
  editedProjectId: string | null = null;
  expanded: boolean = false;
  expandedProjectIds: Array<string> = [];

  constructor(private projectService: ProjectService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {

    if(this.userId) {
      this.projectService.findByOwnerId(this.userId).subscribe( data => {
        this.projects = data;
        console.log('project: ' + JSON.stringify(this.projects, null, 4));
      });
    } else {
      this.projectService.findAllProjects().subscribe( data => {
        this.projects = data;
      });
    }
  }

  deleteProject(project: Project, e?: MouseEvent) {
    e?.stopPropagation();
    let dialogRef = this.dialog.open(DeleteConfirmDialogComponent, {
          height: '150px',
          width: '350px',
          data: 'Delete project ' + project.title + '?',
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

  setEditableStatus(project: Project, e?: MouseEvent  ) {
    e?.stopPropagation();
    this.editable = true;
    this.editedProjectId = project.id ?? null;
    this.statusesToDisplay = project.workflow?.statuses ?? [];
    this.statusFormCtrl.setValue(project.currentStatus?.id ?? null, { emitEvent: false });
  }

  saveStatus(project: Project) {
    const statusId = this.statusFormCtrl.value;
    if (!statusId) return;
    if(!this.editedProjectId) return;

    const createRequest = new ProjectUpdateStatus();
    createRequest.projectId = this.editedProjectId;
    createRequest.statusId = statusId;

    this.projectService.updateStatus(createRequest).subscribe(result => {
      console.log('Updated: ' + result);
    });
    window.location.reload();
  }

  onStatusPicked(project: Project, e?: MouseEvent) {
    // possible extension
  }

  cancelStatusEdit() {
    this.editable = false;
    this.editedProjectId = '';
    this.statusFormCtrl.reset(null, { emitEvent: false });
  }

  expandProject(project: Project) {
    const index = this.expandedProjectIds.indexOf(project.id ?? '');
    if(index == -1) {
      this.expandedProjectIds.push(project.id ?? '');
    } else {
      this.expandedProjectIds = this.expandedProjectIds.filter(id => id != project.id);
    }
  }

}
