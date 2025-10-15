import { ProjectParticipant } from './project-participant';
import { Workflow } from './workflow';
import { User } from '../user';

export class Project {
  id: string | null;
  title: string;
  owner: User | null;
  workflow: Workflow | null;
  participants: Array<ProjectParticipant>;


  constructor(project: Partial<Project> = {}) {
    this.id = project?.id || null;
    this.title = project?.title || '';
    this.owner = project?.owner || null;
    this.workflow = project?.workflow || null;
    this.participants = project?.participants || [];
  }
}
