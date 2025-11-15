import { Component, OnInit, Input, inject, Inject, signal } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Observable, of } from 'rxjs';
import { map, switchMap, debounceTime, distinctUntilChanged, startWith, catchError } from 'rxjs/operators';
import { ProjectStatus } from '../../model/project/project-status';
import { Workflow } from '../../model/project/workflow';
import { ProjectStatusOption } from '../../model/project/project-status-option';
import { Router, ActivatedRoute } from '@angular/router';

type StatusPayload = { id?: string | null; name?: string; priority: number };

@Component({
  selector: 'app-workflow-form',
  templateUrl: './workflow-form.component.html',
  styleUrls: ['./workflow-form.component.css']
})
export class WorkflowFormComponent {
  private fb = inject(FormBuilder);
  private dialogRef = inject<MatDialogRef<WorkflowFormComponent>>(MatDialogRef);

  workflow: Workflow;

  priorities: number[] = [1,2,3,4,5,6,7,8,9];

  trackByIndex = (i: number) => i;

  constructor(@Inject(MAT_DIALOG_DATA) public data: Workflow) {
      this.workflow = data;
      this.buildWorkflowForm();
  }

  isStatusPayload(x: any): x is StatusPayload {
    return !!x && typeof x.priority === 'number' && (typeof x.name === 'string' || x.id != null);
  }

  buildWorkflowForm() {
    if (!this.workflow) {
      if (this.statusesFA.length === 0) this.addStatusRow();
      return;
    }

    this.form.patchValue({ name: this.workflow.name }, { emitEvent: false });
    this.statusesFA.clear();

    (this.workflow.statuses ?? []).forEach(s => {
      this.addStatusRow({
        // preload as an option-shaped object (label will be resolved by first search)
        nameValue: s.name ?? null,
        priority: s.priority ?? 9,
      });
    });
  }

  form: FormGroup = this.fb.group({
    name: ['', Validators.required],
    statuses: this.fb.array<FormGroup>([]),
  });

  get statusesFA(): FormArray<FormGroup> {
    return this.form.get('statuses') as FormArray<FormGroup>;
  }

  private createStatusRow(init?: { nameValue?: string; priority?: number }): FormGroup {
    const g = this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      priority: this.fb.control<number>(init?.priority ?? 9, [Validators.required, Validators.min(1), Validators.max(9)]),
    });

    if (init?.nameValue !== undefined) {
      g.patchValue({ name: init.nameValue }, { emitEvent: false });
    }

    return g;
  }

  addStatusRow(init?: { nameValue?: string; priority?: number }) {
    const g = this.createStatusRow(init);
    this.statusesFA.push(g);
  }

  removeStatusRow(i: number) {
    this.statusesFA.removeAt(i);
    if (this.statusesFA.length === 0) this.addStatusRow();
  }

  save() {
    if (this.form.invalid) return;

    const raw = this.form.getRawValue();

    const normalizedStatuses = (raw.statuses as Array<{ name: string; priority: number }>)

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
