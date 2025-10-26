import { Component, OnInit, Input, inject } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import { FormBuilder, FormGroup, FormControl, ReactiveFormsModule, ValidatorFn, Validators } from '@angular/forms';
import { Workflow } from '../../model/project/workflow';
import { User } from '../../model/user';
import { UserOption } from '../../model/user-option';
import { WorkflowOption } from '../../model/project/workflow-option';
import { Project } from '../../model/project/project';
import { ProjectStatus } from '../../model/project/project-status';
import { ProjectParticipant, ProjectRoles } from '../../model/project/project-participant';
import { ProjectParticipantListComponent } from '../../lists/project-participant-list/project-participant-list.component';
import { ProjectStatusListComponent } from '../../lists/project-status-list/project-status-list.component';
import { ProjectStatusFormComponent } from '../../forms/project-status-form/project-status-form.component';
import { ProjectStatusOption } from '../../model/project/project-status-option';
import { ProjectParticipantFormComponent } from '../../forms/project-participant-form/project-participant-form.component';
import { ProjectCreateRequest } from '../../model/project/requests/project-create-request';
import { ProjectParticipantCreateRequest } from '../../model/project/requests/project-participant-create-request';
import { UserService } from  '../../services/user-service.service';
import { WorkflowService } from  '../../services/workflow-service.service';
import { ProjectStatusService } from  '../../services/project-status-service.service';
import { ProjectService } from  '../../services/project-service.service';
import { Observable, of, BehaviorSubject, forkJoin  } from 'rxjs';
import { MatOptionModule } from '@angular/material/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { WorkflowFormComponent } from '../../forms/workflow-form/workflow-form.component';
import { MatDialog } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { DateAdapter } from '@angular/material/core';

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

type ParticipantPayload = {  role: ProjectRoles; userId: string };
type StatusInput =
  | (ProjectStatusOption & { priority?: number })
  | { id: string; priority?: number }
  | { name: string; priority?: number };

@Component({
  selector: 'app-project-form',
  templateUrl: './project-form.component.html',
  styleUrls: ['./project-form.component.css']
})
export class ProjectFormComponent implements OnInit {

  project?: Partial<Project>;
  projectId: string | null = null;
  isEdit?: boolean;

  readonly MIN_CHARS = 2;

  // form controls - whatever user sees
  titleControl = new FormControl<string | null>(null, Validators.required);
  startDateControl = new FormControl<Date | null>(null, Validators.required);
  endDateControl = new FormControl<Date | null>(null, Validators.required);
  ownerCtrl = new FormControl<UserOption | string>('', Validators.required);
  workflowCtrl = new FormControl<WorkflowOption | string>('', Validators.required);
  participantCtrl = new FormControl<ProjectParticipant[]>([], { nonNullable: true })

  // storing values for the actual POST request
  userOptions: UserOption[] = [];
  owner: User | null = null;
  workflowOptions: WorkflowOption[] = [];
  workflow: Workflow | null = null;
  participants: ProjectParticipant[] = [];
  pendingParticipants: ParticipantPayload[] = [];
  statusesToDisplay: ProjectStatus[] = [];

  mainFormGroup = new FormGroup({
    title: this.titleControl,
    startDate: this.startDateControl,
    endDate: this.endDateControl,
    owner: this.ownerCtrl,
    workflow: this.workflowCtrl,
    participants: this.participantCtrl
  });

  private loadingSubject = new BehaviorSubject<boolean>(false);
  isLoading$ = this.loadingSubject.asObservable();

  displayUser = (u?: UserOption | string) =>
  typeof u === 'string' ? u : (u?.label ?? '');
  trackById = (_: number, u: UserOption) => u.id;

  displayWorkflow = (w?: WorkflowOption | string) =>
  typeof w === 'string' ? w : (w?.label ?? '');
  trackWorkflowById = (_: number, w: WorkflowOption) => w.id;

  readonly dialog = inject(MatDialog);

  readonly sortStatuses = (a: ProjectStatus, b: ProjectStatus) =>
      ( (a.priority ?? 9) - (b.priority ?? 9) ) ||
    a.name.localeCompare(b.name, undefined, { sensitivity: 'base' });

  options$: Observable<UserOption[]> = this.ownerCtrl.valueChanges.pipe(
    map(v => (typeof v === 'string' ? v : v?.label) ?? ''),
    debounceTime(300),
    distinctUntilChanged(),
    switchMap(q =>
      q.length >= this.MIN_CHARS
        ? this.userService.findOptions(q).pipe(catchError(() => of([])))
        : of([])
    ),
    shareReplay(1)
  );

