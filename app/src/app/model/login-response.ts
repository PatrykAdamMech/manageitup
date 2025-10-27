export type LoginResultEnum = 'NOT_REGISTERED' | 'WRONG_PASSWORD' | 'SUCCESSFUL';

export class LoginResponseComponent {
  logged: boolean;
  accessToken: string;
  tokenType: string;
  result: string;

  constructor(loginResponse: Partial<LoginResponseComponent> = {}) {
    this.logged = loginResponse?.logged || false;
    this.result = loginResponse?.result || 'NOT_REGISTERED';
    this.accessToken = loginResponse?.accessToken || '';
    this.tokenType = loginResponse?.tokenType || '';
  }
}
