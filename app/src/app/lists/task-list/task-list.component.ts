import { Component, Input, inject } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Project } from '../../model/project/project';
import { ProjectParticipant } from '../../model/project/project-participant';
import { User } from '../../model/user';
import { Task, TaskStatus, TaskStatusName } from '../../model/project/task';
import { TaskCreateRequest } from '../../model/project/requests/task-create-request';
import { TaskService } from '../../services/task-service.service';
import { DeleteConfirmDialogComponent } from '../../forms/delete-confirm-dialog/delete-confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent {
  @Input() project: Project = new Project();

  columnsToDisplay = ['actions','name','assignee','status','description'];
  private fb = inject(FormBuilder);
  readonly dialog = inject(MatDialog);

  form = this.fb.group({
    tasks: this.fb.array<FormGroup>([])
  });

  /** keep track of which rows are in edit mode */
  private editing = new Set<string>();

  private newRows = new Set<string>();

  private statusMap = new Map<string, string>();

  // mock options – replace with real data
  assignees: Array<ProjectParticipant> = [];
  statuses: string[] = ['Not assigned','Assigned','In progress','Closed'];
  availableStatuses: Array<TaskStatus> = [];

  trackStatusByName = (_: number, s: TaskStatus) => s.name;
  trackRowById = (_: number, t: Task) => t.id ?? _;

  constructor(private taskService: TaskService) {}

  ngOnInit() {
    this.taskService.findAllStatuses().subscribe(data => {
      this.availableStatuses = data;
      this.statusMap = new Map(this.availableStatuses.map(s => [s.name, s.label]));
    });
    this.assignees = this.project.participants;
  }

  // remove ngOnInit() form building to avoid double setControl
  ngOnChanges() {
    if (!this.project?.tasks) return;
    const groups = this.project.tasks.map(t => this.buildRowGroup(t));
    this.form.setControl('tasks', this.fb.array(groups));
  }

  get tasksFA(): FormArray<FormGroup> {
    return this.form.get('tasks') as FormArray<FormGroup>;
  }

  buildRowGroup(t: Task) {
      return this.fb.group({
        id: [{ value: t.id ?? null, disabled: true }],
        name: [t.name ?? '', [Validators.required]],
        assigneeId: [t.assignee?.id ?? ''],
        status: [t.status ?? 'NOT_ASSIGNED', [Validators.required]],
        description: [t.description ?? '']
      });
    }

  isEditing(id: string | null) {
    if(id == null) return;
    return this.editing.has(id);
  }

  statusLabel = (name?: string | null) => {
    if (!name) return '';
    return this.statusMap.get(name) ?? name;
  };

  startEdit(id: string | null, index: number, e?: MouseEvent) {
    e?.stopPropagation?.();
    if(id == null) return;
    this.editing.add(id);
    // seed the form row with latest task data in case project changed
    this.tasksFA.at(index).patchValue(this.buildRowGroup(this.project.tasks[index]).value, { emitEvent: false });
  }

  cancelEdit(id: string | null, index: number, e?: MouseEvent) {
    e?.stopPropagation?.();
    if(id == null) return;
    this.editing.delete(id);
    this.tasksFA.removeAt(index);
  }

  addTask(e?: MouseEvent) {
    e?.stopPropagation?.();
    const t = new Task(); // set defaults if needed
    // temporary client id so editing works before server creates one
    t.id = crypto.randomUUID?.() ?? Math.random().toString(36).slice(2);
    this.project.tasks = [...this.project.tasks, t];
    this.tasksFA.push(this.buildRowGroup(t));
    this.startEdit(t.id!, this.tasksFA.length - 1);
    this.newRows.add(t.id);
    console.log(JSON.stringify(t, null, 4));
  }

  removeTask(index: number, e?: MouseEvent) {
    e?.stopPropagation?.();

    const task = this.project.tasks[index];

    let dialogRef = this.dialog.open(DeleteConfirmDialogComponent, {
          height: '150px',
          width: '350px',
          data: 'Delete task ' + task.name + '?'
        });

    dialogRef.afterClosed().subscribe(result => {
      if(!result) return;

      const id = task.id!;
      // optionally call API first; on success:
      this.editing.delete(id);
      this.tasksFA.removeAt(index);
      this.project.tasks.splice(index, 1);
      this.project.tasks = [...this.project.tasks]; // trigger change detection if needed
      console.log('id to delete: ' + id);
      this.taskService.delete(id).subscribe(() => {});
    });
  }

  saveRow(task: Task, index: number, e?: MouseEvent) {
    e?.stopPropagation?.();

    const rowFA = this.tasksFA.at(index);
    rowFA.markAllAsTouched();
    if (rowFA.invalid) return;

    const rowValue = rowFA.getRawValue() as TaskCreateRequest;
    const payload: TaskCreateRequest = { ...rowValue, projectId: this.project.id };
    const rowId = rowValue.id ?? '';

    // build an updated task from existing + payload (preserve nested objects)
    const existing = this.project.tasks[index] ?? ({} as Task);
    const updatedTask: Task = {
      ...existing,
      ...payload as any, // payload contains primitive fields your template uses
      // if only assigneeId is present, try to reconstruct the assignee object for display
      assignee: this.assignees.find(a => a.id === payload.assigneeId) ?? existing.assignee,
      // keep id if already present (server returns id only — we won't rely on it here)
      id: existing.id ?? payload.id
    };

    // optimistic UI update: replace the task immutably so Angular re-renders
    this.project = {
      ...this.project,
      tasks: this.project.tasks.map((t, i) => (i === index ? updatedTask : t))
    };

    // create row
    if(this.newRows.has(rowId)) {
      payload.id = null;
      console.log('new');

      this.taskService.save(payload).subscribe(
        data => {
          updatedTask.id = data;
        },
        err => {
          console.error('Save failed', err);
          // optional: show user feedback. rollback logic omitted for simplicity.
        }
      );

    } else {
      // send update to server but ignore/skip checking the response body
      this.taskService.update(payload).subscribe(
        // success handler intentionally empty (we already updated the UI)
        () => {},
        err => {
          console.error('Save failed', err);
          // optional: show user feedback. rollback logic omitted for simplicity.
        }
      );
    }

    // sync the form row
    this.tasksFA.at(index).patchValue({
      id: updatedTask.id,
      name: updatedTask.name,
      assigneeId: updatedTask.assignee?.id ?? payload.assigneeId,
      status: updatedTask.status ?? payload.status,
      description: updatedTask.description ?? payload.description
    }, { emitEvent: false });

    // exit edit mode
    this.editing.delete(updatedTask.id ?? '');
  }
}