  optionsWorkflow$: Observable<WorkflowOption[]> = this.workflowCtrl.valueChanges.pipe(
    map(v => (typeof v === 'string' ? v : v?.label) ?? ''),
    debounceTime(300),
    distinctUntilChanged(),
    switchMap(q =>
      q.length >= this.MIN_CHARS
        ? this.workflowService.findOptions(q).pipe(catchError(() => of([])))
        : of([])
    ),
    shareReplay(1)
  );

  constructor(private fb: FormBuilder,
              private route: ActivatedRoute,
              private userService: UserService,
              private workflowService: WorkflowService,
              private statusService: ProjectStatusService,
              private projectService: ProjectService,
              private router: Router,
              private dateAdapter: DateAdapter<Date>) {
    this.dateAdapter.setLocale('pl-PL');
  }

  ngOnInit() {
    this.getAvailableUsers('');
    this.getAvailableWorkflows('');

    this.route.paramMap.subscribe(params => {
        console.log(params);
        const id = params.get('id');
        if(id) {
          this.projectId = id;
          this.isEdit = true;
          this.projectService.findById(id).subscribe(project => {
              this.mainFormGroup.patchValue({
                title: project.title,
                startDate: project.startDate,
                endDate: project.endDate,
                owner: this.buildLabel(project.owner?.name, project.owner?.lastName),
                workflow: project.workflow?.name,
                participants: project.participants
            });
            this.owner = project.owner ?? null;
            this.workflow = project.workflow ?? null;
            this.statusesToDisplay = project.workflow?.statuses ?? [];
            this.participants = project.participants ?? [];
          });

        }
        else {
          this.isEdit = false;
        }
    });
  }

  buildLabel(x: string | null | undefined, y: string | null | undefined) {
      if(!x) return y;
      if(!y) return x;
      return x + ' ' + y;
  }

  getAvailableUsers(matcher: string) {
    this.userService.findOptions(matcher).subscribe( data => {
      this.userOptions = data;
    });;
  }

  getAvailableWorkflows(matcher: string) {
    this.workflowService.findOptions(matcher).subscribe( data => {
      this.workflowOptions = data;
    });;
  }

  onOwnerSelected(e: MatAutocompleteSelectedEvent) {
    const u = e.option.value as UserOption;
    this.ownerCtrl.setValue(u, { emitEvent: false });

    if(u.id) {
      const user = this.userService.findById(u.id).subscribe(user => {
        this.owner = user;
      });
    }
  }

  onWorkflowSelected(e: MatAutocompleteSelectedEvent) {
    const w = e.option.value as WorkflowOption;
    this.mainFormGroup.get('workflow')!.setValue(w);
    this.workflowCtrl.setValue({ id: w.id, label: w.label }, { emitEvent: false });

    if(w.id) {
      const workflow = this.workflowService.findById(w.id).subscribe(workflow => {
        this.workflow = workflow;
      });
    }
  }

  addParticipant() {
    let dialogRef = this.dialog.open(ProjectParticipantFormComponent, {
          height: '40%',
          width: '65%',
          data: {participants: this.participants}
    });
    dialogRef.afterClosed().subscribe(result => {
      if(!result) return;
      this.prepareParticipantsForDisplay(result).subscribe(pp => {
          this.participants = pp;
          this.mainFormGroup.get('participants')!.setValue(pp);
      });

      this.pendingParticipants.push(result)
    });
  }

  addWorkflow() {
    let dialogRef = this.dialog.open(WorkflowFormComponent, {
          height: '40%',
          width: '65%',
          data: {}
    });

    dialogRef.afterClosed().pipe(
        filter((result): result is any => !!result),

        switchMap(result =>
          this.prepareStatusesForDisplay(result.statuses).pipe(
            tap(prepared => { result.statuses = prepared; }),
            map(() => result)
    )
    ),

    tap(workflow => {
          this.statusesToDisplay = workflow.statuses.sort(this.sortStatuses);
          this.workflow = workflow;
          this.mainFormGroup.get('workflow')!.setValue(workflow);
          this.workflowCtrl.setValue({ id: workflow.id, label: workflow.name }, { emitEvent: false });
          console.log('Workflow:', JSON.stringify(workflow, null, 2));
        })
      ).subscribe();
  }

  prepareParticipantsForDisplay(participants: ParticipantPayload[]) {
    const calls = participants.map(p =>
    this.userService.findById(p.userId).pipe(
      map(user => new ProjectParticipant({ user, role: p.role })),
      catchError(() => of(null))
        )
    );
    return forkJoin(calls).pipe(
        map(list => list.filter((x): x is ProjectParticipant => !!x))
      );
  }

