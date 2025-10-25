import { ProjectRoles } from '../project-participant';

export class ProjectParticipantCreateRequest {
  userId: string | undefined;
  role: ProjectRoles;
  projectId: string | null;

  constructor(ppcr: Partial<ProjectParticipantCreateRequest> = {}) {
    this.userId = ppcr?.userId;
    this.role = ppcr?.role || ProjectRoles.DEVELOPER;
    this.projectId = ppcr?.projectId || null;
  }
}
