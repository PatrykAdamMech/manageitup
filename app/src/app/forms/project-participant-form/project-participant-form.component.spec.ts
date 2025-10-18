import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectParticipantFormComponent } from './project-participant-form.component';

describe('ProjectParticipantFormComponent', () => {
  let component: ProjectParticipantFormComponent;
  let fixture: ComponentFixture<ProjectParticipantFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectParticipantFormComponent]
    });
    fixture = TestBed.createComponent(ProjectParticipantFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
