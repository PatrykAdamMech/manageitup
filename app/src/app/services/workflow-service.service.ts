import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Workflow } from '../model/project/workflow';
import { WorkflowOption } from '../model/project/workflow-option';
import { API_BASE_URL } from '../tokens';

@Injectable()
export class WorkflowService {

  private workflowUrl: string;

  constructor(private http: HttpClient, @Inject(API_BASE_URL) private base: string) {
    this.workflowUrl = base + 'workflows';
  }

  public findAll(): Observable<Workflow[]> {
    return this.http.get<Workflow[]>(this.workflowUrl + '/all');
  }

  public findById(id: string): Observable<Workflow> {
    return this.http.get<Workflow>(this.workflowUrl + '/all/'+id);
  }

  public findOptions(matcher: string): Observable<WorkflowOption[]> {
      return this.http.get<WorkflowOption[]>(this.workflowUrl + '/select', {
        params: { matcher }
      });
  }
}
