export class LoginRequestComponent {
  email: string;
  password: string;

  constructor(loginRequest: Partial<LoginRequestComponent> = {}) {
    this.email = loginRequest?.email || '';
    this.password = loginRequest?.password || '';
  }
}
