import { Injectable, Inject } from '@angular/core';
import { LoginRequestComponent } from '../model/login-request';
import { LoginResponseComponent } from '../model/login-response';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { API_BASE_URL } from '../tokens';
import { BehaviorSubject, Observable, map, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedIn$ = new BehaviorSubject<boolean>(false);
  private loginUrl: string;

constructor(private router: Router, private http: HttpClient, @Inject(API_BASE_URL) private base: string) {
    this.loginUrl = base + 'auth/login';
    const token = sessionStorage.getItem('token');
    this.loggedIn$.next(!!token && !this.isExpired(token));
  }

  login(loginRequest: LoginRequestComponent) {
    return this.http.post<LoginResponseComponent>(this.loginUrl, loginRequest).pipe(
      tap( response => {
        const token = response.accessToken ?? '';
        sessionStorage.setItem('token', token);
        this.loggedIn$.next(!this.isExpired(token));
      })
    );
  }

  logout() {
    sessionStorage.removeItem('token');
    this.loggedIn$.next(false);
  }

  get token(): string | null { return sessionStorage.getItem('token'); }

  isLoggedIn() { return this.loggedIn$.value; }

  private isExpired(jwt: string): boolean {
    try {
      const [, payload] = jwt.split('.');
      const { exp } = JSON.parse(atob(payload));
      return Date.now() >= exp * 1000;
    } catch { return true; }
  }

  rehydrateFromStorage() {
    const token = sessionStorage.getItem('token');
    this.loggedIn$.next(!!token && !this.isExpired(token));
  }

}
