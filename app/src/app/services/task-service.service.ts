import { Injectable, Inject, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task, TaskStatus } from '../model/project/task';
import { TaskCreateRequest } from '../model/project/requests/task-create-request';
import { API_BASE_URL } from '../tokens';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private tasksUrl: string;

  constructor(private http: HttpClient, @Inject(API_BASE_URL) private base: string) {
     this.tasksUrl = base + 'tasks';
  }

  public findTasksByProjectId(projectId: string): Observable<Task[]> {
    return this.http.get<Task[]>(this.tasksUrl + '/all/' + projectId);
  }

  public findAllStatuses(): Observable<TaskStatus[]> {
    return this.http.get<TaskStatus[]>(this.tasksUrl + '/statuses/all');
  }

  public save(task: TaskCreateRequest) {
    return this.http.post(this.tasksUrl + '/add', task, { responseType: 'text' });
  }

  public update(task: TaskCreateRequest) {
    console.log('Prepared JSON body for PUT: ' + JSON.stringify(task, null, 4));
    return this.http.put(this.tasksUrl + '/update', task, { responseType: 'text' });
  }

  public delete(id: string) {
    return this.http.delete(this.tasksUrl + '/delete/' + id, { responseType: 'text' });
  }
}
