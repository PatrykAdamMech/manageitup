import { ProjectStatus } from './project-status';

export class Workflow {
  id: string | null;
  name: string;
  statuses: Array<ProjectStatus>;


  constructor(workflow: Partial<Workflow> = {}) {
    this.id = workflow?.id || null;
    this.name = workflow?.name || '';
    this.statuses = workflow?.statuses || [];
  }
}
