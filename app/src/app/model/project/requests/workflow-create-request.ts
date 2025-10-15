export class WorkflowCreateRequest {
  name: string;
  statuses: Array<string>;


  constructor(wcr: Partial<WorkflowCreateRequest> = {}}) {
    this.name = wcr?.name || '';
    this.statuses = wcr?.statuses || [];
  }
}
