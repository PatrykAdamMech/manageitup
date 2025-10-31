import { Injectable, Inject } from '@angular/core';
import { LoginRequestComponent } from '../model/login-request';
import { LoginResponseComponent } from '../model/login-response';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { API_BASE_URL } from '../tokens';
import { BehaviorSubject, Observable, map, tap } from 'rxjs';
import { UserRoles } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedIn$ = new BehaviorSubject<boolean>(false);
  private loginUrl: string;
  private userId: string | null = null;
  private role: UserRoles = UserRoles.USER;

constructor(private router: Router, private http: HttpClient, @Inject(API_BASE_URL) private base: string) {
    this.loginUrl = base + 'auth/login';
    const token = sessionStorage.getItem('token');
    this.loggedIn$.next(!!token && !this.isExpired(token));
  }

  login(loginRequest: LoginRequestComponent) {
    return this.http.post<LoginResponseComponent>(this.loginUrl, loginRequest).pipe(
      tap( response => {
        const token = response.accessToken ?? '';
        this.loggedIn$.next(!this.isExpired(token));
        this.role = response?.role;
        this.userId = response?.userId;
        sessionStorage.setItem('token', token);
        sessionStorage.setItem('userId', this.userId ?? '');
        sessionStorage.setItem('role', this.role);
      })
    );
  }

  logout() {
    if(sessionStorage.getItem('token') != null) this.router.navigateByUrl('/users/login');
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('userId');
    sessionStorage.removeItem('role');
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
    this.userId = sessionStorage.getItem('userId');
    const rawRole = sessionStorage.getItem('role') as keyof typeof UserRoles | null;
    this.role = rawRole ? UserRoles[rawRole] : UserRoles.USER;
    this.loggedIn$.next(!!token && !this.isExpired(token));
  }

  getUserId(): string | null {
    return this.userId;
  }

  getUserRole(): UserRoles {
    return this.role;
  }

  isAdmin(): boolean {
    return sessionStorage.getItem('role') == 'SYSTEM_ADMIN';
  }
}
