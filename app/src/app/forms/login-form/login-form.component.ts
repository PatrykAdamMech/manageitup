import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequestComponent } from '../../model/login-request';
import { LoginResponseComponent } from '../../model/login-response';
import { AuthService } from '../../services/auth-service.service';

@Component({
selector: 'app-login-form',
templateUrl: './login-form.component.html',
styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {
loginModel = new LoginRequestComponent();

constructor(private router: Router, private authService: AuthService) {}

  ngOnInit() {
    if(this.authService.isLoggedIn()) {
      this.router.navigate(['users/dashboard'])
    }
  }

  onSubmit() {
      this.authService.login(this.loginModel).subscribe({
      next: () => this.router.navigate(['users/dashboard']),
      error: error => {
        console.error(error);
        alert('Login failed: server error!');
      }
    });
  }

  isAdmin() {
    return true;
  }
}
