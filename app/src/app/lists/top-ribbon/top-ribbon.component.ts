import { Component } from '@angular/core';
import { AuthService } from '../../services/auth-service.service';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-top-ribbon',
  templateUrl: './top-ribbon.component.html',
  styleUrls: ['./top-ribbon.component.css']
})
export class TopRibbonComponent {

  constructor(private location: Location, private router: Router, public authService: AuthService) {}

  goBack() {
    if (window.history.length > 1) {
      this.location.back();
    } else {
      this.router.navigate(['/home']);
    }
  }

  goAdminPanel() {
    this.router.navigate(['/admin-panel']);
  }

  goDashboard() {
    this.router.navigate(['/users/dashboard']);
  }

  logout() {
    this.authService.logout();
  }

  isVisibleUser() {
    return this.authService.isLoggedIn();
  }

  isVisibleAdmin() {
    return this.authService.isAdmin();
  }
}
