import { Workflow } from '../workflow';
import { ProjectParticipantCreateRequest } from './project-participant-create-request';

export class ProjectCreateRequest {
  title: string;
  participants: Array<ProjectParticipantCreateRequest>;
  workflow: Workflow | null;
  ownerId: string;
  startDate: Date;
  endDate: Date;

  constructor(pcr: Partial<ProjectCreateRequest> = {}) {
    this.title = pcr?.title || '';
    this.ownerId = pcr?.ownerId || '';
    this.workflow = pcr?.workflow || null;
    this.participants = pcr?.participants || [];
    this.startDate = pcr?.startDate || new Date('2000-01-01');
    this.endDate = pcr?.endDate || new Date('2000-01-01');
  }
}
