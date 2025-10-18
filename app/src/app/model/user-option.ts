export class UserOption {
  id: string | null;
  label: string | null;

constructor(user: Partial<UserOption> = {}) {
    this.id = user?.id || null;
    this.label = user?.label || '';
  }
}
