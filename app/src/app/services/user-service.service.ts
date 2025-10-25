import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { UserOption } from '../model/user-option';
import { LoginRequestComponent } from '../model/login-request';
import { LoginResponseComponent } from '../model/login-response';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../tokens';

@Injectable()
export class UserService {

private usersUrl: string;

constructor(private http: HttpClient, @Inject(API_BASE_URL) private base: string) {
    this.usersUrl = base + 'users';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl + '/all');
  }

  public findById(id: string): Observable<User> {
    return this.http.get<User>(this.usersUrl + '/all/'+id);
  }

  public findOptions(matcher: string): Observable<UserOption[]> {
      return this.http.get<UserOption[]>(this.usersUrl + '/select', {
        params: { matcher }
      });
  }

  public save(user: User) {
    return this.http.post<User>(this.usersUrl + '/add', user);
  }
}
