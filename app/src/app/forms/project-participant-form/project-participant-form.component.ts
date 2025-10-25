import { Component, Input, Output, EventEmitter, Optional, Inject, inject } from '@angular/core';
import { AbstractControl, ValidatorFn, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Observable, of } from 'rxjs';
import { debounceTime, distinctUntilChanged, map, startWith, switchMap, catchError } from 'rxjs/operators';

import { ProjectParticipant, ProjectRoles } from '../../model/project/project-participant';
import { User } from '../../model/user';
import { UserService } from '../../services/user-service.service';
import { UserOption } from '../../model/user-option';

type UserValue = string | UserOption | null;
type ParticipantPayload = {  role: ProjectRoles; userId: string };

const ROLE_OPTIONS: string[] = [
  'DEVELOPER',
  'TESTER',
  'PROJECT_MANAGER',
  'SALES_EXECUTIVE',
  'PMO_RESPONSIBLE',
];

@Component({
selector: 'app-project-participant-form',
templateUrl: './project-participant-form.component.html',
styleUrls: ['./project-participant-form.component.css']
})
export class ProjectParticipantFormComponent {
  private fb = inject(FormBuilder);

  roleOptions = ROLE_OPTIONS;

  constructor(
  private userService: UserService,
  @Optional() private dialogRef?: MatDialogRef<ProjectParticipantFormComponent>,
  @Optional() @Inject(MAT_DIALOG_DATA) private data?: { participants?: Array<Partial<ProjectParticipant>> }
  ) {
    // if dialog provided data, seed from it
    if (this.data?.participants?.length) {
      this.seedParticipants(this.data.participants);
    } else {
      // otherwise start with a single empty row (works for @Input path; setter may overwrite)
      this.addParticipantRow();
    }
  }

  /** If you want to seed existing participants, pass them in */
  @Input() set participants(value: Array<Partial<ProjectParticipant>> | undefined) {
    if (value?.length) {
      this.seedParticipants(value);
    }
  }

  private seedParticipants(list: Array<Partial<ProjectParticipant>>) {
    this.participantsFA.clear();
    this.optionsStreams.length = 0;

    list.forEach(p => {
      const u = p.user as User | null | undefined;

      // build a friendly label so it shows immediately
      const label = u
        ? [(u as any).name, (u as any).lastName].filter(Boolean).join(' ')
        : '';

      const userValue: UserValue = u?.id
        ? ({ id: u.id!, label: label || u.username || '(user)' } as UserOption)
        : '';

      this.addParticipantRow({
        userValue,
        role: (p.role as ProjectRoles) ?? 'DEVELOPER'
      });
    });

    if (this.participantsFA.length === 0) this.addParticipantRow();
  }

  /** Emit normalized result if you're not using a dialog */
  @Output() submitted = new EventEmitter<ParticipantPayload[]>();

  // ------- Form root -------
  form: FormGroup = this.fb.group({
    participants: this.fb.array<FormGroup>([]),
  });

  get participantsFA(): FormArray<FormGroup> {
    return this.form.get('participants') as FormArray<FormGroup>;
  }

  readonly MIN_CHARS = 2;

  // Stable per-row option streams (avoid rebuild/cancellation issues)
  optionsStreams: Observable<UserOption[]>[] = [];

  private buildOptionsStream(group: FormGroup): Observable<UserOption[]> {
    const ctrl = group.get('user') as FormControl<UserValue>;
    return ctrl.valueChanges.pipe(
      startWith(ctrl.value ?? ''),
      map(v => (typeof v === 'string' ? v : v?.label ?? '')),
      debounceTime(250),
      distinctUntilChanged(),
      switchMap(q =>
        (q?.trim().length ?? 0) >= this.MIN_CHARS
          ? this.userService.findOptions(q.trim())
          : of([])
      ),
      catchError(() => of([]))
    );
  }

  private createParticipantRow(init?: { userValue?: UserValue; role?: ProjectRoles }): FormGroup {
    const g = this.fb.group({
      // Single field: free-typed string (temporary) OR UserOption (selected)
      user: this.fb.control<UserValue>('', Validators.required),
      role: this.fb.control<ProjectRoles>(init?.role ?? ProjectRoles.DEVELOPER, { nonNullable: true }),
    });

    if (init?.userValue !== undefined) {
      g.patchValue({ user: init.userValue }, { emitEvent: false });
    }

    return g;
  }

  addParticipantRow(init?: { userValue?: UserValue; role?: ProjectRoles }) {
    const g = this.fb.group({
      user: this.fb.control<UserValue>(
        init?.userValue ?? '',
        { validators: [Validators.required, this.optionRequiredValidator] }
      ),
      role: this.fb.control<ProjectRoles>(init?.role ?? ProjectRoles.DEVELOPER, { nonNullable: true }),
    });

    this.participantsFA.push(g);
    this.optionsStreams.push(this.buildOptionsStream(g));
  }

  removeParticipantRow(i: number) {
    this.participantsFA.removeAt(i);
    this.optionsStreams.splice(i, 1);
    if (this.participantsFA.length === 0) this.addParticipantRow(); // keep at least one row
  }

  // ------- Template helpers -------
  trackByIndex = (i: number) => i;
  displayUser = (u?: UserOption | string | null) =>
    typeof u === 'string' ? u : (u?.label ?? '');

  // ------- Submit -------
  save() {
    if (this.form.invalid) return;

    const rows = this.participantsFA.getRawValue() as Array<{ user: UserValue; role: ProjectRoles }>;

    // Normalize: require a real option (with id). Free-typed strings are ignored.
    const payload: ParticipantPayload[] = rows
      .map(r => {
        const v = r.user;
        if (v && typeof v !== 'string' && v.id) {
          return { userId: v.id, role: r.role };
        }
        return null;
      })
      .filter((x): x is ParticipantPayload => !!x);

    // If used as a dialog, close with payload; otherwise emit
    if (this.dialogRef) {
      this.dialogRef.close(payload);
    } else {
      this.submitted.emit(payload);
    }
  }
  private isUserOption(v: unknown): v is UserOption {
    return !!v && typeof v === 'object' && 'id' in (v as any) && (v as any).id;
  }

  private optionRequiredValidator: ValidatorFn = (ctrl: AbstractControl) => {
    const v = ctrl.value as UserValue;
    return this.isUserOption(v) ? null : { optionRequired: true };
  };

  onUserSelected(index: number, opt: UserOption) {
    const c = this.participantsFA.at(index).get('user') as FormControl<UserValue>;
    c.setValue(opt, { emitEvent: true });   // ensure the control holds the option object
    c.markAsDirty();
    c.updateValueAndValidity();
  }

  enforceOption(index: number) {
    const c = this.participantsFA.at(index).get('user') as FormControl<UserValue>;
    if (!this.isUserOption(c.value)) {
      c.setValue(null, { emitEvent: true });  // drop free-typed text
      c.markAsTouched();
      c.updateValueAndValidity();
    }
  }

  cancel() {
    if (this.dialogRef) this.dialogRef.close();
  }
}
