import { Component, Inject } from '@angular/core';
import { Workflow } from '../../model/project/workflow';
import { Project } from '../../model/project/project';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-worlflow-dialog',
  templateUrl: './worlflow-dialog.component.html',
  styleUrls: ['./worlflow-dialog.component.css']
})
export class WorlflowDialogComponent {
  project: Project;
  columnsToDisplay: string[] = ['id'];

  constructor(@Inject(MAT_DIALOG_DATA) public data: Project) {
    this.project = data;
  }
}
