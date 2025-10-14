import { Component, OnInit } from '@angular/core';
import { User } from '../../model/user';
import { UserService } from '../../services/user-service.service';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
selector: 'app-user-list',
templateUrl: './user-list.component.html',
styleUrls: []
})
export class UserListComponent implements OnInit {

users: User[] = [];

constructor(private userService: UserService) {
}

  ngOnInit() {
    this.userService.findAll().subscribe(data => {
      this.users = data;
    });
  }
}
