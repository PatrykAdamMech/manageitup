import { Component } from '@angular/core';
import { AuthService } from '../../services/auth-service.service';

@Component({
  selector: 'app-top-ribbon',
  templateUrl: './top-ribbon.component.html',
  styleUrls: ['./top-ribbon.component.css']
})
export class TopRibbonComponent {

  constructor(public authService: AuthService) {}

  logout() {
    this.authService.logout();
  }

  isVisible() {
    return localStorage.getItem('manageitup_auth') === 'true'
  }
}
