import { ProjectParticipant } from './project-participant';

export type TaskStatusName = 'NOT_ASSIGNED' | 'ASSIGNED' | 'IN_PROGRESS' | 'CLOSED';

export interface TaskStatus {
  name: TaskStatusName;
  label: string;
}

export class Task {
  id: string | null;
  name: string | null;
  assignee: ProjectParticipant | null;
  status: TaskStatusName | null;
  statusLabel: string | null;
  description: string | null;
  projectId: string | null;

  constructor(task: Partial<Task> = {}) {
    this.id = task?.id || null;
    this.name = task?.name || null;
    this.assignee = task?.assignee || null;
    this.status = task?.status || null;
    this.statusLabel = task?.statusLabel || '';
    this.description = task?.description || null;
    this.projectId = task?.projectId || null;
  }

}
