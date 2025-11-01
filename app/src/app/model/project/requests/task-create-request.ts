import { ProjectParticipant } from '../project-participant';
import { Task, TaskStatus } from '../task';

export class TaskCreateRequest {
  id: string | null;
  name: string | null;
  assigneeId: string | null;
  status: TaskStatus | null;
  description: string | null;
  projectId: string | null;

  constructor(task Partial<TaskCreateRequest> = {}) {
    this.id = task?.id || null;
    this.name = task?.name || null;
    this.assignee = task?.assignee || null;
    this.status = task?.status || TaskStatus.NOT_ASSIGNED;
    this.description = task?.description || null;
    this.projectId = task?.projectId || null;
  }
}
