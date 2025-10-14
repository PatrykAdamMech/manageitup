import { Component } from '@angular/core';
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

  onSubmit() {
      this.authService.login(this.loginModel).subscribe({
      next: (res: LoginResponseComponent) => {
        console.log(res);
        if (res.logged == true) {
          // mark authenticated and redirect to protected admin panel
          this.authService.setLoggedIn(true);
          this.router.navigate(['/admin-panel']);
        } else {
          alert('Login failed: ' + res.result);
        }
      },
      error: err => {
        console.error(err);
        alert('Login failed: server error');
      }
    });
  }
}
