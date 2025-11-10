import { ProjectParticipant } from '../project-participant';
import { Task } from '../task';

export class TaskCreateRequest {
  id: string | null;
  name: string | null;
  assigneeId: string | null;
  status: string | null;
  description: string | null;
  projectId: string | null;

  constructor(task: Partial<TaskCreateRequest> = {}) {
    this.id = task?.id || null;
    this.name = task?.name || null;
    this.assigneeId = task?.assigneeId || null;
    this.status = task?.status || null;
    this.description = task?.description || null;
    this.projectId = task?.projectId || null;
  }
}
