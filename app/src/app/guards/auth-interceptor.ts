import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../services/auth-service.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
constructor(private auth: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {

    const token = this.auth.token;

    console.debug('Intercepting request to:',req.url);
    console.debug('Token present?:', !!token);
    console.debug('Is logged in:', this.auth.isLoggedIn());

    if (req.url.includes('/auth/login')) {
      console.debug('Skipping auth!');
      return next.handle(req);
    }
    if (token && this.auth.isLoggedIn()) {
      console.debug('Adding auth header');
      req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
    } else if (token && !this.auth.isLoggedIn()) {
      console.warn('Token exists but is expired or invalid');
    }
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('HTTP Error: ', error.status, error.message);
        if(error.status == 401) {
          console.error('401 Unauthorized');
          this.auth.logout();
        }
        return throwError(() => error);
      })
    );
  }


}
