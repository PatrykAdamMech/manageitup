import { Component, Inject, inject, Output, EventEmitter } from '@angular/core';
import { Project } from '../../model/project/project';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-delete-confirm-dialog',
  templateUrl: './delete-confirm-dialog.component.html',
  styleUrls: ['./delete-confirm-dialog.component.css']
})
export class DeleteConfirmDialogComponent {

  readonly dialogRef = inject(MatDialogRef<DeleteConfirmDialogComponent>);
  project: Project;

  constructor(@Inject(MAT_DIALOG_DATA) public data: Project) {
      this.project = data;
  }

  deleteProject() {
    this.dialogRef.close(true);
  }
  cancel() {
    this.dialogRef.close();
  }
}
