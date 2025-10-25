export class ProjectStatusOption {
  id: string | null;
  label: string | null;

  constructor(status: Partial<ProjectStatusOption> = {}) {
      this.id = status?.id || null;
      this.label = status?.label || '';
  }
}
