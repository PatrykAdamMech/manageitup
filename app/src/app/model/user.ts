export class User {
  id: string | null;
  username: string;
  password: string;
  email: string;
  name: string;
  lastName: string;
  createdOn: string;
  lastModified: string;

constructor(user: Partial<User> = {}) {
    this.id = user?.id || null;
    this.username = user?.username || '';
    this.password = user?.password || '';
    this.email = user?.email || '';
    this.name = user?.name || '';
    this.lastName = user?.lastName || '';
    this.createdOn = user?.createdOn || '';
    this.lastModified = user?.lastModified || '';
  }
}
