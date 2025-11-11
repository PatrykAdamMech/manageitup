import { Component, Input, Inject } from '@angular/core';
import { User } from '../user';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
selector: 'app-data-user-display',
templateUrl: './user-data-display.component.html',
styleUrls: ['./user-data-display.component.css']
})
export class UserDataDisplayComponent {

@Input({ required: true }) user!: User;

constructor(@Inject(MAT_DIALOG_DATA) public data: User) {
      this.user = data;
    }
}
