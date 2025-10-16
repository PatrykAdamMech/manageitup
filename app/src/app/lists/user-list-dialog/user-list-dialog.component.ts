import { Component } from '@angular/core';

@Component({
  selector: 'app-user-list-dialog',
  templateUrl: './user-list-dialog.component.html',
  styleUrls: ['./user-list-dialog.component.css']
})
export class UserListDialogComponent {
  columnsToDisplay: string[] = ['id', 'username', 'password', 'email', 'name', 'lastName', 'createdOn', 'lastModified'];
  constructor() {}

}
