import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth-service.service';
import { ProjectService } from '../../services/project-service.service';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent {

  loggedUserId: string | null;
  isAdmin: boolean;

  constructor(private authService: AuthService, private projectService: ProjectService) {
    this.loggedUserId = authService.getUserId();
    this.isAdmin = authService.isAdmin();
  }

}
