import { Component, Input, Output, EventEmitter, inject, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { ProjectStatus } from '../../model/project/project-status';
import { ProjectStatusService } from  '../../services/project-status-service.service';
import { ProjectStatusOption } from '../../model/project/project-status-option';

@Component({
  selector: 'app-project-status-form',
  templateUrl: './project-status-form.component.html',
  styleUrls: ['./project-status-form.component.css']
})
export class ProjectStatusFormComponent {

}
