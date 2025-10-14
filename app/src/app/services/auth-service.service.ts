import { Injectable } from '@angular/core';
import { LoginRequestComponent } from '../model/login-request';
import { LoginResponseComponent } from '../model/login-response';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
providedIn: 'root'
})
export class AuthService {
  private logged = false;

  private loginUrl="http://localhost:8081/auth/login";

  constructor(private router: Router, private http: HttpClient) {}

  login(loginRequest: LoginRequestComponent) {
      return this.http.post<LoginResponseComponent>(this.loginUrl, loginRequest);
  }

  isLoggedIn(): boolean {
    return this.logged || localStorage.getItem('manageitup_auth') === 'true';
  }

  setLoggedIn(value: boolean) {
    this.logged = value;
    localStorage.setItem('manageitup_auth', value ? 'true' : 'false');
  }

  logout() {
    this.router.navigate(['/users/login']);
    this.setLoggedIn(false);
  }
}
