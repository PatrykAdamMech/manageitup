import { Component, OnInit, Input, inject } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Observable, of } from 'rxjs';
import { map, switchMap, debounceTime, distinctUntilChanged, startWith, catchError } from 'rxjs/operators';
import { ProjectStatus } from '../../model/project/project-status';
import { ProjectStatusOption } from '../../model/project/project-status-option';
import { ProjectStatusService } from  '../../services/project-status-service.service';

type StatusValue = string | ProjectStatusOption | null;
type StatusPayload = { id?: string | null; name?: string; priority: number };

@Component({
  selector: 'app-workflow-form',
  templateUrl: './workflow-form.component.html',
  styleUrls: ['./workflow-form.component.css']
})
export class WorkflowFormComponent {
  private fb = inject(FormBuilder);
  private dialogRef = inject<MatDialogRef<WorkflowFormComponent>>(MatDialogRef);

  readonly MIN_CHARS = 2;

  trackByIndex = (i: number) => i;

  displayStatus = (o?: ProjectStatusOption | string | null) =>
  typeof o === 'string' ? o : (o?.label ?? '');

  optionsStreams: Observable<ProjectStatusOption[]>[] = [];

  constructor(private projectStatusService: ProjectStatusService) {
      this.addStatusRow();
    }

  isStatusPayload(x: any): x is StatusPayload {
    return !!x && typeof x.priority === 'number' && (typeof x.name === 'string' || x.id != null);
  }

  @Input() set workflow(value: { id?: string; name: string; statuses: ProjectStatus[] } | undefined) {
    if (!value) return;
    this.form.patchValue({ name: value.name }, { emitEvent: false });

    // rebuild rows from incoming statuses
    this.statusesFA.clear();
    this.optionsStreams.length = 0;

    (value.statuses ?? []).forEach(s => {
      this.addStatusRow({
        // preload as an option-shaped object (label will be resolved by first search)
        nameValue: s.id ? ({ id: s.id, label: '(loaded)' } as ProjectStatusOption) : s.name,
        priority: s.priority ?? 9,
      });
    });

    if (this.statusesFA.length === 0) this.addStatusRow();
  }

  form: FormGroup = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(2)]],
    statuses: this.fb.array<FormGroup>([]),
  });

  get statusesFA(): FormArray<FormGroup> {
    return this.form.get('statuses') as FormArray<FormGroup>;
  }

  private buildOptionsStream(group: FormGroup): Observable<ProjectStatusOption[]> {
    const ctrl = group.get('name') as FormControl<string | ProjectStatusOption | null>;

    return ctrl.valueChanges.pipe(
      startWith(''),
      map(v => (typeof v === 'string' ? v : v?.label ?? '')),
      debounceTime(250),
      distinctUntilChanged(),
      switchMap(q => (q?.trim().length ?? 0) >= this.MIN_CHARS
        ? this.projectStatusService.findOptions(q.trim())
        : of([])),
      catchError(() => of([]))
    );
}

  private createStatusRow(init?: { nameValue?: StatusValue; priority?: number }): FormGroup {
    const g = this.fb.group({
      // Single field: can be free text (new name) or ProjectStatusOption (existing)
      name: this.fb.control<StatusValue>('', Validators.required),
      priority: this.fb.control<number>(init?.priority ?? 9),
    });

    if (init?.nameValue !== undefined) {
      g.patchValue({ name: init.nameValue }, { emitEvent: false });
    }

    return g;
  }

  addStatusRow(init?: { nameValue?: StatusValue; priority?: number }) {
    const g = this.createStatusRow(init);
    this.statusesFA.push(g);
    this.optionsStreams.push(this.buildOptionsStream(g));
  }

  removeStatusRow(i: number) {
    this.statusesFA.removeAt(i);
    this.optionsStreams.splice(i, 1);
    if (this.statusesFA.length === 0) this.addStatusRow(); // keep at least one row
  }

  save() {
    if (this.form.invalid) return;

    const raw = this.form.getRawValue();

    const normalizedStatuses = (raw.statuses as Array<{ name: StatusValue; priority: number }>)
      .map(row => {
        const v = row.name;
        const priority = row.priority ?? 9;

        if (!v) return null;

        if (typeof v === 'string') {
          const name = v.trim();
          return name ? { name, priority } : null;
        }

        return v.id ? { id: v.id, priority } : null;
      })
      .filter(this.isStatusPayload);

    const payload = {
      name: raw.name as string,
      statuses: normalizedStatuses,
    };

    this.dialogRef.close(payload);


  }

  cancel() {
    this.dialogRef.close();
  }
}
