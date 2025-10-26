import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user-service.service';
import { User, UserRoles } from '../../model/user';
import { UserCreateRequest } from '../../model/user-create-request';
import { FormBuilder, FormGroup, FormControl, ReactiveFormsModule, ValidatorFn, Validators } from '@angular/forms';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent {

  user?: Partial<User>;
  userId: string | null = null;
  isEdit?: boolean;

  username: string = '';
  email: string = '';
  name: string = '';
  lastName: string = '';
  role: string = '';

  mainFormGroup = new FormGroup({
    username: new FormControl<string | null>(null, Validators.required),
    email: new FormControl<string | null>(null, [Validators.required, Validators.email]),
    password: new FormControl<string | null>(null, Validators.required),
    name: new FormControl<string | null>(null, Validators.required),
    lastName: new FormControl<string | null>(null, Validators.required),
    role: new FormControl<UserRoles | null>(null, Validators.required)
  });

  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService) {

  }

  onSubmit() {
    const body = this.prepareUserForPost();
    if(this.mainFormGroup.invalid) {
      alert('Please fill in all mandatory field!');
      return;
    }

    if(this.isEdit) {
      console.log('Prepared PUT body' + JSON.stringify(body, null, 4));
      this.userService.update(body).subscribe({
        next: (res) => {
          console.log('Saved user');
          this.router.navigateByUrl('/users/list');
        },
        error: (err) => {
          console.error('Save failed', err);
          alert('Save failed: ' + (err.message || err.statusText));
        }
      });
    } else {
      console.log('Prepared POST body' + JSON.stringify(body, null, 4));
      this.userService.save(body).subscribe({
        next: (res) => {
          console.log('Saved user');
          this.router.navigateByUrl('/users/list');
        },
        error: (err) => {
          console.error('Save failed', err);
          alert('Save failed: ' + (err.message || err.statusText));
        }
      });
    }

  }

  private prepareUserForPost(): UserCreateRequest {
    const finalUser = new UserCreateRequest();

    if(this.isEdit) finalUser.id = this.userId ?? '';
    const username = this.mainFormGroup.get('username')?.value;
    const email = this.mainFormGroup.get('email')?.value;
    const password = this.mainFormGroup.get('password')?.value;
    const name = this.mainFormGroup.get('name')?.value;
    const lastName = this.mainFormGroup.get('lastName')?.value;
    const role = this.mainFormGroup.get('role')?.value;

    finalUser.username = username ?? '';
    finalUser.email = email ?? '';
    finalUser.password = password ?? '';
    finalUser.name = name ?? '';
    finalUser.lastName = lastName ?? '';
    finalUser.role = role ?? UserRoles.USER;

    return finalUser;

  }



  gotoUserList() {
    this.router.navigate(['/users']);
  }
}
