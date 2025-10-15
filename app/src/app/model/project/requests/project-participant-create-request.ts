export class ProjectParticipantCreateRequest {
  userId: string;
  role: ProjectParticipant.ProjectRoles;
  projectIds: Array<string>;

  constructor(ppcr: Partial<ProjectParticipantCreateRequest> = {}) {
    this.userId = projectParticipant?.userId || '';
    this.role = projectParticipant?.role || ProjectRoles.DEVELOPER;
    this.projectIds = projectParticipant?.projectIds || [];
  }
}
