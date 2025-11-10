import { ProjectParticipant } from './project-participant';
import { ProjectStatus } from './project-status';
import { Workflow } from './workflow';
import { Task } from './task';
import { User } from '../user';

export class Project {
  id: string | null;
  title: string;
  currentStatus: ProjectStatus | null;
  owner: User | null;
  workflow: Workflow | null;
  participants: Array<ProjectParticipant>;
  startDate: Date | null;
  endDate: Date | null;
  tasks: Array<Task>;


  constructor(project: Partial<Project> = {}) {
    this.id = project?.id || null;
    this.title = project?.title || '';
    this.currentStatus = project?.currentStatus || null;
    this.owner = project?.owner || null;
    this.workflow = project?.workflow || null;
    this.participants = project?.participants || [];
    this.startDate = project?.startDate || null;
    this.endDate = project?.endDate || null;
    this.tasks = (project.tasks ?? []).map(t => new Task(t));
  }
}
