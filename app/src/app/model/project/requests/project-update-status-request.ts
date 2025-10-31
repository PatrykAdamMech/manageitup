export class ProjectUpdateStatus {
  projectId: string | null;
  statusId: string | null;

  constructor( pus: Partial<ProjectUpdateStatus> = {}) {
    this.projectId = pus.projectId ?? null;
    this.statusId = pus.statusId ?? null;
  }
}
