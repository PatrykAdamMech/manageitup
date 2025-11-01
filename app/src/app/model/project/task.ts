import { ProjectParticipant } from './project-participant';

export enum TaskStatus {
  NOT_ASSIGNED = 'NOT_ASSIGNED',
  ASSIGNED = 'ASSIGNED',
  IN_PROGRESS = 'IN_PROGRESS',
  CLOSED = 'CLOSED'
}

export class Task {

  id: string | null;
  name: string | null;
  assignee: ProjectParticipant | null;
  status: TaskStatus | null;
  description: string | null;
  projectId: string | null;

  constructor(task: Partial<Task> = {}) {
    this.id = task?.id || null;
    this.name = task?.name || null;
    this.assignee = task?.assignee || null;
    this.status = task?.status || TaskStatus.NOT_ASSIGNED;
    this.description = task?.description || null;
    this.projectId = task?.projectId || null;
  }

}