  prepareStatusesForDisplay(statuses: StatusInput[]): Observable<ProjectStatus[]> {
    if (!statuses?.length) return of([]);

    const calls = statuses.map(s => {
      const priority = (s as any).priority ?? 9;

      if ('id' in s && s.id) {
        return this.statusService.findById(s.id).pipe(
          map(api => new ProjectStatus({ ...api, priority: api.priority ?? priority })),
          catchError(() => of(null)) // skip row on error instead of breaking all
        );
      }
      const name =
        ('name' in s && s.name) ? s.name :
        ('label' in s && s.label) ? s.label! : '';

      return of(new ProjectStatus({ id: null, name, priority }));
    });

    return forkJoin(calls).pipe(
      map(list => list.filter((x): x is ProjectStatus => !!x))
    );
  }

  private prepareProjectForPost(): ProjectCreateRequest {
    const startDate = this.mainFormGroup.get('startDate')?.value;
    const endDate = this.mainFormGroup.get('endDate')?.value;
    const title = this.mainFormGroup.get('title')?.value;
    const ownerId = this.owner?.id;
    const workflow = this.workflow;
    const participants = this.participants;

    const finalProject = new ProjectCreateRequest();

    let convStartDate = this.convertDate(this.dateAdapter.parse(startDate, ''));
    let convEndDate = this.convertDate(this.dateAdapter.parse(endDate, ''));

    if(this.isEdit) finalProject.id = this.projectId;

    finalProject.startDate = convStartDate ? convStartDate : new Date('2000-01-01');
    finalProject.endDate = convEndDate ? convEndDate : new Date('2000-01-01');
    finalProject.title = title ? title : 'Default title';
    finalProject.ownerId = ownerId  ? ownerId : '';
    finalProject.workflow = this.normalizeWorkflow(workflow);

    for(let ps of workflow?.statuses ?? []) {
        finalProject.workflow.statuses.push(this.normalizeStatuses(ps));
    }

    finalProject.participants = [];

    for(let pp of participants ?? []) {
        finalProject.participants.push(this.normalizeParticipants(pp));
    }

    return finalProject;

  }

  private normalizeWorkflow(workflow: Workflow | null): Workflow {
    const finalWorkflow = new Workflow();
    if(workflow) {
      if(workflow.id) {
        finalWorkflow.id = workflow.id;
      } else {
        finalWorkflow.name = workflow.name;
      }
    }

    return finalWorkflow;
  }

  private normalizeStatuses(status: ProjectStatus | null): ProjectStatus {

    const finalStatus = new ProjectStatus();

    if(status) {
      if(status.id) {
        finalStatus.id = status.id;
      } else {
        finalStatus.name = status.name;
        finalStatus.priority = status.priority;
      }
    }

    return finalStatus;
  }

  private normalizeParticipants(pp: ProjectParticipant | null): ProjectParticipantCreateRequest {
      const finalPP = new ProjectParticipantCreateRequest();

      if(pp && pp.user) {
        finalPP.role = pp.role;
        finalPP.userId = pp.user.id ?? '';
      }

      return finalPP;
  }

  toLocalDateString(date: Date | null | undefined) {
    if(date == null || date == undefined) return null;
    const pad = (n: number) => n.toString().padStart(2, '0');
    return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}`;
  }

  convertDate(date: Date | null | undefined): string | null {
      if(date == null || date == undefined) return null;
      let convDate = this.dateAdapter.parse(date, '');
      return this.toLocalDateString(convDate);
  }

  save() {
    const body = this.prepareProjectForPost();
    if(this.mainFormGroup.invalid) {
      alert('Please fill in all mandatory field!');
      return;
    }

    if(this.isEdit) {
      console.log('Prepared PUT body' + JSON.stringify(body, null, 4));
      this.projectService.update(body).subscribe({
        next: (res) => {
          console.log('Saved project');
          this.router.navigateByUrl('/projects/list');
        },
        error: (err) => {
          console.error('Save failed', err);
          alert('Save failed: ' + (err.message || err.statusText));
        }
      });
    } else {
      console.log('Prepared POST body' + JSON.stringify(body, null, 4));
      this.projectService.save(body).subscribe({
        next: (res) => {
          console.log('Saved project');
          this.router.navigateByUrl('/projects/list');
        },
        error: (err) => {
          console.error('Save failed', err);
          alert('Save failed: ' + (err.message || err.statusText));
        }
      });
    }
  }
}
