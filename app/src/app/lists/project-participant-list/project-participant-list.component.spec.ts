import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectParticipantListComponent } from './project-participant-list.component';

describe('ProjectParticipantListComponent', () => {
  let component: ProjectParticipantListComponent;
  let fixture: ComponentFixture<ProjectParticipantListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectParticipantListComponent]
    });
    fixture = TestBed.createComponent(ProjectParticipantListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
