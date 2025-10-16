import { Component, Input } from '@angular/core';
import { User } from '../user';

@Component({
  selector: 'app-user-display',
  templateUrl: './user-display.component.html',
  styleUrls: ['./user-display.component.css']
})
export class UserDisplayComponent {

    @Input({ required: true }) user!: User;

    constructor() {
    }
}
