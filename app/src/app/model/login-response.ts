import { UserRoles } from './user';

export class LoginResponseComponent {
  accessToken: string;
  tokenType: string;
  role: UserRoles;
  userId: string;

  constructor(loginResponse: Partial<LoginResponseComponent> = {}) {
    this.role = loginResponse?.role || UserRoles.USER;
    this.userId = loginResponse?.userId || '';
    this.accessToken = loginResponse?.accessToken || '';
    this.tokenType = loginResponse?.tokenType || '';
  }
}
