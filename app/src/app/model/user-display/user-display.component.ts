import { Component, Input, Inject } from '@angular/core';
import { User } from '../user';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-user-display',
  templateUrl: './user-display.component.html',
  styleUrls: ['./user-display.component.css']
})
export class UserDisplayComponent {

    @Input({ required: true }) user!: User;

    constructor(@Inject(MAT_DIALOG_DATA) public data: User) {
      this.user = data;
    }
}
