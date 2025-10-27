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

    if (req.url.includes('/users/list')) {
      console.debug('INTCPT /users/list token?', !!token);
    }

    if (req.url.includes('/auth/login')) return next.handle(req);

    if (token) {
      req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
    }
    return next.handle(req);
  }


}
