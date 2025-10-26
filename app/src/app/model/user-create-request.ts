import { User, UserRoles } from './user';

export class UserCreateRequest {
  id: string | null;
  username: string;
  password: string;
  email: string;
  name: string;
  lastName: string;
  role: UserRoles;

  constructor(user: Partial<User> = {}) {
    this.id = user?.id || null;
    this.username = user?.username || '';
    this.password = user?.password || '';
    this.email = user?.email || '';
    this.name = user?.name || '';
    this.lastName = user?.lastName || '';
    this.role = user?.role || UserRoles.USER;
  }
}
