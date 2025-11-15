import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequestComponent } from '../../model/login-request';
import { LoginResponseComponent } from '../../model/login-response';
import { AuthService } from '../../services/auth-service.service';
import { FormGroup, FormControl, ValidatorFn, Validators } from '@angular/forms';

@Component({
selector: 'app-login-form',
templateUrl: './login-form.component.html',
styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {
loginModel = new LoginRequestComponent();

mainFormGroup = new FormGroup({
  email:  new FormControl<string | null>(null, [Validators.required, Validators.email]),
  password: new FormControl<string | null>(null, [Validators.required])
});

constructor(private router: Router, private authService: AuthService) {}

  ngOnInit() {
    if(this.authService.isLoggedIn()) {
      this.router.navigate(['users/dashboard'])
    }
  }

  onSubmit() {
      if(this.mainFormGroup.invalid) {
        alert('Please fill in all mandatory field!');
        return;
      }
      this.authService.login(this.loginModel).subscribe({
      next: () => this.router.navigate(['users/dashboard']),
      error: error => {
        console.error(error);
        if(error.status == 404) alert('No user for provided email address could be found!');
        else if(error.status == 401) alert('Wrong password!');
        else alert('An unexpected error occured! ' + error);
      }
    });
  }

  isAdmin() {
    return true;
  }
}
