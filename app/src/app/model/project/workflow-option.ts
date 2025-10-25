export class WorkflowOption {
  id: string | null;
  label: string | null;

constructor(user: Partial<WorkflowOption> = {}) {
    this.id = user?.id || null;
    this.label = user?.label || '';
  }
}
