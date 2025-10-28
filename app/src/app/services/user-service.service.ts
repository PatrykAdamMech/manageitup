import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { UserCreateRequest } from '../model/user-create-request';
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

  public save(user: UserCreateRequest) {
    return this.http.post(this.usersUrl + '/add', user, { responseType: 'text' });
  }

  public update(user: UserCreateRequest) {
    return this.http.put(this.usersUrl + '/add', user, { responseType: 'text' });
  }

  public delete(id: string | null) {
    if(id == null) return;
    return this.http.delete(this.usersUrl + '/delete/'+id, { responseType: 'text' });
  }
}
