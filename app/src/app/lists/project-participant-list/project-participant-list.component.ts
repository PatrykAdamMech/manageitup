import { Component, Input, inject } from '@angular/core';
import { ProjectParticipant } from '../../model/project/project-participant';
@Component({
  selector: 'app-project-participant-list',
  templateUrl: './project-participant-list.component.html',
  styleUrls: ['./project-participant-list.component.css']
})
export class ProjectParticipantListComponent {

    @Input() participants? : Partial<ProjectParticipant>[];
}
