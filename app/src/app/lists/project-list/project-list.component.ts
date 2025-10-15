import { Component, OnInit } from '@angular/core';
import { Project } from '../../model/project/project';
import { ProjectService } from '../../services/project-service.service';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {

  projects: Project[] = [];
  columnsToDisplay: string[] = ['id', 'title', 'owner', 'workflow', 'participants'];

  constructor(private projectService: ProjectService) {}

  ngOnInit() {
    this.projectService.findAllProjects().subscribe( data => {
      this.projects = data;
    });
  }

  click() {
    alert('Clicked!');
  }

}
