import { Component, OnInit, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, ReactiveFormsModule } from '@angular/forms';
import { Project } from '../../model/project/project';
import { Workflow } from '../../model/project/workflow';
import { User } from '../../model/user';
import { UserOption } from '../../model/user-option';
import { ProjectParticipant } from '../../model/project/project-participant';
import { UserService } from  '../../services/user-service.service';
import { Observable, of, BehaviorSubject  } from 'rxjs';
import { MatOptionModule } from '@angular/material/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import {
  map,
  tap,
  debounceTime,
  distinctUntilChanged,
  filter,
  switchMap,
  catchError,
  shareReplay,
  startWith,
  finalize
} from 'rxjs/operators';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatAutocompleteModule,
  MatAutocompleteSelectedEvent,
} from '@angular/material/autocomplete';
@Component({
  selector: 'app-project-form',
  templateUrl: './project-form.component.html',
  styleUrls: ['./project-form.component.css']
})
export class ProjectFormComponent implements OnInit {
@Input() project?: Partial<Project>; // may be undefined

form = this.fb.group({
  id: this.fb.control<string | null>(null),
  title: this.fb.control<string>('', { nonNullable: true }),
  owner: this.fb.control<User | null>(null),
  workflow: this.fb.control<Workflow | null>(null),
  participants: this.fb.control<ProjectParticipant[]>([]),
});

  readonly MIN_CHARS = 2;

  ownerCtrl = new FormControl<UserOption | string>('', { nonNullable: true });

  private loadingSubject = new BehaviorSubject<boolean>(false);
  isLoading$ = this.loadingSubject.asObservable();

  displayUser = (u?: UserOption | string) =>
  typeof u === 'string' ? u : (u?.label ?? '');
  trackById = (_: number, u: UserOption) => u.id;

  private query$ = this.ownerCtrl.valueChanges.pipe(
  startWith(''),
    map(v => (typeof v === 'string' ? v : v?.label) ?? ''),
    debounceTime(300),
    distinctUntilChanged(),
    filter(q => q.length === 0 || q.length >= 2),
    shareReplay(1)
  );

  options$: Observable<UserOption[]> = this.ownerCtrl.valueChanges.pipe(
  // no startWith('')  ← important
  map(v => (typeof v === 'string' ? v : v?.label) ?? ''),
  debounceTime(300),
  distinctUntilChanged(),
  switchMap(q =>
    q.length >= this.MIN_CHARS
      ? this.userService.findOptions(q).pipe(catchError(() => of([])))
      : of([]) // before min length, emit empty list
  ),
  shareReplay(1)
);

  userOptions: UserOption[] = [];

constructor(private fb: FormBuilder, private userService: UserService) {}

  ngOnInit() {
    if (this.project) this.form.patchValue(this.project);
    this.getAvailableUsers('');
  }

  getAvailableUsers(matcher: string) {
    this.userService.findOptions(matcher).subscribe( data => {
      this.userOptions = data;
    });;
  }

  onOwnerSelected(e: MatAutocompleteSelectedEvent) {
    const user = e.option.value as UserOption;
    // e.g., this.form.patchValue({ ownerId: user.id });
  }
}
