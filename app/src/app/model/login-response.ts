export type LoginResultEnum = 'NOT_REGISTERED' | 'WRONG_PASSWORD' | 'SUCCESSFUL';

export class LoginResponseComponent {
  logged: boolean;
  result: LoginResultEnum;

  constructor(loginResponse: Partial<LoginResponseComponent> = {}) {
    this.logged = loginResponse?.logged || false;
    this.result = loginResponse?.result || 'NOT_REGISTERED';
  }
}
