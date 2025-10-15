export class ProjectCreateRequest {
  title: string;
  participants: Array<string>;
  workflowId: string;
  ownerId: string;


  constructor(pcr: Partial<ProjectCreateRequest> = {}) {
    this.title = pcr?.title || '';
    this.ownerId = pcr?.ownerId || '';
    this.workflowId = pcr?.workflowId || '';
    this.participants = pcr?.participants || [];
  }
}
