import { Project } from './project';
import { User } from '../user';

export type ProjectRoles = 'DEVELOPER' | 'TESTER' | 'PROJECT_MANAGER' | 'SALES_EXECUTIVE' | 'PMO_RESPONSIBLE';

export class ProjectParticipant {
  id: string | null;
  user: User | null;
  role: ProjectRoles;
  projects: Array<Project>;


  constructor(projectParticipant: Partial<ProjectParticipant> = {}) {
    this.id = projectParticipant?.id || null;
    this.user = projectParticipant?.user || null;
    this.role = projectParticipant?.role || 'DEVELOPER';
    this.projects = projectParticipant?.projects || [];
  }
}
