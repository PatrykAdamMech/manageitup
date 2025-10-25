import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProjectStatus } from '../model/project/project-status';
import { ProjectStatusOption } from '../model/project/project-status-option';
import { API_BASE_URL } from '../tokens';

@Injectable({
  providedIn: 'root'
})
export class ProjectStatusService {

  private statusUrl: string;

  constructor(private http: HttpClient, @Inject(API_BASE_URL) private base: string) {
    this.statusUrl = base + 'status';
  }

  public findAll(): Observable<ProjectStatus[]> {
    return this.http.get<ProjectStatus[]>(this.statusUrl + '/all');
  }

  public findOptions(matcher: string): Observable<ProjectStatusOption[]> {
      return this.http.get<ProjectStatusOption[]>(this.statusUrl + '/select', {
        params: { matcher }
      });
  }

  public findById(id: string): Observable<ProjectStatus> {
      return this.http.get<ProjectStatus>(this.statusUrl + '/all/'+id);
  }
}
