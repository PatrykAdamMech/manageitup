import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { LoginRequestComponent } from '../model/login-request';
import { LoginResponseComponent } from '../model/login-response';
import { Observable } from 'rxjs';

@Injectable()
export class UserService {

private usersUrl: string;

constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8081/users';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl + '/all');
  }

  public save(user: User) {
    return this.http.post<User>(this.usersUrl + '/add', user);
  }
}
