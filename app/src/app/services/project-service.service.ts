import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Project } from '../model/project/project';
import { ProjectCreateRequest } from '../model/project/requests/project-create-request';

@Injectable()
export class ProjectService {

  private projectsUrl: string;

  constructor(private http: HttpClient) {
    this.projectsUrl = 'http://localhost:8081/projects';
  }

  public findAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(this.projectsUrl + '/all');
  }

}
