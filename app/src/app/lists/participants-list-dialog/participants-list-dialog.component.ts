import { Component, Inject } from '@angular/core';
import { Project } from '../../model/project/project';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserDisplayComponent } from '../../model/user-display/user-display.component';


@Component({
  selector: 'app-participants-list-dialog',
  templateUrl: './participants-list-dialog.component.html',
  styleUrls: ['./participants-list-dialog.component.css']
})
export class ParticipantsListDialogComponent {
  project: Project;
  columnsToDisplay: string[] = ['id'];

  constructor(@Inject(MAT_DIALOG_DATA) public data: Project) {
    this.project = data;
  }
}
