import { Injectable, Inject } from '@angular/core';
import { LoginRequestComponent } from '../model/login-request';
import { LoginResponseComponent } from '../model/login-response';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { API_BASE_URL } from '../tokens';

@Injectable({
providedIn: 'root'
})
export class AuthService {
  private logged = false;

  private loginUrl: string;

  constructor(private router: Router, private http: HttpClient, @Inject(API_BASE_URL) private base: string) {
    this.loginUrl = base + 'auth/login';
  }

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
