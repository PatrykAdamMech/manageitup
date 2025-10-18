import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Project } from '../model/project/project';
import { ProjectCreateRequest } from '../model/project/requests/project-create-request';
import { API_BASE_URL } from '../tokens';

@Injectable()
export class ProjectService {

  private projectsUrl: string;

  constructor(private http: HttpClient, @Inject(API_BASE_URL) private base: string) {
    this.projectsUrl = base + 'projects';
  }

  public findAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(this.projectsUrl + '/all');
  }

}
