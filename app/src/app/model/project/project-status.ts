export class ProjectStatus {
  id: string | null;
  name: string;
  priority: number;


  constructor(projectStatus: Partial<ProjectStatus> = {}) {
    this.id = projectStatus?.id || null;
    this.name = projectStatus?.name || '';
    this.priority = projectStatus?.priority || 9;
  }
}
