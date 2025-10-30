import { Workflow } from '../workflow';
import { ProjectStatus } from '../project-status';
import { ProjectParticipantCreateRequest } from './project-participant-create-request';

export class ProjectCreateRequest {
  id: string | null;
  title: string;
  participants: Array<ProjectParticipantCreateRequest>;
  workflow: Workflow | null;
  ownerId: string;
  startDate: Date | string;
  endDate: Date | string;
  statusId: string | null;

  constructor(pcr: Partial<ProjectCreateRequest> = {}) {
    this.id = pcr?.id || null;
    this.title = pcr?.title || '';
    this.ownerId = pcr?.ownerId || '';
    this.workflow = pcr?.workflow || null;
    this.participants = pcr?.participants || [];
    this.startDate = pcr?.startDate || new Date('2000-01-01');
    this.endDate = pcr?.endDate || new Date('2000-01-01');
    this.statusId = pcr?.statusId || null;
  }
}
