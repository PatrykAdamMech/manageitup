import { Component, Input } from '@angular/core';
import { ProjectStatus } from '../project/project-status';

@Component({
selector: 'app-status-display',
templateUrl: './status-display.component.html',
styleUrls: ['./status-display.component.css']
})
export class StatusDisplayComponent {

@Input({ required: true }) status!: ProjectStatus;

    constructor() {
    }
}
