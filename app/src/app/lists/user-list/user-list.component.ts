import { Component, OnInit, inject } from '@angular/core';
import { User } from '../../model/user';
import { UserService } from '../../services/user-service.service';
import { DeleteConfirmDialogComponent } from '../../forms/delete-confirm-dialog/delete-confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: []
})
export class UserListComponent implements OnInit {

  users: User[] = [];
  readonly dialog = inject(MatDialog);
  columnsToDisplay: string[] = ['id', 'username', 'password', 'email', 'name', 'lastName', 'createdOn', 'lastModified'];

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.findAll().subscribe(data => {
      this.users = data;
    });
  }

  deleteUser(user: User, e?: MouseEvent) {
    e?.stopPropagation();
    let dialogRef = this.dialog.open(DeleteConfirmDialogComponent, {
          height: '150px',
          width: '350px',
          data: user
        });

    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.userService.delete(user.id)?.subscribe(deleted => {
          console.log(deleted);
        });
      }
      window.location.reload();
    });
  }
}
