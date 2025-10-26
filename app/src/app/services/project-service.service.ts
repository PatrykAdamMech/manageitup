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

  public save(pcr: ProjectCreateRequest) {
    console.log('Prepared POST body (service): ' + JSON.stringify(pcr, null, 4));
    return this.http.post(this.projectsUrl + '/add/full', pcr, { responseType: 'text' });
  }

  public delete(id: string | null) {
    if(id == null) return;
    return this.http.delete(this.projectsUrl + '/delete/'+id);
  }

}
