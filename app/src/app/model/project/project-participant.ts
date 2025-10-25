import { Project } from './project';
import { User } from '../user';

export enum ProjectRoles {
DEVELOPER = 'DEVELOPER',
TESTER = 'TESTER',
PROJECT_MANAGER = 'PROJECT_MANAGER',
SALES_EXECUTIVE = 'SALES_EXECUTIVE',
PMO_RESPONSIBLE = 'PMO_RESPONSIBLE' }

export class ProjectParticipant {
  id: string | null;
  user: User | null;
  role: ProjectRoles;
  project: Project | null;


  constructor(projectParticipant: Partial<ProjectParticipant> = {}) {
    this.id = projectParticipant?.id || null;
    this.user = projectParticipant?.user || null;
    this.role = projectParticipant?.role || ProjectRoles.DEVELOPER;
    this.project = projectParticipant?.project || null;
  }
}
