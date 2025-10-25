import { Component, Input } from '@angular/core';
import { ProjectStatus } from '../../model/project/project-status';

@Component({
  selector: 'app-project-status-list',
  templateUrl: './project-status-list.component.html',
  styleUrls: ['./project-status-list.component.css']
})
export class ProjectStatusListComponent {
  @Input() statuses? : Partial<ProjectStatus>[];
}
