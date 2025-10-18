import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProjectStatus } from '../model/project/project-status';
import { API_BASE_URL } from '../tokens';

@Injectable({
  providedIn: 'root'
})
export class ProjectStatusServiceService {

  private statusUrl: string;

  constructor(private http: HttpClient, @Inject(API_BASE_URL) private base: string) {
    this.statusUrl = base + 'status';
  }

  public findAll(): Observable<ProjectStatus[]> {
    return this.http.get<ProjectStatus[]>(this.statusUrl + '/all');
  }

  public findOptions(matcher: string): Observable<ProjectStatus[]> {
      return this.http.get<ProjectStatus[]>(this.usersUrl + '/select', {
        params: { matcher }
      });
  }
}
